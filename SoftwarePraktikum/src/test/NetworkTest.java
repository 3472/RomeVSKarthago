package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import GUI.Board;
import core.*;
import network.*;

import org.junit.Test;

public class NetworkTest {

	public static final String localhost = "localhost";

	// netzwerktestspiel zwischen zwei ai spielern
	@Test
	public void aiGameTest1() {
		mainGameTest("-kinormalj", "-kinormalj", "res/bigmap.mp");
	}

	@Test
	public void aiGameTest2() {
		serverClientGameTest("-kinormalj", "-kinormalj", "res/test2.mp");
	}

	// lasse netzwerkspiel zwischen zwei frei wählbaren spieler auf mit mapPath
	// angegebener Karte ablaufen
	public void mainGameTest(final String player1, final String player2,
			String mapPath) {

		final int port = 7762;

		Thread serverThread = new Thread() {
			public void run() {
				try {
					new Main(new String[] { "-server", port + "", player1 });

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		serverThread.start();

		new Main(new String[] { "-client", port + "", player2, mapPath });
	}

	// lasse netzwerkspiel zwischen zwei frei wählbaren spieler auf mit mapPath
	// angegebener Karte ablaufen
	public void serverClientGameTest(String player1, String player2,
			String mapPath) {

		final int port = 7762;
		Main main = new Main(new String[] { "-server", port + "", player2 });
		final City_Graph cityGraph = new City_Graph();
		final PlayerAbs p2Server = main.getPlayerFromString(player2,
				Player.Cathargo, new Board(cityGraph));

		final Server server = new Server();

		PlayerAbs p1Client = main.getPlayerFromString(player1, Player.Rom,
				new Board(cityGraph));

		if (!cityGraph.loadMapByPath(mapPath)) {
			System.out.println("ERROR while loading map. closing Programm");
			System.exit(1);
		}

		Thread serverThread = new Thread() {
			public void run() {
				try {

					PlayerAbs p1Server = new NetworkPlayer(Player.Rom, server);

					cityGraph.loadMapByStrings(server.initServer(port));
					new ConsolGame(new Board(cityGraph), cityGraph, p1Server,
							p2Server, true);
					server.endConnection();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		serverThread.start();

		Client client = new Client();

		Main main2 = new Main(new String[] { "-client", port + "", player1,
				mapPath });

		ArrayList<String> mapdata = main2.readMapFileAndMove(mapPath);

		if (mapdata == null || p1Client == null) {
			System.exit(1);
		}

		try {
			client.initClient(mapdata, port);
		} catch (IOException e) {

			e.printStackTrace();
		}

		PlayerAbs p2Client = new NetworkPlayer(Player.Cathargo, client);

		new ConsolGame(new Board(cityGraph), cityGraph, p1Client, p2Client,
				true);
		client.endConection();

	}

	public void clientServerTest() {

		assertEquals(1, 1);
		final Server server = new Server();
		Thread serverThread = new Thread() {
			public void run() {
				try {
					server.initServer(7763);
					String moveString = server.readMove();
					assertEquals("R 3", moveString);
					server.sendMove(new Move(moveString));
					server.endConnection();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		serverThread.start();

		Client client = new Client();

		ArrayList<String> map = new ArrayList<String>();
		map.add("1");
		map.add("V 0 C 1 2");

		Move move = new Move("R 3");
		try {
			client.initClient(map, 7763);
			client.sendMove(move);
			assertEquals("R 3", client.readMove());
			client.endConection();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}