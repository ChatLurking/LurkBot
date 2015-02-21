package glinesa.LurkBot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;


/*
 * 
 * Create or find file if it already exists
 * read file into a linked list
 * print out random quote
 * add quote
 * 
 * 
 */

public class QuoteSystem {
	LinkedList <String> quote = new LinkedList<String>();
	String[] quotes;
	File q = new File("quotes.txt");

	public QuoteSystem(){
		try {
			Reader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addQuote(String message){
        if(message != null){
		    quote.push(message);
		//System.err.println(quote.pop());
			Writer(message+"\n");
		}
	}

	public String Quote(){
		quotes  = new String[quote.size()];
		for (int i = 0; i < quote.size(); i++){
			quotes[i] = quote.pop();
		}

		int random = anyRandomIntRange(0,quotes.length);
		while(quotes[random] == null){
			random = anyRandomIntRange(0,quotes.length);
		}

		for(int i = 0; i < quotes.length; i++){
			quote.push(quotes[i]);
		}
		return quotes[random];
	}


	public void Reader() throws IOException{
		try {


			if (q.createNewFile()){
				System.out.println("File is created!");
			}else{
				System.out.println("File already exists.");
			}
			BufferedReader in = new BufferedReader(new FileReader(q));

			while (in.ready()) {
				String s = in.readLine();
				quote.push(s);
			}
			in.close();


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void Writer(String message){
		try{
			FileWriter fw = new FileWriter(q,true); //the true will append the new data
			fw.write(message);//appends the string to the file
			fw.close();
		}
		catch(IOException ioe){
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	public int anyRandomIntRange(int low, int high) {
		Random random = new Random();
		int randomInt = (random.nextInt(high) + low) + low;
		System.err.println(randomInt);
		return randomInt;
	}

/*	public static void main(String[] args) throws IOException{
		QuoteSystem q = new QuoteSystem();
		q.addQuote(null);
		q.addQuote("abcdefg");
		q.addQuote("qwegadsf");
		System.out.println(q.Quote());
	}*/

}
