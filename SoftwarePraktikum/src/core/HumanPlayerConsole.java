package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class HumanPlayerConsole extends PlayerAbs {
	
	private BufferedReader consoleReader;



	public HumanPlayerConsole(Player name) {
		super(name);
		consoleReader = new BufferedReader(new InputStreamReader(System.in));
		
	}
	
	
	private void errorTryAgain(String msg){
		System.out.println(msg);
		System.out.println("Try Again");
	}
	
	/**
	 * waits for an userinput and trys to turn it into an Move
	 * if the input is wrong, the move will remain 
	 * 
	 * @return 
	 */
	public Move makeMove(City_Graph city_graph, History h, Move prevMove){
		Move move = null;
		String input = "";
		
		try {
			input = consoleReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String arguments[] = input.split(" ");
		
		
		// wrong amount of Arguments
		if(arguments.length != 2){
			errorTryAgain("Invalide number of Arguments");
		}else{
			// First Tag is Wrong
			if(!(arguments[0].equals("R") || arguments[0].equals("C"))){
				errorTryAgain("First Argument has to be the playertag");
			}else{
				Player p = null;
				if(arguments[0].equals("R")){
					p = Player.Rom;
				}else{
					p = Player.Cathargo;
				}
				
				// Wrong Player entered
				if(p != this.name){
					errorTryAgain("Wrong Player");
				}else{
					
					// (player forfeits)
					if(arguments[1].equals("X")){
						forfeits = true;
						return new Move(this.name, -1 );
					}else{
						int id = -1;
						try{
							id = Integer.parseInt(arguments[1]);
							
							// input seems right
							// add move
							
							if(city_graph.getCity(id).getOwner() != Owner.Neutral){
								errorTryAgain("City is Already Captured");
							}else{
								move = new Move(arguments[0] + " " + arguments[1]);
								if(move != null){
									
								}
							}
							
						}catch(NumberFormatException ex){
							errorTryAgain("Last Argument is not a number nor an 'X'");
						}
					}
				}
			}
		}
		
		return move;
	}
}
