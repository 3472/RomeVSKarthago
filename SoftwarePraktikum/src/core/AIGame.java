package core;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.text.PlainDocument;


	public class AIGame implements GameLogic,Runnable{
		
		public static void main(String[] args){
			new AIGame("res/test.mp");
		}

		private boolean GameOver;
		private Board gameBoard;
		private City_Graph city_Graph;
		private boolean moveMake;
		private boolean isMoveAForfeit;
		private History history;
		private AIPlayer pl1,pl2,currentPlayer;
		private BufferedReader consoleReader;
		private Move move;
		private City_Graph newGameState;
		
		
		public AIGame(String path){
			city_Graph = new City_Graph();
			city_Graph.loadMap(path);
			
			history = new History();
			history.add(city_Graph);
			consoleReader = new BufferedReader(new InputStreamReader(System.in));

			gameBoard = new Board(city_Graph);
			
			String input = "";

			System.out.println("Choose AI: (Wasp -> 1), (Sloth -> 2)");
			System.out.println("Player1: ");
			
			try {
				while(!input.equals("1") && !input.equals("2")) 
					input = consoleReader.readLine();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			pl1 = (AIPlayer) (input.equals("1") ? new Wasp(Player.Rom) : new Sloth(Player.Rom)); 
			
			input = "";
			System.out.println("Player2: ");
			
			try {
				while(!input.equals("1") && !input.equals("2"))		
					input = consoleReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
				
			pl2 = (AIPlayer) (input.equals("1") ? new Wasp(Player.Cathargo) : new Sloth(Player.Cathargo)); 
			
			System.out.println("Player1: " + pl1.getClass().getName());
			System.out.println("Player2: " + pl2.getClass().getName());
			currentPlayer = pl1;
			
			
			Thread th = new Thread(this);
			th.start();
		}
		
		
		
		//Johannes
		@Override
		public void run() {

			
			GameOver = false;
			while(!GameOver){
				
				moveMake = false;
			
				/*
				 * @author: johannes
				 * unser Movekonstruktor kann nur eine zahl als Argument nehmen
				 * was machen wir also wenn zb: "R X" eingegeben wird?
				 * 
				 * (braucht ja dann die gameLogic)
				 * habe jetzt einen boolean "isMoveAForfeit" eingebaut,
				 * der true ist falls z.B. "R X" engegeben wurde.
				 * 
				 * Der  CityGraph bleibt dann gleich
				 */
				isMoveAForfeit = false;
				
				while(!moveMake){
					
					System.out.println(this.currentPlayer.getClass().getName());
					System.out.print("> ");
					move = null;
					move = this.currentPlayer.makeAIMove(this.city_Graph);
					if(move != null){
						moveMake = true;
					}
					
			
				}
				doLocig();
				
				
		
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
			
			
			
			if(move == null) {	
					return "Illegal";

			}
			else {
					City_Graph newGraph = graph.gameStateTransition(move);	
		
				if(history.contains(newGraph)){	
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
		private void doLocig() {
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
				if(isMoveAForfeit) {
					currentPlayer.skip = 1;
					
				}
				else {
					city_Graph = city_Graph.gameStateTransition(move);
					history.add(city_Graph);
			
					pl1.skip = 0;
					pl2.skip = 0;
		
				}	

			}	

			else if(status.equals("Illegal")) {
				System.out.println("Illegal move");
				currentPlayer.skip = 1;	

			}	

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
		
}


