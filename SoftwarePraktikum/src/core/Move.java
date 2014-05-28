package core;

public class Move {
	
	private Player player;
	private int targetCityID;
	
	
	public Move(Player p, int id) {
		player = p;
		targetCityID = id;
	}
	
	
	public Move(String instruction) {
		Move move = createMoveFromString(instruction);
		player = move.player;
		targetCityID = move.targetCityID;
	}
	
	public int getCityID(){
		return targetCityID;
	}
	
	public Player getPlayer(){
		return player;
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
