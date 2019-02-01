package com.qst.file_format_conv.services;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBService {
	static DBConnector dbCon;

	static {
		dbCon = new DBConnector();
	}

	public static void insertFileToDB(String fileName, InputStream stream, String fileExt) throws SQLException {
		Connection conn = dbCon.getConnection();
		if (conn != null) {
			PreparedStatement ps = conn
					.prepareStatement("insert into audit_tbl (fileName,file_ext,status,file) values (?,?,?,?)");
			ps.setString(2, fileName);
			ps.setString(3, fileExt);
			ps.setString(4, "uploaded");
			ps.setBlob(5, stream);
			ps.executeQuery();
			conn.commit();
		}
	}
}
