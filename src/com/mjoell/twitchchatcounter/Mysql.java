package com.mjoell.twitchchatcounter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mysql {	
	private static String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static String DB_CONNECTION = "jdbc:mysql://localhost:3306/twitch";
	private static String DB_USER = TwitchChatCounter.mysqluser;
	private static String DB_PASSWORD = TwitchChatCounter.mysqlpassword;
 
	public static void main() {
		if(DB_PASSWORD.equals("false")) {
			DB_PASSWORD = "";
		}
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
			
			if(dbConnection != null) {
				dbConnection.close();
			}
		}
	}
	
	private static void addOneForUserGlobal() throws SQLException {
		// TODO When out of beta, we will have a global count.  Until then...
	}
	
	public static String getTopChatterInChannel(String channel) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement ps = null;
		String sendMessage = null;
 
		String checkSQL = "SELECT * FROM " + channel + "_count ORDER BY messages DESC LIMIT 1";
		
		try {
			dbConnection = getDBConnection();
			ps = dbConnection.prepareStatement(checkSQL);
			
			final ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next()) {
				String usernamefd = resultSet.getString("username");
				int messagesfd = resultSet.getInt("messages");
				
				sendMessage  = "The top chatter right now is " + usernamefd + " with " + messagesfd + " on record!";
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(ps != null) {
				ps.close();
			}
			
			if(dbConnection != null) {
				dbConnection.close();
			}
		}
		return sendMessage;
	}
	
	public static String getTopTenChatterInChannel(String channel) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement ps = null;
		String sendMessage = "Top 10 chatters: ";
		
		String checkSQL = "SELECT * FROM " + channel + "_count ORDER BY messages DESC LIMIT 10";
		
		try {
			dbConnection = getDBConnection();
			ps = dbConnection.prepareStatement(checkSQL);
			
			final ResultSet resultSet = ps.executeQuery();
			
			while(resultSet.next()) {
				String usernamefd = resultSet.getString("username");
				int messagesfd = resultSet.getInt("messages");
				
				sendMessage = sendMessage + usernamefd + " - " + messagesfd + "; ";
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(ps != null) {
				ps.close();
			}
			
			if(dbConnection != null) {
				ps.close();
			}
		}
		
		return sendMessage;
	}
	
	public static void addOneEmoteForChannel(String emote, String channel) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement ps = null;
		
		String updateSQL = "UPDATE " + channel + "_emotes SET " + emote + " = 1 + " + emote;
		
		try {
			dbConnection = getDBConnection();
			ps = dbConnection.prepareStatement(updateSQL);
			ps.executeUpdate();
			System.out.println("Adding one...");				
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null) {
				ps.close();
			}
			
			if(dbConnection != null) {
				ps.close();
			}
		}
	}
	
	public static String getEmoteCountInChannel(String channel) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement ps = null;
		String sendMessage = "How often have emotes been used?  ";
		
		String checkSQL = "SELECT * FROM " + channel + "_emotes";
		
		try {
			dbConnection = getDBConnection();
			ps = dbConnection.prepareStatement(checkSQL);
			
			final ResultSet resultSet = ps.executeQuery();
			
			while(resultSet.next()) {				
				int KappaHD = resultSet.getInt("KappaHD");
				int FrankerZ = resultSet.getInt("FrankerZ");
				int Keepo = resultSet.getInt("Keepo");
				int PJSalt = resultSet.getInt("PJSalt");
				int Kreygasm = resultSet.getInt("Kreygasm");
				int SwiftRage = resultSet.getInt("SwiftRage");
				int FailFish = resultSet.getInt("FailFish");
				int PogChamp = resultSet.getInt("PogChamp");
				int Kappa = resultSet.getInt("Kappa");
				
				sendMessage = sendMessage + "Kappa - " + Kappa + "; FrankerZ - " + FrankerZ + "; KappaHD - " + KappaHD + "; Keepo - " + Keepo + "; PJSalt - " + PJSalt + "; Kreygasm - " + Kreygasm + "; SwiftRage - " + SwiftRage + "; Fail Fish - " + FailFish + "; PogChamp - " + PogChamp;
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(ps != null) {
				ps.close();
			}
			
			if(dbConnection != null) {
				ps.close();
			}
		}
		
		return sendMessage;
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
