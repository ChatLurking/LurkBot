

public class LurkBotMain {
	public static void main (String[] args) throws Exception{
		//Start up bot
		String owner = "the channel you want the bot to join"; //must be in lowercase
		String nickname = "Bot name here";
		
		LurkBot bot = new LurkBot(owner, nickname);

		//Enable debugging
		bot.setVerbose(true);

		//connect to irc server
		bot.connect("irc.twitch.tv", 6667, "oauth:"); // http://twitchapps.com/tmi/

		//joins channel
		bot.joinChannel("#"+owner);

		

	}
}