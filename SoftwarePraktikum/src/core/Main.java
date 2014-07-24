package core;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import GUI.Board;
import network.Client;
import network.NetworkIOHandler;
import network.Server;

public class Main {

	public static void main(String[] args){
		String[] test1 = {"-server","7762","-PriorityMan"};
		String[] test2 = {"-client","7762","-PriorityMan","res/bigmap.mp","localhost"};
		String[] test3 = {"-local","-guiplayer","-consoleplayer","res/bigmap.mp"};
		new Main(test2);
	}
	
	
	public Main(String[] args){
		if(args.length < 3){
			System.out.println("ERROR: Invalid number of args");
			System.exit(0);
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
		
		
		int port = Integer.parseInt(args[1]);
		Server server = new Server();
		City_Graph cityGraph = new City_Graph();
		
		try {
			cityGraph.loadMapByStrings(server.initServer(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Board gameBoard = new Board(cityGraph);
		
		
		PlayerAbs p2 = getPlayerFromString(args[2], Player.Cathargo, gameBoard);
		
		PlayerAbs p1 = new NetworkPlayer(Player.Rom, server);
		
		
		new ConsolGame(gameBoard, cityGraph, p1, p2, true);
		
		
		try {
			server.endConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/*
	 * Input for Client: 1. -client 2. port 3. (local)player 4. mapfile
	 */
	private void initClientGame(String[] args) {
		
		
		ArrayList<String> mapdata = this.readMapFile(args[3]);
		
		
		City_Graph cityGraph = new City_Graph();
		if(!cityGraph.loadMapByPath(args[3])){
			System.out.println(
					"ERROR while loading map. closing Programm");
			System.exit(0);
		}
		Board gameBoard = new Board(cityGraph);
		
		
		PlayerAbs p1 = getPlayerFromString(args[2], Player.Rom, gameBoard);
		
		if(mapdata == null || p1 == null){
			System.exit(0);
		}

		
		int port = Integer.parseInt(args[1]);
		Client client = new Client();
		
		
		
		
		try {
			client.initClient(mapdata, port,args[4]);
		} catch (IOException e) {
		
			e.printStackTrace();
		}

		PlayerAbs p2 = new NetworkPlayer(Player.Cathargo, client);
		
		new ConsolGame(gameBoard, cityGraph, p1, p2, true);
		
		client.endConection();
	}


	
	/*
	 * Input for localgame: 1. -local 2. player1 3. player2 4. mappath
	 */
	private void initLocalGame(String[] args) {

		City_Graph cityGraph = new City_Graph();
		if(!cityGraph.loadMapByPath(args[3])){
			System.out.println(
					"ERROR while loading map. closing Programm");
			System.exit(1);
		}
		Board gameBoard = new Board(cityGraph);
		
		
		PlayerAbs p1 = getPlayerFromString(args[1], Player.Rom, gameBoard);
		if(p1 == null){
			System.out.println("Wrong first player");
			System.exit(1);
		}
		PlayerAbs p2 = getPlayerFromString(args[2], Player.Cathargo,gameBoard);
		if(p2 == null){
			System.out.println("Wrong second player");
			System.exit(1);
		}
		
		System.out.println("Player1: " + p1.getClass().getName());
		System.out.println("Player2: " + p2.getClass().getName());	
		
		
		new ConsolGame(gameBoard, cityGraph, p1, p2, true);
	}


	/**
	 * 
	 * @param name names the type of player
	 * @param p tells witch fraction the player plays
	 *
	 * @return returns a player
	 */
	public static PlayerAbs getPlayerFromString(String name, Player p, Board b){
		if(name.toUpperCase().equals("-WASP")){
		
			return new Wasp(p);
			
			
		}else if(name.toUpperCase().equals("-SLOTH")){
			
			return new Sloth(p);
		
			
		}else if(name.toUpperCase().equals("-CONSOLEPLAYER")){
			
			return new HumanPlayerConsole(p);
			
		}else if(name.toUpperCase().equals("-SCOOGE")){
			
			return new Scooge(p);
		
		}else if(name.toUpperCase().equals("-KILLJOY")){
		
			return new Killjoy(p);
			
		}else if(name.toUpperCase().equals("-SCOOGEJOY")){
		
			return new ScoogeJoy(p);
			
		}else if(name.toUpperCase().equals("-KIEASYJ")){
			
			return new KIEasyJ(p);
		}else if(name.toUpperCase().equals("-KINORMALJ")){
			
			return new KINormalJ(p);
		}else if(name.toUpperCase().equals("-KIJH")){
			
			return new KIJH(p);
		}else if(name.toUpperCase().equals("-GUIPLAYER")){
			return new GUIPlayer(p,b);
		}else if(name.toUpperCase().equals("-PRIORITYMAN")){
			return new PriorityMan(p);
		}
		
		return null;
	}
	
	public ArrayList<String> readMapFile(String mappath){
		BufferedReader fileReader = null;
		String line = null;

		ArrayList<String> mapdata =  new ArrayList<>();
		
		try {
			fileReader = new BufferedReader(new FileReader(mappath));
			
			while((line = fileReader.readLine()) != null){
				mapdata.add(line);
			}
			
			fileReader.close();
		
		} catch (FileNotFoundException e) {
			System.out.println("Failed to read File");
			mapdata = null;
		} catch(IOException e) {
			System.out.println("Failed to read File");
			mapdata = null;
		}
		
		
		return mapdata;
	}
}
