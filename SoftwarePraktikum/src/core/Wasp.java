package core;

import java.io.PrintWriter;

public class Wasp extends PlayerAbs {

	public Wasp(Player name) {
		super(name);
	}
	
	
	
	public Move makeMove(City_Graph cityGraph, Move prevMove) {
		Move move = new Move(this.name, 0);
		return move;
	}

}
