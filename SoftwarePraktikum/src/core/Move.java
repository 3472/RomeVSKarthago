package core;

public class Move {
	
	private Player player;
	private int targetCityID;
	private boolean illigalMove;
	
	
	public Move(Player p, int id) {
		player = p;
		targetCityID = id;
		illigalMove = id < 0;
	}
	
	
	public Move(String instruction) {
		Move move = createMoveFromString(instruction);				
	
		this.player = move.player;
		this.targetCityID = move.targetCityID; 
	}
	
	public int getCityID(){
		return targetCityID;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	// maurice
	public Move createMoveFromString(String instruction) {
		
		String[] Split = instruction.split(" ");
		String owner = Split[0];
		int ID;
		if(Split[1].equals("X")){
			ID = -1;
		}else{
			ID = Integer.parseInt(Split[1]);
		}
		
		illigalMove = ID < 0;
		Player player = null;
		
		
		if(owner.equals(Character.toString('R'))) player =  Player.Rom;
		else if(owner.equals(Character.toString('C'))) player  = Player.Cathargo;
		
		Move move = new Move(player, ID);
		return move;
	}
	
	public boolean wasMoveIllegal(){
		return illigalMove;
	}
	
	public void makeMoveIllegal(){
		illigalMove = true;
	}
	
	//Kai
	public String toString(){		

		return player == Player.Rom? "R " + targetCityID : "C" +" "+targetCityID;
	}

}
