package com.qst.file_format_conv.handlers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;



import com.qst.file_format_conv.parser.XlsToCsvService;

@WebServlet("/upload")
@MultipartConfig
public class UploadHandler extends HttpServlet {
	//public static final Logger log = Logger.getLogger(UploadHandler.class.getName());
	private static final long serialVersionUID = 1L;
	String errorString;
	String errorFiles;

	public UploadHandler() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		errorString = "";
		List<Part> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName()))
				.collect(Collectors.toList());
		String firstFile = getFileName(fileParts.get(0));

		if (fileParts.size() >= 4) {
			errorString = "Please upload atleast 4 files or less.";
			request.setAttribute("error", errorString);
			RequestDispatcher dispatcher = request.getRequestDispatcher("addFiles.jsp");
			dispatcher.forward(request, response);
			return;
		} else if (fileParts.size() == 1 && firstFile.length() == 0) {
			errorString = "Please select a file to upload";
			request.setAttribute("error", errorString);
			RequestDispatcher dispatcher = request.getRequestDispatcher("addFiles.jsp");
			dispatcher.forward(request, response);
			return;
		} else {
			for (Part filePart : fileParts) {
				String fileName = getFileName(filePart);
				InputStream fileContent = filePart.getInputStream();
				String csvString = null;

				try {
					if (fileName.contains(".xls") || fileName.contains(".xlsx")) {
						String[] fileNameSplit = fileName.split("\\.");
						String newName = fileNameSplit[0] + ".csv";
						XlsToCsvService.XlsToCsv(fileContent);
					} else {
				//		log.info("File Format  Not Supported.");
						errorFiles += fileName + ", ";
					}
				} catch (Exception e) {
					e.getMessage();
					errorString += "All files could not be uploaded. Please try again.";
					request.setAttribute("error", errorString);
					RequestDispatcher dispatcher = request.getRequestDispatcher("addFiles.jsp");
					dispatcher.forward(request, response);
					return;
				}
				if (errorFiles.length() == 0) {
					// FORWARD REQUEST WITH STATUS MESSAGE TO THE HOME TAB ADD RUNS
					request.setAttribute("status_hcp",
							"*Files Uploaded Successfully, Please go to the Summary Tab for more details.\n");
				} else if (errorFiles.length() > 0) {
					request.setAttribute("error_hcp", "*" + errorFiles + " could not be uploaded!<br/>" + errorString);

				}
				RequestDispatcher dispatcher = request.getRequestDispatcher("addFiles.jsp");
				dispatcher.forward(request, response);

				return;

			}

		}
	}

	public static String getFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		System.out.println("content-disposition header= " + contentDisp);
		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return token.substring(token.indexOf("=") + 2, token.length() - 1);
			}
		}
		return "";
	}
}
