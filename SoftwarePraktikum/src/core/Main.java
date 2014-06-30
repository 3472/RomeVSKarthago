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
		Server server = new Server();
		PlayerAbs p2 = getPlayerFromString(args[1], Player.Cathargo, server.getToClientPrinter());
		if(p2 == null){
			System.out.println("Wrong first player");
			System.exit(1);
		}
		
		PlayerAbs p1 = new NetworkPlayer(Player.Rom, server.getFromClientReader());
		
		new ConsolGame(args[2], p1 ,p2);
	}


		
	private void initClientGame(String[] args) {
		Client client = new Client();
		PlayerAbs p1 = getPlayerFromString(args[1], Player.Rom, client.getToServerPrinter());
		if(p1 == null){
			System.out.println("Wrong first player");
		}
		
		PlayerAbs p2 = new NetworkPlayer(Player.Cathargo, client.getFromServerReader());
		
		new ConsolGame(args[2], p1 ,p2);
	}


	private void initLocalGame(String[] args) {
		PlayerAbs p1 = getPlayerFromString(args[1], Player.Rom, null);
		if(p1 == null){
			System.out.println("Wrong first player");
			System.exit(1);
		}
		PlayerAbs p2 = getPlayerFromString(args[2], Player.Cathargo, null);
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
	private PlayerAbs getPlayerFromString(String name, Player p, PrintWriter ps){
		if(name.toUpperCase().equals("-WASP")){
			
			if(ps == null){
				return new Wasp(p);
			}else{
				return new Wasp(p, ps);
			}
			
		}else if(name.toUpperCase().equals("-SLOTH")){
			
			if(ps == null){
				return new Sloth(p);
			}else{
				return new Sloth(p, ps);
			}
			
		}else if(name.toUpperCase().equals("-CONSOLEPLAYER")){
			
			if(ps == null){
				return new HumanPlayerConsole(p);
			}else{
				return new HumanPlayerConsole(p, ps);
			}
			
		}
		
		return null;
	}
	
}
