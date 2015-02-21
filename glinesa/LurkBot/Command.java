package glinesa.LurkBot;

import java.io.*;
import java.util.*;


public class Command {
	String owner; // Sets the owner for the bot
    File file = new File("commands.txt");
    HashMap<String, String> command = new HashMap<String, String>();

	//LinkedList<String> ll = new LinkedList<String>();
   // boolean isRaffle = false;
    
    
    
	public Command(String owner){
		this.owner = owner;
	}


	public String timerMessage(String channel) {
		return "Like what you see? Then slap that follow button so you know the next time I go live!";
	}
    
    public void loadCommand(){
        try {


            if (file.createNewFile()){
                System.out.println("File is created!");
            }else{
                System.out.println("File already exists.");
            }
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains(":")) {
                    String[] strings = line.split("=");
                    command.put(strings[0], strings[1]);
                }
            }
            in.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String addCommand(String com){ //substring 9 10 - next_space is name after that is the message
        try{
            FileWriter fw = new FileWriter(file,true); //the true will append the new data
            String [] strings = com.substring(10).split(" ", 2);
            fw.write(strings[0]+":"+strings[1]);//appends the string to the file
            fw.close();
            return "Your command has been added";
        }
        catch(IOException ioe){
            System.err.println("IOException: " + ioe.getMessage());
        }
        return "Error in Command";
        
    }
    
    public String executeCommand(String message){
        for (Map.Entry<String, String> entry : command.entrySet()) {
            if(message.equals(entry.getKey())) {
                //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                return entry.getValue();
            }
            if(message.equals("!addcommmand")){
                addCommand(message);
            }
            if(message.equals("!caster")){
                return "Follow this lovely person at www.twitch.tv/"+message.substring(8);
            }
            
        }
        return null;
    }
    
}
