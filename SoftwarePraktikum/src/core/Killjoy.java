package core;

import java.util.Iterator;

public class Killjoy extends PlayerAbs {

	public Killjoy(Player name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Move makeMove(City_Graph city_graph, History h, Move prevMove) {
		Iterator<City> it = city_graph.iterator();

		Move bestMove = null;
		int biggestDifference = 0;

		int momentanScore = city_graph.getScore(this.ReturnOtherPlayer(this
				.PlayerToOwner(name)));

		City targetCity = null;
		while (it.hasNext()) {
			targetCity = it.next();
			if (targetCity.getOwner() == Owner.Neutral) {
				Move m = new Move(this.name, targetCity.getID());
				City_Graph testGraph = city_graph.gameStateTransition(m);

				int dif = momentanScore
						- testGraph
								.getScore(ReturnOtherPlayer(PlayerToOwner(this.name)));
				if (dif > biggestDifference) {
					biggestDifference = dif;

					bestMove = m;
				}
			}

		}

		if (biggestDifference == 0) {
			it = city_graph.iterator();

			Move bestMove1 = null;
			int highestScore = 0;

			City targetCity1 = null;
			while (it.hasNext()) {
				targetCity1 = it.next();
				if (targetCity1.getOwner() == Owner.Neutral) {
					Move m = new Move(this.name, targetCity1.getID());
					City_Graph testGraph = city_graph.gameStateTransition(m);

					if (testGraph.getScore(PlayerToOwner(this.name)) > highestScore) {
						highestScore = testGraph
								.getScore(PlayerToOwner(this.name));

						bestMove1 = m;
					}
				}

			}

			if (highestScore == 0) {
				String forfit = "";
				if (name == Player.Rom) {
					forfit = "R X";
				} else {
					forfit = "C X";
				}
				bestMove1 = new Move(forfit);
			}

			return bestMove1;
		}

		return bestMove;

	}
}
