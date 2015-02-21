package glinesa.LurkBot;

import java.io.*;
import java.util.*;


//STILL VERY MUCH IN DEVLOPMENT

public class PointSystem  {
	File points = new File("points.txt");
	HashMap<String, Integer> point;


	public PointSystem(){
		point = new HashMap<String, Integer>();
		Reader();

	}

	public void addUser(String nick){
		point.put(nick, 0);

	}

	public void isFile() throws IOException{
		try {


			if (points.createNewFile()){
				System.out.println("File is created!");
			}else{
				System.out.println("File already exists.");


			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Reader(){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(points));
			 String line = null;
		        Map<String, Integer> map = new HashMap<String, Integer>();// it should be static - where ever you define
		        while ((line = reader.readLine()) != null) {
		            if (line.contains("=")) {
		                String[] strings = line.split("=");
		                point.put(strings[0], Integer.parseInt(strings[1]));
		            }
		        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}


	public void Writer(){
		/*Properties p = new Properties();
		   Set<Map.Entry<String,Integer>> set = point.entrySet();
		   for (Map.Entry<String, Integer> entry : set) {
		     p.put(entry.getKey(), entry.getValue());
		   }*/
		   

		FileWriter fstream;
		try {
			fstream = new FileWriter(points);
			BufferedWriter out = new BufferedWriter(fstream);

			// create your iterator for your map
			Iterator<Map.Entry<String, Integer>> it = point.entrySet().iterator();

			while (it.hasNext()) {

				Map.Entry<String, Integer> p;
                p = it.next();
                System.out.println(p.getKey()+" " + p.getValue());
				out.write(p.getKey() + "=" + p.getValue()+"\n");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public void addUser(LinkedList<String> users){
		String[] u = new String[users.size()];
		for (int i = 0; i < users.size(); i++){
			u[i] = users.pop();
			point.put(u[i], 0);
		}
	}

	public void addPoints(LinkedList<String> users){
		String[] u = (String[]) users.toArray();
		for(int i = 0; i < u.length; i++){
			int n = point.get(u[i]);
			point.put(u[i], n+1);
		}
	}

	public String getPoints(String sender){
		int n = point.get(sender);
		return sender+" has "+n+" points!";
	}

	public void givePoints(LinkedList<String> users, int n){

		while(!users.isEmpty()){
			String u = users.pop();
			int points = point.get(u);

			if(point.containsKey(u)){		
				point.put(u, points + n);
			}

		}
	}

	public String addPoints(String nick, int n){
		if(point.containsKey(nick)){
			int points = point.get(nick);
			point.put(nick, points + n);
		}
		return "Points have been added";
	}


	/*public static void main(String[] args){
		PointSystem p = new PointSystem();
		//p.Reader();
		p.addUser("Bob");
		p.addUser("Lucas");
		p.addPoints("Bob", 10);
		p.addPoints("Lucas", 40);
        p.addPoints("Bob", 30);
		p.Writer();
	}*/

}
