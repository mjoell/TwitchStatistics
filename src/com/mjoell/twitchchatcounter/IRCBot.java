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
			e.printStackTrace();
		}
		
		if(sender.toLowerCase().equals("pirateeeeeee") && message.toLowerCase().equals("uberfacts, topchatter")) {
			String topChatter = null;
			try {
				topChatter = Mysql.getTopChatterInChannel(channel.replace("#", ""));
			} catch(SQLException e) {
				e.printStackTrace();
			}
			sendMessage(channel, topChatter);
		}
		
		if(sender.toLowerCase().equals("pirateeeeeee") && message.toLowerCase().equals("uberfacts, top10chatters")) {
			String topChatter = null;
			try {
				topChatter = Mysql.getTopTenChatterInChannel(channel.replace("#", ""));
			} catch(SQLException e) {
				e.printStackTrace();
			}
			sendMessage(channel, topChatter);
		}
		
		if(sender.toLowerCase().equals("pirateeeeeee") && message.toLowerCase().equals("uberfacts, emotes")) {
			String emotes = null;
			try {
				emotes = Mysql.getEmoteCountInChannel(channel.replace("#", ""));
			} catch(SQLException e) {
				e.printStackTrace();
			}
			sendMessage(channel, emotes);
		}
		
		String[] CommonEmotes = new String[]{"KappaHD", "FrankerZ", "Keepo", "PJSalt", "Kappa", "Kreygasm", "SwiftRage", "FailFish", "PogChamp"};
		
		for(int i = 0; i < CommonEmotes.length; i++) {
			if(message.contains(CommonEmotes[i])) {
				try {
					Mysql.addOneEmoteForChannel(CommonEmotes[i], channel.replace("#", ""));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}