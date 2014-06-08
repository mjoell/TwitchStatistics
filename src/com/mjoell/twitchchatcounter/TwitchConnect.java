package com.mjoell.twitchchatcounter;

public class TwitchConnect {
	private static IRCBot bot = new IRCBot();
	
	public static void main() throws Exception {
		bot.setVerbose(true);
		bot.connect("irc.twitch.tv", 6667, TwitchChatCounter.password);
		bot.joinChannel(TwitchChatCounter.channel);
		
		bot.sendMessage(TwitchChatCounter.channel, "I'm here!");
	}
}
