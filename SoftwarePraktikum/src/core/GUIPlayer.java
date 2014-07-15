package core;

import GUI.Board;

public class GUIPlayer extends PlayerAbs{

	private Board b;
	
	public GUIPlayer(Player name, Board board){
		super(name);
		b = board;
	}
	
	@Override
	public Move makeMove(City_Graph c, History h, Move prevMove){
		int id = b.getID();
		return new Move(name, id);
	}
}
