package com.qst.file_format_conv.services;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zxcvbnm,./");
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
