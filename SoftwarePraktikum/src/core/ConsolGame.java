package core;

import javax.swing.text.PlainDocument;

/*
 * Die Klasse ist sehr ähnlich zu der Main-Loop die wir später brauchen
 */


//Johannes
//Du kümmerst dich darum, die Konsolen aplikation zum laufen zu bringen, hab schon mal ein Grundgerüst vor getippt
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
		Move move;
		City_Graph newGameState;
		
		while(!GameOver){
			
			while(!moveMake){
				/*
				 * Spielzug aus Konsole einlesen und in einen Move umwandenln
				 * am bessten eine Methode schreiben dafür
				 * 
				 * Für die Methode:
				 * 
				 * Schau dir mal die Bsp bei dem zettel an, so sollten die eingaben aussehen
				 * bei falschen ausgaben soll move gleich null sein und Syntax error ausgeben werden
				 * und erneut eine eingabe gefordert werden
				 */
			}
			doLocig();
			moveMake = false;
			
			/*
			 * Graph des GameBoard aktl;
			 * nächster Spieler ist am Zug
			 */
		}
		
	}

	//Gerald
	@Override
	public String logic(Move move, City_Graph graph,PlayerAbs currentPlayer) {
		// TODO Auto-generated method stub
		
		//Also hier kannst/sollst du die History benutzen
		//nutze gameStateTransition() von city_graph und
		//prüfe ob der string von dem neuen Graph schon mal
		//in der History auftaucht
		//Falls move null ist, brauchst du keine logik zu prüfen weil es beim eingeben
		//einen syntax fehler gab.
		
		return null;
	}


	//Gerald
	/*
	 * Die Methode soll die Ausgaben von GameLogic() nutzen und Richtig auf die Ausgaben reagieren
	 * Z.B bei einem Ilegalen Zug soll bleibt der Graph gleich und Aussetzen wird eins hochgesetzt,
	 * 
	 * 
	 * Bei einem Legal Zug wird city_Graph durch den neuen Graph ersetzt
	 */
	private void doLocig() {
		// TODO Auto-generated method stub
		
		
		//TODO Auf ausgaben von logic reagieren
		/*
		 * Bei einem Legalen Zug:
		 * 	- graph durch neuen erstetzen
		 * 	- neuen Graph erzeugen
		 * 	- den neuen graph in die History einfügen
		 * 	- Skips werden zurück gesetzt vom aktl spieler
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
		
		if(currentPlayer == pl2){
			currentPlayer = pl1;
		}else{
			currentPlayer = pl2;
		}
	}
	
}
