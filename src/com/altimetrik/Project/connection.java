package com.altimetrik.Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection {
	public static Connection createConnection() {

		final String DB_URL = "jdbc:mysql://localhost:3306/invoice?autoReconnect=true&useSSL=false";
		final String USER = "root";
		final String PASS = "root";
		Connection con = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

}
