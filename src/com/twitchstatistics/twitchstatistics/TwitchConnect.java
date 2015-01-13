package com.twitchstatistics.twitchstatistics;

public class TwitchConnect {
	private static IRCBot bot = new IRCBot();
	
	public static void main() throws Exception {
		if(TwitchChatCounter.verbose) {
			bot.setVerbose(true);
		} else {
			bot.setVerbose(false);
		}
		bot.connect("irc.twitch.tv", 6667, TwitchChatCounter.password);
		System.out.println("Connected to irc.twitch.tv");
		
		for(int i = 0; i < TwitchChatCounter.channels.length; i++) {
			bot.joinChannel("#" + TwitchChatCounter.channels[i]);
			System.out.println("Joined channel " + TwitchChatCounter.channels[i] + ".  Waiting 4 seconds before joining next.");
			Thread.sleep(4000);
		}
		bot.joinChannel("#uberfacts");
		System.out.println("Joined channel uberfacts.  Waiting 4 seconds before joining next.");
		Thread.sleep(4000);

		System.out.println("Joined all channels.  Assuming you're not in verbose, this is the last you'll hear from us for a while.");
	}
	
	public static void joinNew(String channel) {
		bot.joinChannel(channel);
		System.out.println("Joined new channel " + channel);
	}
}
