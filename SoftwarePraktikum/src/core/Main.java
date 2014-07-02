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

	/*
	 * Input for Server: 1. -server 2. port 3. (local) player
	 */
	private void initServerGame(String[] args) {
		PlayerAbs p2 = getPlayerFromString(args[2], Player.Cathargo);
		
		int port = Integer.parseInt(args[1]);
		Server bla = new Server();
		bla.initServer(port);
		
		PlayerAbs p1 = new NetworkPlayer(Player.Rom, bla);
	}



	/*
	 * Input for Client: 1. -client 2. port 3. (local)player 4. mapfile
	 */
	private void initClientGame(String[] args) {
		PlayerAbs p1 = getPlayerFromString(args[2], Player.Rom);
	
		
		City_Graph cityGraph = new City_Graph();
		if(!cityGraph.loadMapByPath(args[3])){
			System.out.println(
					"ERROR while loading map. closing Programm");
			System.exit(1);
		}
	
		
		int port = Integer.parseInt(args[1]);
		Client client = new Client();
		client.initClient(args[2], port);

		PlayerAbs p2 = new NetworkPlayer(Player.Cathargo, client);
	}


	
	/*
	 * Input for localgame: 1. -local 2. player1 3. player2 4. mappath
	 */
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
		
		
		City_Graph cityGraph = new City_Graph();
		if(!cityGraph.loadMapByPath(args[3])){
			System.out.println(
					"ERROR while loading map. closing Programm");
			System.exit(1);
		}
		
		new ConsolGame(cityGraph, p1, p2);
	}


	/**
	 * 
	 * @param name names the type of player
	 * @param p tells witch fraction the player plays
	 *
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
