import java.util.LinkedList;


public class Command {
	String owner; // Sets the owner for the bot

	LinkedList<String> ll = new LinkedList<String>();
	boolean isRaffle = false;
	public Command(String owner){
		this.owner = owner;
	}

	public String command(String channel, String sender, String message){



		/*To add commands:
		 * 
		 * if(message.startsWith(!yourcommand)){
		 * return "what you want the bot to say"
		 * }
		 */
		
		if(message.startsWith("!twitter")){
			return "Follow me on Twitter! www.twitter.com/"+owner;
		}

		//Just a test command
		//Anyone can do it
		if(message.startsWith("!test")){
			return "BOOP This is a test!";
		}

		//A simple caster command
		//Anyone can use it
		if (message.startsWith("!caster")) {
			return "Follow this lovely person at www.twitch.tv/"+message.substring(8);
		}

	
		return null;

	}

	public String timerMessage() {
		return "Timer test";
	}

}
