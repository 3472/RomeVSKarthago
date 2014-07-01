package core;

import java.io.PrintWriter;

abstract class PlayerAbs {

	boolean forfeits;
	public int skip;
	public Player name; 
	
	
	public PlayerAbs(Player name){
		this.name = name;
		
	}
	
	public abstract Move makeMove(City_Graph city_graph, Move prevMove);
	
	
	
}
