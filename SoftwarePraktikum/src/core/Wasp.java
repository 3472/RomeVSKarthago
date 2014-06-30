package core;

import java.io.PrintWriter;

public class Wasp extends PlayerAbs {

	public Wasp(Player name) {
		super(name);
	}
	
	public Wasp(Player name, PrintWriter toNetwork) {
		super(name, toNetwork);
	}
	
	
	
	public Move makeMove(City_Graph cityGraph) {
		Move move = new Move(this.name, 0);
		return sendMove(move);
	}

}
