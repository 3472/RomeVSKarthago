package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class KIJH extends PlayerAbs {

	int zugNr;
	int maxNeuralNeighboursToTry;
	int startNeutralSurroundStrat;

	public KIJH(Player name) {
		super(name);
		maxNeuralNeighboursToTry = 2;
		zugNr = 0;
	}

	@Override
	public Move makeMove(City_Graph city_graph, History h, Move prevMove) {

		zugNr++;

		Map<Integer, Integer> best = new HashMap<Integer, Integer>();
		for (City c : city_graph) {
			best.put(c.getID(), 0);
		}

		Owner thisPlayer = this.PlayerToOwner(name);
		Owner otherPlayer = this.ReturnOtherPlayer(PlayerToOwner(name));

		/*
		 * war der vorherige Zug Illegal und wir haben mehr Punkte als der
		 * Gegner, geben wir einen Illegalen move zuck und haben gewonnen
		 */
		if (prevMove != null) {
			if (h.wasLastMoveIllegal()) {
				if (city_graph.getScore(thisPlayer) > city_graph
						.getScore(otherPlayer)) {
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
		Iterator<City> it = city_graph.iterator();
		while (it.hasNext()) {
			City c = it.next();
			if (c.getOwner() == Owner.Neutral) {
				City_Graph checkGraph = city_graph
						.gameStateTransition(new Move(name, c.getID()));

				int dif = enemyCityCount
						- citysRuledByOwner(checkGraph, otherPlayer);
				if (!h.contains(checkGraph)) {
					int add = 1;
					if (dif == 2) {
						add = 4;
					} else if (dif > 2) {
						add = 8;
					}
					best.put(c.getID(), best.get(c.getID()) + add);
				}
			}
		}

		/*
		 * es wird geschaut ob man eine Neutrale stadt mit x zügen umrunden kann
		 */

		ArrayList<ArrayList<City>> possibleFields = this.onlyXToSurround(
				city_graph, maxNeuralNeighboursToTry);

		if (!possibleFields.isEmpty()) {

			for (ArrayList<City> cityList : possibleFields) {

				for (int i = 0; i < cityList.size(); i++) {
					City bestCity = cityList.get(0);
					Move bestMove = new Move(name, bestCity.getID());

					City_Graph testGraph = city_graph
							.gameStateTransition(bestMove);
					if (!this.givesOneTurnCaptureArea(testGraph,
							testGraph.getCity(bestCity.getID()))) {
						int add = 1;
						if (cityList.size() == 1) {
							add = 7;
						}
						best.put(bestCity.getID(), best.get(bestCity.getID())
								+ add);
					}
				}
			}
		}

		/*
		 * Es wird der Move gewählt der am Meisten punkte bringt
		 */
		it = city_graph.iterator();

		int currentScore = city_graph.getScore(PlayerToOwner(this.name));
		int mostPointsID = -1;
		int mostPoints = currentScore;

		while (it.hasNext()) {
			City targetCity = null;
			targetCity = it.next();
			if (targetCity.getOwner() == Owner.Neutral) {
				Move m = new Move(this.name, targetCity.getID());
				City_Graph testGraph = city_graph.gameStateTransition(m);

				if (testGraph.getScore(PlayerToOwner(this.name)) > currentScore
						&& !h.contains(testGraph)
						&& !this.givesOneTurnCaptureArea(testGraph,
								testGraph.getCity(targetCity.getID()))) {

					if (testGraph.getScore(PlayerToOwner(this.name)) > mostPoints) {
						mostPoints = testGraph
								.getScore(PlayerToOwner(this.name));
						mostPointsID = targetCity.getID();
					}

					int add = (testGraph.getScore(PlayerToOwner(this.name)) - (currentScore));
					add = 1;
					best.put(targetCity.getID(), best.get(targetCity.getID())
							+ add);
				}
			}

		}

		/*
		 * wenn der beste zug nur 1 punkt gibt, wird die stadt mit den meisten
		 * neutralen nachbern eingenommen
		 */

		it = city_graph.iterator();

		while (it.hasNext()) {
			City targetCity = null;
			targetCity = it.next();
			if (targetCity.getOwner() == Owner.Neutral) {
				int neutrals = this.citysInSetRuledByOwner(
						city_graph.getNeighbourhood(targetCity), Owner.Neutral);
				if (neutrals > 1) {
					int add = 0;
					if (neutrals == 2) {
						add = 1;
					} else if (neutrals == 3) {
						add = 2;
					} else if (neutrals < 3 && neutrals < 6) {
						add = neutrals;
					} else {
						add = neutrals + 1;
					}
					best.put(targetCity.getID(), best.get(targetCity.getID())
							+ add);
				}
			}

		}

		int hi = 0;
		int id = -1;
		Iterator<Integer> inti = best.keySet().iterator();
		while (inti.hasNext()) {
			int i = inti.next();
			int val = best.get(i);
			if (val > hi) {
				hi = val;
				id = i;
			}
		}

		if (id < 0) {
			return new Move(name, mostPointsID);
		}
		return new Move(name, id);

	}

	private int citysRuledByOwner(City_Graph cityGraph, Owner o) {
		Iterator<City> it = cityGraph.iterator();
		int res = 0;
		while (it.hasNext()) {
			if (it.next().getOwner() == o)
				res++;
		}

		return res;
	}

	private int citysInSetRuledByOwner(VertexSet vertexSet, Owner o) {
		Iterator<City> it = vertexSet.iterator();
		int res = 0;
		while (it.hasNext()) {
			if (it.next().getOwner() == o)
				res++;
		}

		return res;
	}

	private ArrayList<ArrayList<City>> onlyXToSurround(City_Graph cityGraph,
			int x) {

		ArrayList<ArrayList<City>> res = new ArrayList<ArrayList<City>>();

		for (City c : cityGraph) {

			if (c.getOwner() == Owner.Neutral) {
				VertexSet n = cityGraph.getNeighbourhood(c);

				ArrayList<City> resSet = new ArrayList<City>();

				int amount = citysInSetRuledByOwner(n, Owner.Neutral);
				if (amount <= x
						&& amount > 0
						&& citysInSetRuledByOwner(
								n,
								this.ReturnOtherPlayer(this.PlayerToOwner(name))) == 0) {

					for (City city : n) {
						if (city.getOwner() == Owner.Neutral) {
							resSet.add(city);
						}
					}
					res.add(resSet);
				}
			}
		}

		return res;
	}

	private boolean givesOneTurnCaptureArea(City_Graph cityGraph, City city) {

		if (city.getOwner() != this.PlayerToOwner(name)) {
			return true;
		}
		VertexSet n = cityGraph.getVertexSetNeighbourhood(cityGraph
				.getConnectedComponents(city));

		int count = 0;
		for (City c : n) {
			if (c.getOwner() == Owner.Neutral) {
				count++;
			}

		}
		return count < 2;
	}
}
