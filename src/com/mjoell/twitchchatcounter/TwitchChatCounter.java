package com.mjoell.twitchchatcounter;

import java.sql.SQLException;

public class TwitchChatCounter {
	public static String username;
	public static String password;
	public static String channel;
	public static String mysqluser;
	public static String mysqlpassword;
	
	public static void main(String args[]) throws Exception {
		if(args.length != 4) {
			System.out.println("You messed up, please fix.");
		} else {
			username = args[0];
			password = args[1];
			channel = "#" + args[2];
			mysqluser = args[3];
			mysqlpassword = "";
						
			TwitchConnect.main();
		}
	}
}
