package com.mjoell.twitchchatcounter;

public class TwitchConnect {
	private static IRCBot bot = new IRCBot();
	
	public static void main() throws Exception {
		if(TwitchChatCounter.verbose) {
			bot.setVerbose(true);
		} else {
			bot.setVerbose(false);
		}
		bot.connect("irc.twitch.tv", 6667, TwitchChatCounter.password);
		bot.joinChannel(TwitchChatCounter.channel);
	}
}
