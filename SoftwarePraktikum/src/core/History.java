package core;

import java.util.ArrayList;

/*
 * Die Klasse dient dazu, die unterschiedlichen Spielzust�nde zu speichern die im Laufe des Spiels auftreten
 * und um so zu schauen ob schon ob es sich bei dem aktuellen Zustand um einen neuen oder einen alten(was ein fehler w�r
 * laut der Regeln)
 */
public class History {

	private ArrayList<String> history;

	public History() {
		history = new ArrayList<String>();
	}

	/*
	 * Ueberprueft ob es diesen Spielzustand schon mal gab
	 */
	public boolean contains(City_Graph graph) {

		String gameState = graph.convertGameStateToString();

		for (String str : history) {
			if (str.equals(gameState))
				return true;
		}

		return false;
	}

	/*
	 * F�gt einen Zustand in die History ein, wobei �berpr�ft wird ob der
	 * Zustand schon existiert
	 */
	/**
	 * 
	 * @param gameState
	 * @return true falls der Zustand noch nicht in der History auftaucht
	 */
	public boolean add(City_Graph graph) {
		if (contains(graph)) {
			return false;
		} else {
			String gameState = graph.convertGameStateToString();

			history.add(gameState);
			return true;
		}

	}
	
	public boolean wasLastMoveIllegal(){
		if(history.size() < 2){
			return false;
		}
		return history.get(history.size()-1).equals(history.get(history.size()-2));
	}
}
