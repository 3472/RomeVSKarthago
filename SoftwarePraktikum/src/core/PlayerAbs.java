package core;

import java.io.PrintWriter;

public abstract class PlayerAbs {

	boolean forfeits;
	public int skip;
	public Player name; 
	
	
	public PlayerAbs(Player name){
		this.name = name;
		
	}
	
	public abstract Move makeMove(City_Graph city_graph, History h, Move prevMove);
	
	
	

	public Owner PlayerToOwner(Player p){
    	if(p == Player.Cathargo){
    		return Owner.Cathargo;
    	}
    	return Owner.Rom;
    }

	
	public Owner ReturnOtherPlayer(Owner owner){
	        if (owner==owner.Rom) return owner.Cathargo;
	        if (owner==owner.Cathargo) return owner.Rom;
	        else return owner;
	  }
	
	
	 public void gameEnded(Move finalMove){
		 
	 }
	  

	public Move forfitMove(){
			  String forfit = "";
			if(name == Player.Rom){
				forfit = "R X";
			}else{
				forfit = "C X";
			}
			return new Move(forfit);
		}
		
}
