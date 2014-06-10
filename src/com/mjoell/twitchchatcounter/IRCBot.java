package com.mjoell.twitchchatcounter;

import java.sql.SQLException;

import org.jibble.pircbot.PircBot;

import com.mjoell.twitchchatcounter.TwitchChatCounter;

public class IRCBot extends PircBot {
	public static String topChatter;
	
	public IRCBot() {
		this.setName(TwitchChatCounter.username);
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		try {
			if(!sender.toLowerCase().equals("nightbot") || !sender.toLowerCase().equals("moobot")) {
				Mysql.addOneForUserInChannel(channel.replace("#", ""), sender);
			}
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
		
		if(sender.toLowerCase().equals("pirateeeeeee") && message.toLowerCase().equals("uberfacts, topchatter")) {
			String topChatter = null;
			try {
				topChatter = Mysql.getTopChatterInChannel(TwitchChatCounter.channel.replace("#", ""));
			} catch(SQLException e) {
				System.out.println(e.getStackTrace());
			}
			sendMessage(TwitchChatCounter.channel, topChatter);
		}
		
		if(sender.toLowerCase().equals("pirateeeeeee") && message.toLowerCase().equals("uberfacts, top10chatters")) {
			String topChatter = null;
			try {
				topChatter = Mysql.getTopTenChatterInChannel(TwitchChatCounter.channel.replace("#", ""));
			} catch(SQLException e) {
				System.out.println(e.getStackTrace());
			}
			sendMessage(TwitchChatCounter.channel, topChatter);
		}
		
		String[] CommonEmotes = new String[]{"KappaHD", "FrankerZ", "Keppo", "PJSalt", "Kappa", "Kreygasm", "SwiftRage", "FailFish", "PogChamp"};
		
		for(int i = 0; i < CommonEmotes.length; i++) {
			if(message.contains(CommonEmotes[i])) {
				try {
					Mysql.addOneEmoteForChannel(CommonEmotes[i], channel);
				} catch (SQLException e) {
					System.out.println(e.getStackTrace());
				}
			}
		}
	}
}