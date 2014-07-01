package core;


import java.io.PrintWriter;

import network.Client;
import network.NetworkIOHandler;
import network.Server;

public class Main {

	public static void main(String[] args){
		new Main(args);
	}
	
	
	public Main(String[] args){
		if(args.length < 2){
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
		PlayerAbs p2 = getPlayerFromString(args[1], Player.Cathargo);
		
		Server bla = new Server();
		bla.initServer();
		
		PlayerAbs p1 = new NetworkPlayer(Player.Rom, bla);
	}


		
	private void initClientGame(String[] args) {
		PlayerAbs p1 = getPlayerFromString(args[1], Player.Rom);
	
		Client client = new Client();
		client.initClient(args[2]);

		PlayerAbs p2 = new NetworkPlayer(Player.Cathargo, client);
	}


	private void initLocalGame(String[] args) {

		PlayerAbs p1 = getPlayerFromString(args[1], Player.Rom);
		if(p1 == null){
			System.out.println("Wrong first player");
			System.exit(1);
		}
		PlayerAbs p2 = getPlayerFromString(args[2], Player.Cathargo);
		if(p2 == null){
			System.out.println("Wrong second player");
			System.exit(1);
		}
		
		System.out.println("Player1: " + p1.getClass().getName());
		System.out.println("Player2: " + p2.getClass().getName());	
		
		
		new ConsolGame(args[3], p1, p2);
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
	private PlayerAbs getPlayerFromString(String name, Player p){
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
