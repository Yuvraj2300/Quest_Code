package com.qst.file_format_conv.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qst.file_format_conv.services.DBConnector;

@WebServlet("/download")
public class DownloadHandler extends HttpServlet {
/*
	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;

	public DownloadHandler() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("downldBtn") != null) {

			DBConnector dbCon = new DBConnector();
			Connection conn = dbCon.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT file_name,file FROM audit_table_1 WHERE JOB_ID IN (Select Max(JOB_ID) from audit_table_1 )");
			ResultSet rs = pstmt.executeQuery();
			Blob blob = rs.getBlob(1);

			PreparedStatement statement = conn.prepareStatement(sql);

			ResultSet result = statement.executeQuery();
			if (result.next()) {
				
				String fileName = result.getString("FILE_NAME");
				Blob blob = result.getBlob("FILE");

				InputStream inputStream = blob.getBinaryStream();

				// FORCES DOWNLOAD
				response.setHeader("Content-Disposition", "attachment; filename=" + "Output_" + fileName);

				// OBTAINS RESPONSE'S OUTPUT STREAM
				OutputStream outputStream = response.getOutputStream();

				byte[] buffer = new byte[4096];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outputStream.close();
		//		log.info("Output File Downloaded for File: " + fileName + "\n");
			}

			conn.close();


			}
		}*/
	}


