package core;

public class Move {
	
	private Player player;
	private int targetCityID;
	
	
	public Move(Player p, int id) {
		player = p;
		targetCityID = id;
	}
	
	
	// maurice
	public Move createMoveFromString(String instruction) {
		
		return null;
	}
	
	
	//Kai
	public String toString(){
		return player == Player.Rom? "R" : "C" +" "+targetCityID;
	}

}
