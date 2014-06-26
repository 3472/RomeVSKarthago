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
public class ConsolGame implements GameLogic,Runnable{
	
	public static void main(String[] args){
		
		// hier auswaehlen der spielertypen AI oder Human und evtl noch die map
				
		initializeGame();
	}

	private boolean GameOver;
	private Board gameBoard;
	private City_Graph city_Graph;


	private History history;
	private PlayerAbs pl1,pl2,currentPlayer;
	private Move move;
	private boolean isMoveAForfeit;
	private boolean moveMake;
	
	public ConsolGame(String path, PlayerAbs player1, PlayerAbs player2){
		city_Graph = new City_Graph();
		city_Graph.loadMap(path);
		
		history = new History();
		history.add(city_Graph);
		
		gameBoard = new Board(city_Graph);
		
		pl1 = player1;
		pl2 = player2;
		currentPlayer = player1;
		
		
		Thread th = new Thread(this);
		th.start();
	}
	
	static public void initializeGame() {
		
		PlayerAbs pl1 = null;
		PlayerAbs pl2 = null;
	
		String input = "";
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
	
		System.out.println("Choose Human or AI player: (Wasp -> 1), (Sloth -> 2), (Human -> 3)");
		System.out.println("Player1: ");
		
		try {
			while(!input.equals("1") && !input.equals("2") && !input.equals("3")) 
				input = consoleReader.readLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		pl1 = (PlayerAbs) (input.equals("1") ? new Wasp(Player.Rom) : 
			              (input.equals("2") ? new Sloth(Player.Rom) : new HumanPlayerLocal(Player.Rom)));
		
		input = "";
		System.out.println("Player2: ");
		
		try {
			while(!input.equals("1") && !input.equals("2") && !input.equals("3"))		
				input = consoleReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		pl2 = (PlayerAbs) (input.equals("1") ? new Wasp(Player.Cathargo) : 
	        (input.equals("2") ? new Sloth(Player.Cathargo) : new HumanPlayerLocal(Player.Cathargo)));
		
		System.out.println("Player1: " + pl1.getClass().getName());
		System.out.println("Player2: " + pl2.getClass().getName());	
		
		
		new ConsolGame("res/test.mp", pl1, pl2);
	
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
				
				System.out.print("> ");
				move = null;
				move = currentPlayer.makeMove(city_Graph);
				if(move != null){
					moveMake = true;
				}
				
		
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
		
		if(move == null) {	
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
