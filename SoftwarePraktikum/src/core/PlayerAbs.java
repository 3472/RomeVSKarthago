package core;

abstract class PlayerAbs {

	boolean forfeits;
	public int skip;
	public Player name;
	
	public PlayerAbs(Player name){
		this.name = name;
	}
	
	public Move makeMove(City_Graph city_graph) {
		return null;
	}
	
	
}
