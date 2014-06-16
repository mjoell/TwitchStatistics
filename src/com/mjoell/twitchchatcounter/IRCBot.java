package com.mjoell.twitchchatcounter;

import java.io.IOException;
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
				Mysql.addOneForUserGlobal(sender);
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
				if(message.toLowerCase().equals("!lookupuser")) {
					sendMessage(channel, "This command requires parameters: !lookupuser <username> | !lookupuser <username> <channel>");
					return;
				}
				
				String[] command = message.split(" ");
				if(command.length == 2) {
					try {
						sendMessage(channel, Mysql.getCountUserGlobal(command [1]));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(command.length == 3) {
					try {
						sendMessage(channel, Mysql.getCountUserChannel(command[2], command[1]));
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return;
				}
			}
			if(message.toLowerCase().startsWith("!lookupchannel")) {
				if(message.toLowerCase().equals("!lookupchannel")) {
					sendMessage(channel, "This command requires parameters: !lookupchannel <channel>");
					return;
				}
				
				String[] command = message.split(" ");
				try {
					String total = Mysql.getTotalMessagesInChannel(command[1]);
					if(total.contains("null")) {
						sendMessage(channel, "An error has occured.  Most likely, the channel " + command[1] + " is not being watched, and no data on it exists.  If not, check back later and it should be fixed.");
					} else {
						sendMessage(channel, total);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(message.toLowerCase().equals("!topglobal")) {
				try {
					sendMessage(channel, Mysql.getTopGlobalUser());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(message.toLowerCase().startsWith("!topchan")) {
				if(message.toLowerCase().equals("!topchan")) {
					sendMessage(channel, "This command requires parameters: !topchan <channel>");
				}
				
				String[] command = message.split(" ");
				try {
					sendMessage(channel, Mysql.getTopChannelUser(command[1]));
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			// TODO fill out more here as ideas arise
			
			if(message.toLowerCase().equals("!join")) {
				String chan = sender;
				
				for(int i = 0; i < TwitchChatCounter.channels.length; i++) {
					if(chan.equals(TwitchChatCounter.channels[i])) {
						sendMessage(channel, "I am already in that channel.");
						return;
					}
				}
				
				sendMessage(channel, "I will join " + chan + " within the next 5 seconds.  Please wait while I setup a database for you.");
				try {
					TwitchChatCounter.addChannel(chan);
					TwitchConnect.joinNew("#" + chan);
					Mysql.addChannel(chan);
					Thread.sleep(2000);
					sendMessage(chan, "Hi!  I have joined and will begin tracking your channel now!");					
				} catch (IOException|SQLException|InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}