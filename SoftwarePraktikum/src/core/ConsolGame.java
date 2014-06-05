package core;

import javax.swing.text.PlainDocument;

/*
 * Die Klasse ist sehr �hnlich zu der Main-Loop die wir sp�ter brauchen
 */


//Johannes
//Du k�mmerst dich darum, die Konsolen aplikation zum laufen zu bringen, hab schon mal ein Grundger�st vor getippt
public class ConsolGame implements GameLogic,Runnable{
	
	public static void main(String[]args){
		new ConsolGame("res/test.mp");
	}

	private boolean GameOver;
	private Board gameBoard;
	private City_Graph city_Graph;
	private boolean moveMake;
	private History history;
	private PlayerAbs pl1,pl2,currentPlayer;
	
	
	public ConsolGame(String path){
		city_Graph = new City_Graph();
		city_Graph.loadMap(path);
		
		history = new History();
		history.add(city_Graph);
		
		gameBoard = new Board(city_Graph);
		
		pl1 = new PlayerAbs(Player.Rom);
		pl2 = new PlayerAbs(Player.Cathargo);
		
		currentPlayer = pl1;
		
		Thread th = new Thread(this);
		th.start();
	}
	
	
	
	//Johannes
	@Override
	public void run() {
		Move move = null;
		City_Graph newGameState;
		
		while(!GameOver){
			
			while(!moveMake){
				/*
				 * Spielzug aus Konsole einlesen und in einen Move umwandenln
				 * am bessten eine Methode schreiben daf�r
				 * 
				 * F�r die Methode:
				 * 
				 * Schau dir mal die Bsp bei dem zettel an, so sollten die eingaben aussehen
				 * bei falschen ausgaben soll move gleich null sein und Syntax error ausgeben werden
				 * und erneut eine eingabe gefordert werden
				 */
			}
			doLogic(move);
			moveMake = false;
			
			/*
			 * Graph des GameBoard aktl;
			 * n�chster Spieler ist am Zug
			 */
		}
		
	}

	//Gerald
	@Override
	public String logic(Move move, City_Graph graph,PlayerAbs currentPlayer) {
		// TODO Auto-generated method stub
		
		//Also hier kannst/sollst du die History benutzen
		//nutze gameStateTransition() von city_graph und
		//pr�fe ob der string von dem neuen Graph schon mal
		//in der History auftaucht
		//Falls move null ist, brauchst du keine logik zu pr�fen weil es beim eingeben
		//einen syntax fehler gab.
		
		
		if(move == null) {		
			return "Illegal"; 
			
		}
		else {
			City_Graph newGraph = graph.gameStateTransition(move);			
	
			if(history.contains(newGraph)) {						
				return "Illegal";				
				
			}
			else {			
				return "Legal";
				
			}		
		}
	}


	//Gerald
	/*
	 * Die Methode soll die Ausgaben von GameLogic() nutzen und Richtig auf die Ausgaben reagieren
	 * Z.B bei einem Ilegalen Zug soll bleibt der Graph gleich und Aussetzen wird eins hochgesetzt,
	 * 
	 * 
	 * Bei einem Legal Zug wird city_Graph durch den neuen Graph ersetzt
	 */
	private void doLogic(Move move) {
		// TODO Auto-generated method stub
		
		
		//TODO Auf ausgaben von logic reagieren
		/*
		 * Bei einem Legalen Zug:
		 * 	- graph durch neuen erstetzen
		 * 	- neuen Graph erzeugen
		 * 	- den neuen graph in die History einf�gen
		 * 	- Skips werden zur�ck gesetzt vom aktl spieler
		 * 	- Passende Consolen-Ausgaben
		 * Bei einem ilegalen Zug
		 * 	-Graph bleibt bestehen
		 * 	-Skip wird hochgesetzt
		 * 	- Passende Consolen-Ausgaben
		 * Bei GameOver
		 * 	-Graph aktualliesieren
		 * 	- Passende Consolen-Ausgaben
		 * 
		 */
		
		String status = logic(move, city_Graph, currentPlayer);
		
		if(status == "Legal") {		
			
			// muss noch angepasst werden
			//je nachdem wie das passen nach dem einlesen gespeichert wird
			if(move.getCityID() == 'X') {
				currentPlayer.skip = 1;

			}
			else {
			city_Graph = city_Graph.gameStateTransition(move);
			history.add(city_Graph);
			
			pl1.skip = 0;
			pl2.skip = 0;
			
			}	
			
		}	
		
		else if(status == "Illegal") {				
			currentPlayer.skip = 1;	
			
		}		
			
		if(pl1.skip == 1 && pl2.skip == 1)
				GameOver = true;
				
		System.out.println(city_Graph.convertGameStateToString());

		if(currentPlayer == pl2){
			currentPlayer = pl1;
		}else{
			currentPlayer = pl2;
		}
	}
	
}
