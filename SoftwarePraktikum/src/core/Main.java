package core;


import java.io.PrintWriter;

import network.Client;
import network.Server;

public class Main {

	public static void main(String[] args){
		new Main(args);
	}
	
	
	public Main(String[] args){
		if(args.length < 3){
			System.out.println("ERROR: Invalid number of args");
			System.exit(1);
		}
			
		if(args[0].equals("-server")){
			initServerGame(args);
		}else if(args[0].equals("-client")){
			initClientGame(args);
		}else if(args[0].equals("-local")){
			initLocalGame(args);
		}else{
			System.out.println("Invalid first Argument \n must be '-server', '-client' or -'local'");
			System.exit(1);
		}
	

	}

	private void initServerGame(String[] args) {
		
	}


		
	private void initClientGame(String[] args) {


	}


	private void initLocalGame(String[] args) {

		
	}


	/**
	 * 
	 * @param name names the type of player
	 * @param p tells witch fraction the player plays
	 * @param ps if null, the function will return an local player
	 * 			if a PrintWriter is given, the player will send each
	 * 			of his moves directly to the printwriter
	 * @return returns a player
	 */
	private PlayerAbs getPlayerFromString(String name, Player p, PrintWriter ps){
		if(name.toUpperCase().equals("-WASP")){
		
			return new Wasp(p);
			
			
		}else if(name.toUpperCase().equals("-SLOTH")){
			
			return new Sloth(p);
		
			
		}else if(name.toUpperCase().equals("-CONSOLEPLAYER")){
			
			return new HumanPlayerConsole(p);
			
		}
		
		return null;
	}
	
}
