

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;


import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

/*==================================================================
 * 
 * This is a program that is a chat bot for Twitch.tv
 * So far it only has simple commands and a simple raffle system
 * 
 * =================================================================
 * 
 * TODO:
 * Point System:
 * 		Data structure
 * 		How to store
 * 		Timers to gain exp
 * 
 * Quote System:
 * 		Data Structure
 * 		How to store
 * 		How to read
 * 		cool down timers
 * 
 * Death Counter
 * Flood check
 * 
 */


public class LurkBot extends PircBot {
	String owner;
	Command c = new Command(owner);	
	boolean isRaffle = false;
	long currentTime = System.currentTimeMillis();
	static long tenTime = 600000;
	long tenMinTimer = currentTime + tenTime;
	
	LinkedList<String> raffle = new LinkedList<String>();
	LinkedList<String> mod = new LinkedList<String>();

	LinkedList<String> users = new LinkedList<String>();


	public LurkBot(String owner, String nickname){
		this.setName(nickname);
		this.owner = owner;
	}


	//Start of methods
	public void onMessage(String channel, String sender,
			String login, String hostname, String message){

		//Disconnects the bot and stops the program from chat
		//Only I can do it
		if(message.startsWith("!disconnect")){
			if(sender.equals(owner)){
				disconnect();
				System.exit(0);
			}
		}

		//Disconnects the bot and reconnects the bot

		if(message.startsWith("!refresh")){
			if(sender.equals(owner)){
				disconnect();
				try {
					reconnect();
				} catch (NickAlreadyInUseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (IrcException e) {
					e.printStackTrace();
				}
			}
		}


		if(c.command(channel, sender, message) != null){

			sendMessage(channel, c.command(channel, sender, message));
		}

		if(currentTime == tenMinTimer){
			sendMessage(channel, c.timerMessage());
			tenMinTimer = tenMinTimer + tenTime;
		}
		
		
		
/*===================================================================================================
 * 							DO NOT EDIT PAST THIS POINT
 ====================================================================================================*/
		//Starts a raffle
		//Only I can do it
		//Might change where mods can also start a raffle
		if(message.startsWith("!raffle")){
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
		if(message.startsWith("!enter")){
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
		if(message.startsWith("!draw")){

			if(sender.equals(owner)){ //Makes it so only I can draw
				isRaffle = false;
				System.err.println("isRaffle: "+isRaffle);

				String[] enteredArray = new String[raffle.size()]; //Creates an array to hold the people who entered

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
	}



	//Method used for the raffle draw system
	//Might use for a !spin???
	public static int anyRandomIntRange(int low, int high) {
		Random random = new Random();
		int randomInt = (random.nextInt(high) + low) + low;
		System.err.println(randomInt);
		return randomInt;
	}




	public void onJoin(String channel, String sender, 
			String login, String hostname, String message){
		users.push(sender);
		System.err.println(sender);


	}

}


