package com.twitchstatistics.twitchstatistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class Mysql {
	private static DataSource ds = MysqlDS.getDataSource();

	@SuppressWarnings("resource")
	public static void addOneForUserInChannel(String channel, String username) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
 
		String checkSQL = "SELECT * FROM " + channel + "_count WHERE username = ?";
		String updateSQL = "UPDATE " + channel + "_count SET messages = messages + 1 WHERE username = ?";
		String insertSQL = "INSERT INTO " + channel + "_count (username, messages) VALUES (?, ?)";
		
		try {
			con = ds.getConnection();
			
			ps = con.prepareStatement(checkSQL);
			
			ps.setString(1, username);
			
			rs =  ps.executeQuery();
			
			if(rs.next()) {
				if(TwitchStatistics.verbose) System.out.println("Adding one...");
				
				ps = con.prepareStatement(updateSQL);
				ps.setString(1, username);
				ps.executeUpdate();
				
			} else {
				if(TwitchStatistics.verbose) System.out.println("Inserting new...");
								
				ps = con.prepareStatement(insertSQL);
				ps.setString(1, username);
				ps.setInt(2, 1);
				ps.executeUpdate();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
	}
	
	@SuppressWarnings("resource")
	public static void addOneForUserGlobal(String username) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
 
		String checkSQL = "SELECT * FROM global WHERE username = ?";
		String updateSQL = "UPDATE global SET messages = messages + 1 WHERE username = ?";
		String insertSQL = "INSERT INTO global (username, messages) VALUES (?, ?)";
		
		try {
			con = ds.getConnection();
			
			ps = con.prepareStatement(checkSQL);
			
			ps.setString(1, username);
			
			rs =  ps.executeQuery();
			
			if(rs.next()) {
				if(TwitchStatistics.verbose) System.out.println("Adding one...");
								
				ps = con.prepareStatement(updateSQL);
				ps.setString(1, username);
				ps.executeUpdate();
				
			} else {
				if(TwitchStatistics.verbose) System.out.println("Inserting new...");
								
				ps = con.prepareStatement(insertSQL);
				ps.setString(1, username);
				ps.setInt(2, 1);
				ps.executeUpdate();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
	}
	
	public static String getTopChatterInChannel(String channel) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sendMessage = null;
 
		String checkSQL = "SELECT * FROM " + channel + "_count ORDER BY messages DESC LIMIT 1";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(checkSQL);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				String usernamefd = rs.getString("username");
				int messagesfd = rs.getInt("messages");
				
				sendMessage  = "The top chatter right now is " + usernamefd + " with " + messagesfd + " on record!";
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		return sendMessage;
	}
	
	public static String getTopTenChatterInChannel(String channel) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sendMessage = "Top 10 chatters: ";
		
		String checkSQL = "SELECT * FROM " + channel + "_count ORDER BY messages DESC LIMIT 10";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(checkSQL);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String usernamefd = rs.getString("username");
				int messagesfd = rs.getInt("messages");
				
				sendMessage = sendMessage + usernamefd + " - " + messagesfd + "; ";
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return sendMessage;
	}
	
	public static void addOneEmoteForChannel(String emote, String channel) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String updateSQL = "UPDATE " + channel + "_emotes SET " + emote + " = 1 + " + emote;
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(updateSQL);
			ps.executeUpdate();
			if(TwitchStatistics.verbose) System.out.println("Adding one...");				
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
	}
	
	public static String getEmoteCountInChannel(String channel) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sendMessage = "How often have emotes been used?  ";
		
		String checkSQL = "SELECT * FROM " + channel + "_emotes";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(checkSQL);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {				
				int KappaHD = rs.getInt("KappaHD");
				int FrankerZ = rs.getInt("FrankerZ");
				int Keepo = rs.getInt("Keepo");
				int PJSalt = rs.getInt("PJSalt");
				int Kreygasm = rs.getInt("Kreygasm");
				int SwiftRage = rs.getInt("SwiftRage");
				int FailFish = rs.getInt("FailFish");
				int PogChamp = rs.getInt("PogChamp");
				int Kappa = rs.getInt("Kappa");
				
				sendMessage = sendMessage + "Kappa - " + Kappa + "; FrankerZ - " + FrankerZ + "; KappaHD - " + KappaHD + "; Keepo - " + Keepo + "; PJSalt - " + PJSalt + "; Kreygasm - " + Kreygasm + "; SwiftRage - " + SwiftRage + "; FailFish - " + FailFish + "; PogChamp - " + PogChamp;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return sendMessage;
	}
	
	public static String getTotalMessagesInChannel(String channel) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sendMessage = null;
 
		String checkSQL = "SELECT SUM(messages) FROM " + channel + "_count";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(checkSQL);
			rs = ps.executeQuery();
			
			rs.next();
			int messages = rs.getInt(1);
			sendMessage = channel + " has had a total of " + messages + " messages sent!";
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return sendMessage;
	}
	
	public static String getCountUserChannel(String channel, String username) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sendMessage = null;
		
		String checkSQL = "SELECT messages FROM " + channel + "_count WHERE username = ?";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(checkSQL);
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			rs.next();
			int messages = rs.getInt("messages");
			sendMessage = username + " has sent a total of " + messages + " in " + channel + "'s channel!";
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return sendMessage;
	}
	
	public static String getCountUserGlobal(String username) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sendMessage = null;
		
		String checkSQL = "SELECT messages FROM global WHERE username = ?";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(checkSQL);
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			rs.next();
			int messages = rs.getInt("messages");
			sendMessage = username + " has sent a total of " + messages + " messages in tracked channels!";
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return sendMessage;
	}
	
	public static String getTopGlobalUser() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sendMessage = null;
		
		String checkSQL = "SELECT * FROM global ORDER BY messages DESC LIMIT 1";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(checkSQL);
			rs = ps.executeQuery();
			
			rs.next();
			int messages = rs.getInt("messages");
			String username = rs.getString("username");
			sendMessage = username + " holds the global record with " + messages + " messages in tracked channels!";
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return sendMessage;
	}
	
	public static String getTopChannelUser(String channel) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sendMessage = null;
		
		String checkSQL = "SELECT * FROM " + channel + "_count ORDER BY messages DESC LIMIT 1";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(checkSQL);
			rs = ps.executeQuery();
			
			rs.next();
			int messages = rs.getInt("messages");
			String username = rs.getString("username");
			sendMessage = username + " holds the " + channel + " record with " + messages + " messages in the channel!";
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return sendMessage;
	}
	
	public static void addChannel(String channel) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		
		String createSQL = "CREATE TABLE " + channel + "_count (id INT(11) AUTO_INCREMENT PRIMARY KEY, username VARCHAR(50), messages INT(11))";
		String createSQL2 = "CREATE TABLE " + channel + "_emotes (KappaHD INT(11), FrankerZ INT(11), Keepo INT(11), PJSalt INT(11), Kappa INT(11), Kreygasm INT(11), SwiftRage INT(11), FailFish INT(11), PogChamp INT(11))";
		
		try {
			con = ds.getConnection();
			
			ps = con.prepareStatement(createSQL);
			ps.executeUpdate();
			
			ps2 = con.prepareStatement(createSQL2);
			ps2.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
	}
}
