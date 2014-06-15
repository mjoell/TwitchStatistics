package com.mjoell.twitchchatcounter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class TwitchChatCounter {
	public static String username;
	public static String password;
	public static String[] channels;
	public static String mysqluser;
	public static String mysqlpassword;
	public static String chanlist;
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
				properties.setProperty("channels", "1,2,3");
				properties.setProperty("mysqluser", "MySQLUser");
				properties.setProperty("mysqlpass", "MySQLPassword");
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
				verbose = Boolean.parseBoolean(properties.getProperty("verbose"));
				chanlist = properties.getProperty("channels");
				
				String channelsb4 = properties.getProperty("channels");
				channels = channelsb4.split(",");
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
		OutputStream output = null;
		
		try {
			output = new FileOutputStream("twitch.conf");
			
			properties.setProperty("channels", channels + "," + channel);
			
			properties.store(output, null);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			output.close();
		}	
	}
}
