package core;

import java.util.Iterator;

public class Sloth extends AIPlayer {

	public Sloth(Player name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
public Move makeAIMove(City_Graph cityGraph) {
		
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
