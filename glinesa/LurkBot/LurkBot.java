package glinesa.LurkBot;

import java.util.LinkedList;
import java.util.Random;

import org.jibble.pircbot.PircBot;

/*==================================================================
 * 
 * This is a program that is a chat bot for Twitch.tv
 * So far it only has simple commands and a simple raffle system
 * This class should not be edited by the user
 * 
 * =================================================================
 * 
 * TODO:
 * Point System:
 * 		Data structure--DONE
 * 		How to store--DONE
 * 		Backup file--DONE
 * 
 * Quote System:--DONE
 * 		Data Structure--DONE
 * 		File to hold them = backup and ability to add--DONE
 * 		How to store--DONE
 * 		How to read--DONE
 * 
 * Death Counter--under development---mostly done
 * Flood check--DONE
 * 
 */


public class LurkBot extends PircBot {
	//Vars
	String owner;
	String password;
	boolean isRaffle = false;
	int death = 0;

	//Structures
	LinkedList<String> raffle = new LinkedList<String>();
	LinkedList<String> mod = new LinkedList<String>();
	LinkedList<String> users = new LinkedList<String>();

	//Class calls
	Command c = new Command(owner);	
	QuoteSystem quote = new QuoteSystem();
    PointSystem ps = new PointSystem();

	//Constructor 
	public LurkBot(String owner, String nickname, String password){
		this.setName(nickname);
		this.owner = owner;
		this.password = password;
	}


	//Start of methods
	public void onMessage(String channel, String sender,
			String login, String hostname, String message){



		//Death counter
		//anyone can do it
		//Will make adding and subtracting Mod only in the future
		if(message.equalsIgnoreCase("!death +")){
			death++;
			sendMessage(channel, "Deaths: "+death);

		}
		if(message.equalsIgnoreCase("!death -")){
			death--;
			sendMessage(channel, "Deaths: "+death);

		}
		if(message.equalsIgnoreCase("!deaths")){
			sendMessage(channel, "Deaths: "+death);
		}
		
/*================================================================================================================
 * 										DO NOT EDIT PAST THIS POINT
 * 										THIS STUFF WORKS
 * 										DO NOT EDIT
================================================================================================================*/
		//Sees if message is a command from Command.java
        if(message.substring(0,1).contains("!")) {
            if (c.executeCommand(message) != null) {
                sendMessage(channel, c.executeCommand(message));
            }
        }
		
		if(message.equalsIgnoreCase("!addquote")){
			quote.addQuote(message.substring(10));
			sendMessage(channel, "You're quote has been added");
		}
		
		if(message.equalsIgnoreCase("!quote")){
			sendMessage(channel, quote.Quote());
		}

		//Starts a raffle
		//Only I can do it
		//Might change where mods can also start a raffle
		if(message.equalsIgnoreCase("!raffle")){
			if(sender.equals(owner)){
				isRaffle = true;
				System.err.println("isRaffle: "+isRaffle);
				sendMessage(channel, "A raffle has been started! Type '!enter' to enter.");

			}else{
				//Right now its if anyone but me tries to start a raffle
				sendMessage(channel,"You are not the boss of me!");
			}
		}

		//Command used for people to enter a raffle
		//Anyone can use it
		if(message.equalsIgnoreCase("!enter")){
			if(isRaffle = true){	
				raffle.push(sender);
				String person = raffle.pop();
				System.err.println(person);
				raffle.push(person);
			}
		}
		System.err.println("isRaffle: "+isRaffle);


		//Used to draw a winner for the raffle
		//Only I can use it
		//Might change so mods can also draw a winner
		if(message.equalsIgnoreCase("!draw")){

			if(sender.equals(owner)){ //Makes it so only I can draw
				isRaffle = false;
				System.err.println("isRaffle: "+isRaffle);

				String[] enteredArray = new String[raffle.size()]; // an array to hold the people who entered

				for(int i = 0; i < raffle.size(); i++){ //puts people from the linked list into the array
					enteredArray[i] = raffle.pop();
				}
				int n = anyRandomIntRange(0, enteredArray.length); //Random number to be used to choose winner
				//Makes sure that the random number isn't going to return a name that is null
				while(enteredArray[n] == null){ 
					n = anyRandomIntRange(0, enteredArray.length);
				}
				//Outputs the winner into chat
				sendMessage(channel, enteredArray[n]+" has won the raffle!");
				//System.out.println(a[n]+" has won!");
			}else{
				sendMessage(channel, "You are not the boss of me!");
			}

		}




		//Disconnects the bot and stops the program from chat
		//Only I can do it
		if(message.equalsIgnoreCase("!disconnect")){
			if(sender.equals(owner)){
				disconnect();
                ps.Writer();
                
				System.exit(0);
			}
		}

	}

    public void TimerMessage(String channel){

        sendMessage(channel, c.timerMessage(channel));
    }

	//Method used for the raffle draw system
	//Might use for a !spin???
	public static int anyRandomIntRange(int low, int high) {
		Random random = new Random();
		int randomInt = (random.nextInt(high) + low) + low;
		System.err.println(randomInt);
		return randomInt;
	}

	public LinkedList<String> getUserList(){
		
		return users;
	}


	public void onOp( String recipient){

		mod.push(recipient);
		/*while(mod.peek() != null){
			System.err.println("Mod: "+mod.pop());
		}*/

	}
	public void onJoin(String channel, String sender, String login, String hostname){
		onOp(sender);
		users.push(sender);
		//System.err.println(sender+" has been added to list");

	}

	public void onPart(String channel, String sender, String login, String hostname){
		System.err.println("onPart method");
		mod.removeFirstOccurrence(sender);
		users.removeFirstOccurrence(sender);
		//System.err.println(sender+"has been removed");

	}
    public void addPoints(){
        ps.addPoints(users);
        
    }
    public void Write(){
        ps.Writer();
        
    }


	//Doesn't look like twitch uses onQuit method
	/*public void onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason){
		System.err.println("onQuit method");
		mod.removeFirstOccurrence(sourceNick);
		users.removeFirstOccurrence(sourceNick);
		System.err.println(sourceNick+"has been removed");

	}*/

}


