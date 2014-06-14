package com.mjoell.twitchchatcounter;

import java.sql.SQLException;

import org.jibble.pircbot.PircBot;

public class IRCBot extends PircBot {
	public static String topChatter;
	
	public IRCBot() {
		this.setName(TwitchChatCounter.username);
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		try {
			if(!sender.toLowerCase().equals("nightbot") && !sender.toLowerCase().equals("moobot") && !sender.toLowerCase().equals("uberfacts") && !channel.toLowerCase().equals("#uberfacts")) {
				Mysql.addOneForUserInChannel(channel.replace("#", ""), sender);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(sender.toLowerCase().equals("pirateeeeeee") && message.toLowerCase().equals("uberfacts, topchatter")) {
			try {
				sendMessage(channel, Mysql.getTopChatterInChannel(channel.replace("#", "")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(sender.toLowerCase().equals("pirateeeeeee") && message.toLowerCase().equals("uberfacts, top10chatters")) {
			try {
				sendMessage(channel, Mysql.getTopTenChatterInChannel(channel.replace("#", "")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(sender.toLowerCase().equals("pirateeeeeee") && message.toLowerCase().equals("uberfacts, emotes")) {
			try {
				sendMessage(channel, Mysql.getEmoteCountInChannel(channel.replace("#", "")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		
		if(channel.toLowerCase().equals("#uberfacts")) {
			if(message.toLowerCase().equals("!help")) {
				sendMessage(channel, "Please read the channel help panel to the left for help information.");
			}
			if(message.toLowerCase().startsWith("!lookupuser")) {
				//TODO Lookup stats on a particular user in global database, or in channel database.  Will come back here tomorrow.
			}
			if(message.toLowerCase().startsWith("!lookupchannel")) {
				if(message.toLowerCase().equals("!lookupchannel")) {
					sendMessage(channel, "This command requires parameters: !lookupchannel <channel>");
					return;
				}
				
				String[] command = message.split(" ");
				try {
					sendMessage(channel, command[1] + " has had a total of " + Mysql.getTotalMessagesInChannel(command[1]));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(message.toLowerCase().equals("!globaltop")) {
				// TODO
			}
			if(message.toLowerCase().equals("!topchan")) {
				// TODO
			}
			// TODO fill out more here as ideas arise
		}
	}
}