package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import core.City_Graph;
import core.Move;
import static org.junit.Assert.*;
import org.junit.Before;

// Hiermit können wir jederzeit Spielszenarien erzeugen und testen. Da ich uns als fähig genug einschätze, habe ich die Fehlerbehebung weggelassen.

public class TerminalTestSession {

		City_Graph city_graph = new City_Graph();
		
		@Before
		public void init(){
			city_graph.loadMap("res/test.mp");
		}
		
		public void loadTerminalSession(String Sessionpath){
			
			BufferedReader fileReader = null;
			String line = null;
			try {
				fileReader = new BufferedReader(new FileReader(Sessionpath));
				line = fileReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			while(line!=null){
				String expectedOutput;
				String moveString = line.substring(2);
				Move move = new Move(moveString);
				
				try {
					fileReader = new BufferedReader(new FileReader(Sessionpath));
					line = fileReader.readLine();
				} catch (IOException e) {
					e.printStackTrace();}
				
				expectedOutput = line;
				city_graph = city_graph.gameStateTransition(move);
				assertEquals(city_graph.convertGameStateToString(), expectedOutput);
		
				
				try {
					fileReader = new BufferedReader(new FileReader(Sessionpath));
					line = fileReader.readLine();
				} catch (IOException e) {
					e.printStackTrace();}
			}
		}
}