package core;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.text.PlainDocument;

/*
 * Die Klasse ist sehr ���hnlich zu der Main-Loop die wir sp���ter brauchen
 */


//Johannes
//Du k���mmerst dich darum, die Konsolen aplikation zum laufen zu bringen, hab schon mal ein Grundger���st vor getippt
public class ConsolGame implements GameLogic{
	
	private boolean GameOver;
	private Board gameBoard;
	private City_Graph city_Graph;


	private History history;
	private PlayerAbs pl1,pl2,currentPlayer;
	private Move move;
	private Move prevMove;
	private boolean moveMake;
	
	public ConsolGame(City_Graph cityGraph, PlayerAbs player1, PlayerAbs player2){
		city_Graph = cityGraph;
		
		history = new History();
		history.add(city_Graph);
		
		gameBoard = new Board(city_Graph);
		
		pl1 = player1;
		pl2 = player2;
		currentPlayer = player1;
		prevMove = null;
		
		runs();
		//Thread th = new Thread(this);
		//th.start();
	}
	
	
	//Johannes
	public void runs() {

		
		GameOver = false;
		while(!GameOver){
			
			moveMake = false;
		
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//TODO: Send Move change
			
			System.out.print("> ");
			move = null;
			move = currentPlayer.makeMove(city_Graph, history, prevMove);
			if(move != null){
				System.out.println(move.toString());
			}
			
		
			
			doLogic();
			
			
	
			/*
			 * (nicht vergessen das city_graph = newCityGraph 
			 * DAVOR aufgerufen werden sollte)
			 */
			gameBoard.setNewGraph(city_Graph);
			
		}
		
	}
	
	
	//Gerald
	@Override
	public String logic(Move move, City_Graph graph,PlayerAbs currentPlayer) {
		
		if(move == null || move.getCityID() == -1) {	
				return "Illegal";
		}
		else {
				City_Graph newGraph = graph.gameStateTransition(move);	
	
			if(history.contains(newGraph) && !currentPlayer.forfeits){	
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
	private void doLogic() {
		// TODO Auto-generated method stub
		
		
		//TODO Auf ausgaben von logic reagieren
		/*
		 * Bei einem Legalen Zug:
		 * 	- graph durch neuen erstetzen
		 * 	- neuen Graph erzeugen
		 * 	- den neuen graph in die History einf���gen
		 * 	- Skips werden zur���ck gesetzt vom aktl spieler
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

		if(status.equals("Legal")) {	

			// changed by johannes
			if(currentPlayer.forfeits) {
				currentPlayer.skip = 1;
				currentPlayer.forfeits = false;
			}
			else {
				city_Graph = city_Graph.gameStateTransition(move);
		
				pl1.skip = 0;
				pl2.skip = 0;
	
			}	
			
		}	

		else if(status.equals("Illegal")) {
			System.out.println("Illegal move");
			currentPlayer.skip = 1;	

		}	
		
		// der CityGraph wird jetzt immer hinzugefügt
		history.add(city_Graph);
		prevMove = move;

		if(pl1.skip == 1 && pl2.skip == 1){
			GameOver = true;
			System.out.println("game over");
			System.out.println("SCORE:");
			System.out.println("ROM: " + city_Graph.getScore(Owner.Rom));
			System.out.println("CATHARGO: " + city_Graph.getScore(Owner.Cathargo));
	
		}
			

		System.out.println(city_Graph.convertGameStateToString());

		
		
		if(currentPlayer == pl2){
			currentPlayer = pl1;
		}else{
			currentPlayer = pl2;
		}
	}
	
	public Move getFinalMove(){ return prevMove; }
}