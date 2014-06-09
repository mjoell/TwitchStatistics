package com.mjoell.twitchchatcounter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mysql {	
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/twitch";
	private static final String DB_USER = TwitchChatCounter.mysqluser;
	private static final String DB_PASSWORD = "";
 
	public static void main() {
		
	}

	public static void addOneForUserInChannel(String channel, String username) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement ps = null;
 
		String checkSQL = "SELECT * FROM " + channel + "_count WHERE username = ?";
		String updateSQL = "UPDATE " + channel + "_count SET messages = messages + 1 WHERE username = ?";
		String insertSQL = "INSERT INTO " + channel + "_count (username, messages) VALUES (?, ?)";
		
		try {
			dbConnection = getDBConnection();
			ps = dbConnection.prepareStatement(checkSQL);
			
			ps.setString(1, username);
			
			final ResultSet resultSet =  ps.executeQuery();
			
			if(resultSet.next()) {
				final int count = resultSet.getInt(1);
				System.out.println("Adding one...");
								
				ps = dbConnection.prepareStatement(updateSQL);
				ps.setString(1, username);
				ps.executeUpdate();
				
			} else {
				System.out.println("Inserting new...");
								
				ps = dbConnection.prepareStatement(insertSQL);
				ps.setString(1, username);
				ps.setInt(2, 1);
				ps.executeUpdate();
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(ps != null) {
				ps.close();
			}
		}
	}
	
	private static void addOneForUserGlobal() throws SQLException {
		
	}
	
	private static Connection getDBConnection() {
		Connection dbConnection = null;
 
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
 
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return dbConnection;
	}
	
	private static java.sql.Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}
}
