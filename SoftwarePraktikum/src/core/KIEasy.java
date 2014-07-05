package core;

import java.util.Iterator;

public class KIEasy extends PlayerAbs{

	int zugNr;
	
	public KIEasy(Player name) {
		super(name);
		zugNr = 0;
	}

	@Override
	public Move makeMove(City_Graph city_graph, History h, Move prevMove) {
		
		
		zugNr++;
		
		Owner thisPlayer = this.PlayerToOwner(name);
		Owner otherPlayer = this.ReturnOtherPlayer(PlayerToOwner(name));
		
		
		/* war der vorherige Zug Illegal und wir haben mehr Punkte als
		 * der Gegner, geben wir einen Illegalen move zurück und haben
		 * gewonnen
		 */
		if(prevMove != null){
			if(prevMove.wasMoveIllegal()){
				if(city_graph.getScore(thisPlayer) > city_graph.getScore(otherPlayer)){
					return forfitMove();
				}
			}
		}
		
		
		/*
		 * wenn eine oder mehrere Städte des Gegners ausgehungert werden können,
		 * wird dies gemacht (bei mehreren Möglichkeiten, der Zug der die
		 * meisten städte aushungert
		 */
		int enemyCityCount = citysRuledByOwner(city_graph, otherPlayer);
		int biggestDifference = 0;
		Move killMove = null;
		Iterator<City> it = city_graph.iterator();
		while(it.hasNext()){
			City c = it.next();
			if(c.getOwner() == Owner.Neutral){
				City_Graph checkGraph = city_graph.gameStateTransition(
						new Move(name, c.getID()));
				
				int dif = enemyCityCount - citysRuledByOwner(checkGraph, otherPlayer);
				if(dif > biggestDifference && !h.contains(checkGraph)){
					biggestDifference = dif;
					killMove = new Move(name, c.getID());
				}
			}
		}
		
		if(biggestDifference > 0){
			return killMove;
		}
		
		
		
		/*
		 * Es wird der Move gewählt der am Meisten punkte bringt
		 */
		it = city_graph.iterator();
		
		Move bestMove = null;
		int highestScore = 0;
		int currentScore = city_graph.getScore(PlayerToOwner(this.name));
		
		
    	while(it.hasNext()){
    		City targetCity = null;
    		targetCity = it.next();
    		if(targetCity.getOwner() == Owner.Neutral){
    			Move m = new Move(this.name, targetCity.getID());
    			City_Graph testGraph = city_graph.gameStateTransition(
    					m);
    			
    			if(testGraph.getScore(PlayerToOwner(this.name)) > highestScore
    					&& !h.contains(testGraph)){
    				highestScore = testGraph.getScore(
    						PlayerToOwner(this.name));
    				
    				bestMove = m;
    			}
    		}
    
    	}	
    	int ScoreDiff = highestScore - currentScore;
    	
    	
    	/*
    	 * gibt kein Zug punkte wird ausgesetzt
    	 */
    	if(ScoreDiff == 0){
    		return this.forfitMove();
    	}
    	
    
    	/*
    	 * wenn der beste zug nur 1 punkt gibt,
    	 * wird die stadt mit den meisten neutralen
    	 * nachbern eingenommen
    	 */
    	if(ScoreDiff < 2){
    		it = city_graph.iterator();
    		
    		int mostNeutral = 0;
    		
        	while(it.hasNext()){
        		City targetCity = null;
        		targetCity = it.next();
        		if(targetCity.getOwner() == Owner.Neutral){
        			int neutrals = this.citysInSetRuledByOwner(
        					city_graph.getNeighbourhood(targetCity),
        					Owner.Neutral);
        			if(neutrals > mostNeutral){
        				mostNeutral = neutrals;
        				System.out.println(neutrals);
        				bestMove = new Move(name, targetCity.getID());
        			}
        		}
        
        	}	
        	
    	}
    
    	
    	return bestMove;
	}
	
	
	private int citysRuledByOwner(City_Graph cityGraph, Owner o){
		Iterator<City> it = cityGraph.iterator();
		int res = 0;
		while(it.hasNext()){
			if(it.next().getOwner() == o) res++;
		}
		
		return res;
	}
	
	
	private int citysInSetRuledByOwner(VertexSet vertexSet, Owner o){
		Iterator<City> it = vertexSet.iterator();
		int res = 0;
		while(it.hasNext()){
			if(it.next().getOwner() == o) res++;
		}
		
		return res;
	}
	 
}
