package com.qst.file_format_conv.handlers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import com.qst.file_format_conv.parser.JsonToCsvService;
import com.qst.file_format_conv.parser.XlsToCsvService;
import com.qst.file_format_conv.services.DBService;

@WebServlet("/upload")
@MultipartConfig
public class UploadHandler extends HttpServlet {
	// public static final Logger log =
	// Logger.getLogger(UploadHandler.class.getName());
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
				String jsonString = null;

				try {
					if (fileName.contains(".xls") || fileName.contains(".xlsx")) {
						String[] fileNameSplit = fileName.split("\\.");
						String originalFileName = fileNameSplit[0];
						String fileExt = fileNameSplit[1];
						String newName = fileNameSplit[0] + ".csv";
						csvString = XlsToCsvService.XlsToCsv(fileContent);

						fileContent = new ByteArrayInputStream(csvString.getBytes());

						DBService.insertFileToDB(originalFileName, fileContent, fileExt);
					} else if (fileName.contains(".json")) {

						String[] fileNameSplit = fileName.split("\\.");
						String originalFileName = fileNameSplit[0];
						String fileExt = fileNameSplit[1];
						String newName = fileNameSplit[0] + ".csv";
						
						jsonString	=	JsonToCsvService.convert(fileContent);
						DBService.insertFileToDB(originalFileName, fileContent, fileExt);

					} else {
						// log.info("File Format Not Supported.");
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
