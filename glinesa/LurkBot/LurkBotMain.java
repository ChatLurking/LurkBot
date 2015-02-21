package glinesa.LurkBot;


public class LurkBotMain {
	public static void main (String[] args) throws Exception{
        long currentTime = System.currentTimeMillis();
        final int time = 6000;
        long timerTime = currentTime + time;
        

		//Start up bot
		String owner = "chatlurking"; //must be in lowercase
		String nickname = "LurkingChatBot";
		String password = "oauth:";

        Command c = new Command(owner);
		LurkBot bot = new LurkBot(owner, nickname, password);

		//Enable debugging
		bot.setVerbose(true);

		//connect to irc server
		bot.connect("irc.twitch.tv", 6667, password); // http://twitchapps.com/tmi/

		//joins channel
		bot.joinChannel("#"+owner);

		bot.setMessageDelay(5000); //in milliseconds 1000ms = 1sec
        
        c.loadCommand();

        while (true) {
            if (currentTime == timerTime) {
                bot.TimerMessage(owner);
                bot.addPoints();
                bot.Write();
            }
        }
	}
}