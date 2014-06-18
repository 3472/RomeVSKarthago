package core;

public class Wasp extends AIPlayer {

	public Wasp(Player name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public Move makeAIMove(City_Graph cityGraph) {
		return new Move(this.name, 0);
	}

}
