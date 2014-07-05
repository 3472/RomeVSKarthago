package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class KINormalJ extends PlayerAbs{

	public KINormalJ(Player name) {
		super(name);
		maxNeuralNeighboursToTry = 2;
	}

	int maxNeuralNeighboursToTry;
	
	@Override
	public Move makeMove(City_Graph city_graph, History h, Move prevMove) {
		

		Owner thisPlayer = this.PlayerToOwner(name);
		Owner otherPlayer = this.ReturnOtherPlayer(PlayerToOwner(name));
		
		
		/* war der vorherige Zug Illegal und wir haben mehr Punkte als
		 * der Gegner, geben wir einen Illegalen move zurück und haben
		 * gewonnen
		 */
		if(prevMove != null){
			if(h.wasLastMoveIllegal()){
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
		 * es wird geschaut ob man eine Neutrale stadt 
		 * mit x zügen umrunden kann
		 */
		ArrayList<ArrayList<City>> possibleFields = this.onlyXToSurround(city_graph,
				maxNeuralNeighboursToTry);
		
		if(!possibleFields.isEmpty()){
			int smallest = 1;
			while(smallest <= maxNeuralNeighboursToTry){
				for(ArrayList<City> cityList : possibleFields){
					int i = cityList.size();
					if(i == smallest){
						
						//TODO: den besten wählen
						System.out.println("THISWAY");
						return new Move(name,  cityList.get(0).getID());
					}
				}
				smallest++;
			}
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

	private ArrayList<ArrayList<City>> onlyXToSurround(City_Graph cityGraph, int x){
		
		
		
		ArrayList<ArrayList<City>> res = new ArrayList<ArrayList<City>>();
		
		
		for(City c : cityGraph){
			
			if(c.getOwner() == Owner.Neutral){
				VertexSet n = cityGraph.getNeighbourhood(c);
				
				ArrayList<City> resSet = new ArrayList<City>();
				
				
				int amount = citysInSetRuledByOwner(n, Owner.Neutral);
				if(amount <= x && amount > 0
						&& citysInSetRuledByOwner(n, this.ReturnOtherPlayer(
								this.PlayerToOwner(name))) == 0){
					
					
					for(City city : n){
						if(city.getOwner() == Owner.Neutral){
							resSet.add(city);
						}
					}
					res.add(resSet);
				}
			}
		}
		
		return res;
	}
}
