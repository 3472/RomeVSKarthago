package core;

import java.io.PrintWriter;

abstract class PlayerAbs {

	boolean forfeits;
	public int skip;
	public Player name; 
	protected PrintWriter toNetwork = null;
	protected boolean isOnline;
	
	
	public PlayerAbs(Player name){
		this.name = name;
		isOnline = false;
	}
	
	public PlayerAbs(Player name, PrintWriter toNetwork){
		this.name = name;
		this.toNetwork = toNetwork;
		isOnline = true;
	}
	
	public abstract Move makeMove(City_Graph city_graph);
	
	public Move sendMove(Move m){
		if(isOnline){
			String p = "C";
			if(m.getPlayer() == Player.Rom){
				p = "R";
			}
			// TODO: muss direkt gesedet werden, wie=
			toNetwork.println(p + " " + m.getCityID());
		}
		
		return m;
	}
	
}
