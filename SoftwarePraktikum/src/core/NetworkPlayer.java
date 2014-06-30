package core;

import java.io.BufferedReader;
import java.io.IOException;

public class NetworkPlayer extends PlayerAbs{

	private BufferedReader fromNetwork = null;
	
	public NetworkPlayer(Player name, BufferedReader fromNetwork) {
		super(name);
		
		this.fromNetwork = fromNetwork;
		
	}

	@Override
	public Move makeMove(City_Graph city_graph) {
		
		Player p = null;
		String input = null;
		try {
			input = fromNetwork.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(input.split(" ")[0].equals("R")){
			p = Player.Rom;
		}else{
			p = Player.Cathargo;
		}
		
		return new Move(p, Integer.parseInt(input.split(" ")[1]));
	}

}
