package core;

import java.util.Iterator;

public class Scooge extends PlayerAbs {

	public Scooge(Player name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Move makeMove(City_Graph city_graph, History h, Move prevMove) {

		Owner thisPlayer = this.PlayerToOwner(name);
		Owner otherPlayer = this.ReturnOtherPlayer(PlayerToOwner(name));
		

		/* war der vorherige Zug Illegal und wir haben mehr Punkte als
		 * der Gegner, geben wir einen Illegalen move zurck und haben
		 * gewonnen
		 */
		if(prevMove != null){
			if(h.wasLastMoveIllegal()){
				if(city_graph.getScore(thisPlayer) > city_graph.getScore(otherPlayer)){
					return forfitMove();
				}
			}
		}
		
		
		
		
		
		
		
		Iterator<City> it = city_graph.iterator();
		
		Move bestMove = null;
		int highestScore = 0;
		
		City targetCity = null;
    	while(it.hasNext()){
    		targetCity = it.next();
    		if(targetCity.getOwner() == Owner.Neutral){
    			Move m = new Move(this.name, targetCity.getID());
    			City_Graph testGraph = city_graph.gameStateTransition(
    					m);
    			
    			if(testGraph.getScore(PlayerToOwner(this.name)) > highestScore){
    				highestScore = testGraph.getScore(
    						PlayerToOwner(this.name));
    				
    				bestMove = m;
    			}
    		}
    
    	}	
    	
    	if(highestScore == 0){
    		String forfit = "";
    		if(name == Player.Rom){
    			forfit = "R X";
    		}else{
    			forfit = "C X";
    		}
    		bestMove = new Move(forfit);
    	}
    
    	return bestMove;
	}
}
