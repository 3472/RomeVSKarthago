package core;

import java.io.PrintWriter;
import java.util.Iterator;

public class Sloth extends PlayerAbs {

	public Sloth(Player name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public Sloth(Player name, PrintWriter toNetwork){
		super(name);
	}
	
	
public Move makeMove(City_Graph cityGraph, History h, Move prevMove) {
		
		Iterator<City> it = cityGraph.iterator();
		
		City targetCity = null;
    	while(it.hasNext()){
    		targetCity = it.next();
    		if(targetCity.getOwner() == Owner.Neutral){
    			break;
    		}
    
    	}	
    	Move move = new Move(this.name ,targetCity.getID());
    	return move;
	}

}
