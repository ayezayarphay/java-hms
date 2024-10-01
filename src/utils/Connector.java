package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Connector {
	private static final String url = "jdbc:mysql://localhost/hospital";
	private static final String user = "root";
	private static final String password = "";

	public static Connection getConnection() throws Exception {

		// String url = "jdbc:mysql://" + InetAddress.getLocalHost().getHostAddress() +
		// "/hospital";
		return DriverManager.getConnection(url, user, password);
	}

	public static PreparedStatement getPrepareStatement(String sql) throws Exception {
		return getConnection().prepareStatement(sql);
	}

	public static ResultSet getResultSet(String sql) throws Exception {
		return getPrepareStatement(sql).executeQuery();
	}
}
