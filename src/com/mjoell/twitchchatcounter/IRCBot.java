package com.mjoell.twitchchatcounter;

import org.jibble.pircbot.PircBot;

import com.mjoell.twitchchatcounter.TwitchChatCounter;

public class IRCBot extends PircBot {
	public IRCBot() {
		this.setName(TwitchChatCounter.username);
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		
	}
}