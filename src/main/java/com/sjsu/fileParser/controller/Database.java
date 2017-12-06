package com.sjsu.fileParser.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Database {
	public List<Map<String, Object>> executeQuery(String sql) throws SQLException {
        System.out.println("Executing SQL query: " + sql);

        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }
        Connection connection = DriverManager.getConnection("add your connection sting","username","pasword");
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<String, Object>(columnCount);
                for (int i = 1; i <= columnCount; i++)
                    row.put(meta.getColumnName(i), rs.getObject(i));

                rows.add(row);
            }

            rs.close();
            statement.close();
            return rows;
        } catch (SQLException e) {
            throw new SQLException("Error executing query: " + sql, e);
        } finally {
            connection.close();
        }
    }
	
	public boolean executeUpdate(String sql) throws SQLException{
		System.out.println("Executing SQL query: " + sql);

        Statement statement = null;
        Connection connection = DriverManager.getConnection("add your connection sting","username","pasword");
        try {
			statement = connection.createStatement();

			statement.execute(sql);

			System.out.println("Record is updated to DBUSER table!");
			return true;
			

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return false;

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}

		}

	}
	
}
