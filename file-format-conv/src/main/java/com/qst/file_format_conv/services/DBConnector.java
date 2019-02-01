package com.qst.file_format_conv.services;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
	public Connection getConnection(String userName, String password, String port, String host, String serviceName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBNAMEEE", "root", "root");
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
