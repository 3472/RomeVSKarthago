package core;

public class GUIPlayer extends PlayerAbs{

	private Board b;
	
	public GUIPlayer(Player name, Board board){
		super(name);
		b = board;
	}
	
	@Override
	public Move makeMove(City_Graph c, Move prevMove){
		//return b.getMove();
		return null;
	}
}
