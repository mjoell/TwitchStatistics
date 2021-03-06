package com.twitchstatistics.twitchstatistics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class TwitchStatistics {
	public static String username;
	public static String password;
	public static String[] channels;
	public static String[] admins;
	public static String mysqluser;
	public static String mysqlpassword;
	public static String mysqlhost;
	public static String mysqlport;
	public static String mysqldb;
	public static String chanlist;
	public static String adminlist;
	public static boolean verbose;
	
	public static void main(String args[]) throws Exception {
		File file = new File("twitch.conf");
		
		if(!file.exists()) {
			Properties properties = new Properties();
			OutputStream output = null;
			
			try {
				output = new FileOutputStream("twitch.conf");
				
				properties.setProperty("twitchusername", "TwitchUsername");
				properties.setProperty("twitchpassword", "TwitchPassword");
				properties.setProperty("channels", "chan1,chan2,chan3");
				properties.setProperty("admins", "admin1,admin2,admin3");
				properties.setProperty("mysqluser", "MySQLUser");
				properties.setProperty("mysqlpass", "MySQLPassword");
				properties.setProperty("mysqlhost", "MySQLHost");
				properties.setProperty("mysqlport", "MySQLPort");
				properties.setProperty("mysqldb", "MySQLDatabase");
				properties.setProperty("verbose", "false");
				
				properties.store(output, null);
				
				System.out.println("No configuration file existed, one has been generated for you.");
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				output.close();
			}
		} else {
			Properties properties = new Properties();
			InputStream input = null;
			
			try {
				input = new FileInputStream("twitch.conf");
				
				properties.load(input);
				username = properties.getProperty("twitchusername");
				password = properties.getProperty("twitchpassword");
				mysqluser = properties.getProperty("mysqluser");
				mysqlpassword = properties.getProperty("mysqlpass");
				mysqlhost = properties.getProperty("mysqlhost");
				mysqlport = properties.getProperty("mysqlport");
				mysqldb = properties.getProperty("mysqldb");
				verbose = Boolean.parseBoolean(properties.getProperty("verbose"));
				chanlist = properties.getProperty("channels");
				adminlist = properties.getProperty("admins");
				
				channels = chanlist.split(",");
				admins = adminlist.split(",");
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				input.close();
			}
			
			TwitchConnect.main();
		}
	}
	
	public static void addChannel(String channel) throws IOException {
		Properties properties = new Properties();
		InputStream input = null;
		OutputStream output = null;
		
		try {
			input = new FileInputStream("twitch.conf");
			properties.load(input);
			input.close();
			
			output = new FileOutputStream("twitch.conf");
			chanlist = chanlist + "," + channel;
			properties.setProperty("channels", chanlist);
			channels = chanlist.split(",");
			properties.store(output, null);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			output.close();
		}	
	}
}
