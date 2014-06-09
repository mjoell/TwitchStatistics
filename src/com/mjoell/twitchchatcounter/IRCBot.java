package com.mjoell.twitchchatcounter;

import java.sql.SQLException;

import org.jibble.pircbot.PircBot;

import com.mjoell.twitchchatcounter.TwitchChatCounter;

public class IRCBot extends PircBot {
	public IRCBot() {
		this.setName(TwitchChatCounter.username);
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		try {
			Mysql.addOneForUserInChannel(channel.replace("#", ""), sender);
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
	}
}