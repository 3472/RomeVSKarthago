package core;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import network.Client;
import network.NetworkIOHandler;
import network.Server;

public class Main {

	public static void main(String[] args){
		String[] test1 = {"-server","7762","-sloth"};
		String[] test2 = {"-client","7762","-sloth","res/test2.mp"};
		String[] test3 = {"-local","-sloth","-sloth","res/test_zettel2_1.mp"};
		String[] test4 = {"-local","-scooge","-scooge","res/test_zettel2_1.mp"};
		String[] test5 = {"-local","-scooge","-scoogejoy","res/test_zettel2_1.mp"};
		new Main(test5);
	}
	
	
	public Main(String[] args){
		if(args.length < 3){
			System.out.println("ERROR: Invalid number of args");
			System.exit(1);
		}
			
		if(args[0].equals("-server")){
			try {
				initServerGame(args);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
	private void initServerGame(String[] args) throws IOException {
		PlayerAbs p2 = getPlayerFromString(args[2], Player.Cathargo);
		
		int port = Integer.parseInt(args[1]);
		Server server = new Server();
		City_Graph cityGraph = new City_Graph();
		cityGraph.loadMapByStrings(server.initServer(port));
		
		PlayerAbs p1 = new NetworkPlayer(Player.Rom, server);
		
		
		ConsolGame cg = new ConsolGame(cityGraph, p1, p2);
		server.sendMove(cg.getFinalMove());
		server.endConnection();
	}



	/*
	 * Input for Client: 1. -client 2. port 3. (local)player 4. mapfile
	 */
	private void initClientGame(String[] args) {
		
		
		ArrayList<String> mapdata = this.readMapFileAndMove(args[3]);
		
		
		PlayerAbs p1 = getPlayerFromString(args[2], Player.Rom);
		
		if(mapdata == null || p1 == null){
			System.exit(1);
		}

	
		
		City_Graph cityGraph = new City_Graph();
		if(!cityGraph.loadMapByPath(args[3])){
			System.out.println(
					"ERROR while loading map. closing Programm");
			System.exit(1);
		}
	
		
		int port = Integer.parseInt(args[1]);
		Client client = new Client();
		
		
		
		
		try {
			client.initClient(mapdata, port);
		} catch (IOException e) {
		
			e.printStackTrace();
		}

		PlayerAbs p2 = new NetworkPlayer(Player.Cathargo, client);
		
		ConsolGame cg = new ConsolGame(cityGraph, p1, p2);
		client.sendMove(cg.getFinalMove());
		client.endConection();
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
			
		}else if(name.toUpperCase().equals("-SCOOGE")){
			
			return new Scooge(p);
		
		}else if(name.toUpperCase().equals("-KILLJOY")){
		
			return new Killjoy(p);
			
		}else if(name.toUpperCase().equals("-SCOOGEJOY")){
		
			return new ScoogeJoy(p);
			
		}
		
		return null;
	}
	
	private ArrayList<String> readMapFileAndMove(String mappath){
		BufferedReader fileReader = null;
		String line = null;

		ArrayList<String> mapdata =  new ArrayList<>();
		
		try {
			fileReader = new BufferedReader(new FileReader(mappath));
			
			while((line = fileReader.readLine()) != null){
				mapdata.add(line);
			}
		
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
