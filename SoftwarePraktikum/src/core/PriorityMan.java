package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PriorityMan extends KIEasyJ {

	public PriorityMan(Player name) {
		super(name);
	}

	Map<Integer, City_Graph> newGraph = null;
	Map<Integer, Integer> possibles = null;
	int zugNr = 0;

	private ArrayList<VertexSet> safeAreas = new ArrayList<>();

	// private ArrayList<Integer> safeNeutralIDs = new ArrayList<>();

	@Override
	public Move makeMove(City_Graph cityGraph, History h, Move prevMove) {
		zugNr++;

		/*
		 * hat der Graph nur weniger als 20 städte wird KIEasyJ spielen
		 */
		if (cityGraph.getAmountOfCitys() <= 24) {
			System.out.println("KIEASY");
			return super.makeMove(cityGraph, h, prevMove);
		}

		/*
		 * alle städte wählen deren eroberung sinnvoll sein könnte
		 */
		newGraph = new HashMap<Integer, City_Graph>();
		possibles = new HashMap<Integer, Integer>();

		getPossibleCitys(cityGraph, h);

		Owner thisPlayer = this.PlayerToOwner(name);
		Owner otherPlayer = this.ReturnOtherPlayer(PlayerToOwner(name));

		/*
		 * war der vorherige Zug Illegal und wir haben mehr Punkte als der
		 * Gegner, geben wir einen Illegalen move zuck und haben gewonnen
		 */
		if (h.wasLastMoveIllegal()) {
			if (cityGraph.getScore(thisPlayer) > cityGraph
					.getScore(otherPlayer)) {
				return forfitMove();
			}
		}

		this.setNeutralNeighboursPriority();
		this.setScorePriority(cityGraph);
		this.setKillPriority(cityGraph);
		this.prepareSurround();
		this.setSafeCaptureCitys(cityGraph);
		this.denyScorePriority(cityGraph);

		/*
		 * for test
		 */
		Iterator<Integer> it = possibles.keySet().iterator();
		while (it.hasNext()) {
			int a = it.next();
			System.out.println(a + ":  " + possibles.get(a));
		}

		return this.getBestMove(possibles);
	}

	private void prepareSurround() {
		Iterator<Integer> ids = newGraph.keySet().iterator();
		Owner o = PlayerToOwner(name);

		ArrayList<Integer> testedIDs = new ArrayList<>();
		while (ids.hasNext()) {
			int id = ids.next();

			City_Graph tmpG = newGraph.get(id);
			VertexSet neuts = new VertexSet();
			int score = tmpG.getScore(o);
			for (City c : tmpG) {
				if (c.getOwner() == Owner.Neutral) {
					neuts.addVertex(c);
				}
			}

			for (City c : neuts) {
				City_Graph testGraph = tmpG.gameStateTransition(new Move(name,
						c.getID()));

				int dif = testGraph.getScore(o) - score;

				if (!testedIDs.contains(id)) {
					if (dif == 2) {
						addPoints(id, 2);
						testedIDs.add(id);
					} else if (dif == 3) {
						addPoints(id, 6);
						testedIDs.add(id);
					}
				}
			}
		}
	}

	private void setKillPriority(City_Graph cg) {
		Owner o = this.ReturnOtherPlayer(PlayerToOwner(name));
		int score = cg.getScore(o);
		Iterator<Integer> ids = newGraph.keySet().iterator();

		while (ids.hasNext()) {
			int id = ids.next();
			int dif = score - newGraph.get(id).getScore(o);

			if (dif == 1) {
				addPoints(id, 5);
			} else if (dif > 2) {
				addPoints(id, dif + 13);
			}
		}
	}

	private void setScorePriority(City_Graph cg) {
		Owner o = PlayerToOwner(name);
		int score = cg.getScore(o);
		Iterator<Integer> ids = newGraph.keySet().iterator();

		while (ids.hasNext()) {
			int id = ids.next();
			int dif = newGraph.get(id).getScore(o) - score;

			if (dif == 2) {
				addPoints(id, 6);
			} else if (dif == 3) {
				addPoints(id, 100);
				VertexSet safe = newGraph.get(id).getConnectedComponents(
						newGraph.get(id).getCity(id));
				this.safeAreas.add(safe);
			} else if (dif > 3) {
				addPoints(id, 9);
			}

		}
	}

	private void denyScorePriority(City_Graph cg) {
		Owner o = this.ReturnOtherPlayer(PlayerToOwner(name));
		int score = cg.getScore(o);
		Iterator<Integer> ids = newGraph.keySet().iterator();

		while (ids.hasNext()) {
			int id = ids.next();
			int dif = score - newGraph.get(id).getScore(o);

			if (dif == 4) {
				addPoints(id, 5);
				System.out.println("IT HAPPEND");
			} else if (dif > 4) {
				addPoints(id, dif + 2);
				System.out.println("IT HAPPEND");
			} else if (dif == 3) {
				addPoints(id, 1);
			}

		}
	}

	private void setSafeCaptureCitys(City_Graph cg) {
		for (VertexSet al : safeAreas) {
			VertexSet neigs = cg.getVertexSetNeighbourhood(al);
			for (City c : neigs) {
				if (c.getOwner() == Owner.Neutral
						&& possibles.containsKey(c.getID())) {
					addPoints(c.getID(), 1);
				}
			}
		}
	}

	private void setNeutralNeighboursPriority() {
		Iterator<Integer> ids = newGraph.keySet().iterator();
		while (ids.hasNext()) {
			int id = ids.next();
			City_Graph tmpGraph = newGraph.get(id);
			VertexSet neigs = tmpGraph.getNeighbourhood(tmpGraph.getCity(id));
			int count = this.citysInSetRuledByOwner(neigs, Owner.Neutral);

			/*
			 * POINTS
			 */

			/*
			 * if(count == 3){ addPoints(id, 1); }else if(count == 4){
			 * addPoints(id, 2); }else if(count == 5){ addPoints(id, count-1);
			 * }else if(count > 5){ addPoints(id, count + 1); }
			 */
			if (count > 5) {
				addPoints(id, 9 + count);
			} else if (count == 5) {
				addPoints(id, 5);
			} else if (count > 2) {
				addPoints(id, count - 2);
			}

		}
	}

	private void addPoints(int id, int points) {
		possibles.put(id, possibles.get(id) + points);
	}

	private void getPossibleCitys(City_Graph cityGraph, History h) {

		for (City c : cityGraph) {
			if (c.getOwner() == Owner.Neutral) {
				City_Graph tmpGraph = cityGraph.gameStateTransition(new Move(
						name, c.getID()));
				if (tmpGraph.getCity(c.getID()).getOwner() != Owner.Neutral) {

					/*
					 * dont add 2 neutral surrounded
					 */
					boolean dontAdd = false;
					VertexSet neutralSet = cityGraph.getConnectedComponents(c);
					if (neutralSet.getSize() < 3) {
						VertexSet neigs = cityGraph
								.getVertexSetNeighbourhood(neutralSet);
						dontAdd = true;
						for (City bla : neigs) {
							if (bla.getOwner() != PlayerToOwner(name)) {
								dontAdd = false;
							}
						}

					}

					if (!dontAdd && !h.contains(tmpGraph)) {
						possibles.put(c.getID(), 0);
						newGraph.put(c.getID(), tmpGraph);
					}

				}
			}
		}

	}

	private Move getBestMove(Map<Integer, Integer> moves) {
		int id = -1;
		int hi = 0;

		Iterator<Integer> it = moves.keySet().iterator();
		while (it.hasNext()) {
			int val = it.next();
			if (moves.get(val) > hi) {
				id = val;
				hi = moves.get(val);
			}
		}

		if (id == -1) {
			// TODO: Better
			System.out.println("NOTE: No move selected!!");
			if (moves.isEmpty()) {
				return forfitMove();
			} else {
				// HERE
				Iterator<Integer> ito = moves.keySet().iterator();
				return new Move(name, moves.get(ito.next()));
			}
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
}
