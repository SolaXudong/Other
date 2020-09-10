package com.xu.tt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by jixin on 18-2-25.
 */
public class PhoenixTest {

	public static void main(String[] args) throws Exception {
		// System.setProperty("hadoop.home.dir",
		// "D:\\tt\\hadoop-common-2.2.0-bin-master");
		Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
		Connection connection = DriverManager.getConnection("jdbc:phoenix:hadoop100:2181");
		PreparedStatement statement = connection.prepareStatement("select * from person");
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			System.out.println(resultSet.getString("name"));
		}
		statement.close();
		connection.close();
	}

}
