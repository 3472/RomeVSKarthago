package core;

public class Wasp extends AIPlayerLocal {

	public Wasp(Player name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public Move makeMove(City_Graph cityGraph) {
		return new Move(this.name, 0);
	}

}
