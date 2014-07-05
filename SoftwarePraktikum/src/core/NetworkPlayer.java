package core;

import java.io.BufferedReader;
import java.io.IOException;

import network.NetworkIOHandler;

public class NetworkPlayer extends PlayerAbs{

	NetworkIOHandler network = null;
		
	public NetworkPlayer(Player name, NetworkIOHandler network) {
		super(name);
		
		this.network = network;
		
	}

	@Override
	public Move makeMove(City_Graph city_graph, History h, Move prevMove) {
		
		if(prevMove != null){
			network.sendMove(prevMove);
		}
		
		
		
		Player p = null;
		String input = null;
		try {
			input = network.readMove();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		if(input == null || input.split(" ").length != 2){
			System.out.println("Wrong move recieved");
			System.out.println(input);
			return null;
		}
		if(input.split(" ")[0].equals("R")){
			p = Player.Rom;
		}else{
			p = Player.Cathargo;
		}
		
		return new Move(p, Integer.parseInt(input.split(" ")[1]));
	}

}
