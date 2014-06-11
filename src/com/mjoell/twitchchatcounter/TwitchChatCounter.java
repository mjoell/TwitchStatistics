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
	public static String channel;
	public static String mysqluser;
	public static String mysqlpassword;
	
	public static void main(String args[]) throws Exception {
		File file = new File("twitch.conf");
		
		if(!file.exists()) {
			Properties properties = new Properties();
			OutputStream output = null;
			
			try {
				output = new FileOutputStream("twitch.conf");
				
				properties.setProperty("twitchusername", "TwitchUsername");
				properties.setProperty("twitchpassword", "TwitchPassword");
				properties.setProperty("channel", "TwitchChannel");
				properties.setProperty("mysqluser", "MySQLUser");
				properties.setProperty("mysqlpass", "MySQLPassword");
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
				channel = properties.getProperty("channel");
				mysqluser = properties.getProperty("mysqluser");
				mysqlpassword = properties.getProperty("mysqlpassword");
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				input.close();
			}
			
			TwitchConnect.main();
		}
	}
}
