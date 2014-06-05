package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.text.PlainDocument;

/*
 * Die Klasse ist sehr �hnlich zu der Main-Loop die wir sp�ter brauchen
 */


//Johannes
//Du k�mmerst dich darum, die Konsolen aplikation zum laufen zu bringen, hab schon mal ein Grundger�st vor getippt
public class ConsolGame implements GameLogic,Runnable{
	
	public static void main(String[] args){
		new ConsolGame("res/test.mp");
	}

	private boolean GameOver;
	private Board gameBoard;
	private City_Graph city_Graph;
	private boolean moveMake;
	private boolean isMoveAForfeit;
	private History history;
	private PlayerAbs pl1,pl2,currentPlayer;
	private BufferedReader consoleReader;
	private Move move;
	private City_Graph newGameState;
	
	
	public ConsolGame(String path){
		city_Graph = new City_Graph();
		city_Graph.loadMap(path);
		
		history = new History();
		history.add(city_Graph);
		
		gameBoard = new Board(city_Graph);
		
		pl1 = new PlayerAbs(Player.Rom);
		pl2 = new PlayerAbs(Player.Cathargo);
		
		currentPlayer = pl1;
		
		consoleReader = new BufferedReader(new InputStreamReader(System.in));
		
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
				
				System.out.print("> ");
				move = null;
				move = this.getMoveFromInput();
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
	
	


	/**
	 * waits for an userinput and trys to turn it into an Move
	 * if the input is wrong, the move will remain 
	 * 
	 * @return 
	 */
	private Move getMoveFromInput(){
		Move move = null;
		String input = "";
		
		try {
			input = consoleReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String arguments[] = input.split(" ");
		
		
		// wrong amount of Arguments
		if(arguments.length != 2){
			errorTryAgain("Invalide number of Arguments");
		}else{
			// First Tag is Wrong
			if(!(arguments[0].equals("R") || arguments[0].equals("C"))){
				errorTryAgain("First Argument has to be the playertag");
			}else{
				Player p = null;
				if(arguments[0].equals("R")){
					p = Player.Rom;
				}else{
					p = Player.Cathargo;
				}
				
				// Wrong Player entered
				if(p != currentPlayer.name){
					errorTryAgain("Wrong Player");
				}else{
					
					// (player forfeits)
					if(arguments[1].equals("X")){
						isMoveAForfeit = true;
						moveMake = true;
					}else{
						int id = -1;
						try{
							id = Integer.parseInt(arguments[1]);
							
							// input seems right
							// add move
							
							if(city_Graph.getCity(id).getOwner() != Owner.Neutral){
								errorTryAgain("City is Already Captured");
							}else{
								move = new Move(arguments[0] + " " + arguments[1]);
								if(move != null){
									moveMake = true;
								}
							}
							
						}catch(NumberFormatException ex){
							errorTryAgain("Last Argument is not a number nor an 'X'");
						}
					}
				}
			}
		}
		
		return move;
	}
	
	
	
	private void errorTryAgain(String msg){
		System.out.println(msg);
		System.out.println("Try Again");
	}

	
	
	
	
	
	
	//Gerald
	@Override
	public String logic(Move move, City_Graph graph,PlayerAbs currentPlayer) {
		
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
	private void doLocig() {
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
