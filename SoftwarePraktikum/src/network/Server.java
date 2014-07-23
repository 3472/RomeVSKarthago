package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.ObjectInputStream.GetField;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.Move;

public class Server extends NetworkIO implements ServerIOHandler {

	private Socket client;
	private BufferedReader fromClient;
	private PrintWriter toClient;

	private final long WAIT = 60000;// 1 Minute
	private final Pattern MOVEPATTERN = Pattern.compile("R X|R [0-9]+");
	private final Pattern MAPPATTERN = Pattern
			.compile("E [0-9]+ [0-9]+|V [0-9]+ [C|R|N] [0-9]+ [0-9]+");

	private final String EOL = System.getProperty("line.separator");

	public Server() {

	}

	@Override
	public String readMove() throws IOException {
		long time = System.currentTimeMillis() + WAIT;

		// Das laesst sich bestimmt noch effektiver und schoener Loesen
		while (!fromClient.ready()) {
			try {
				// System.out.println("Ich warte");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (System.currentTimeMillis() > time) {
				endConnection();
				return null;
			}
		}

		String result;

		result = fromClient.readLine();

		Matcher matcher = MOVEPATTERN.matcher(result);

		if (matcher.matches()) {
			return result;
		} else {
			endConnection();
			return null;
		}

	}

	public void endConnection() throws IOException {
		System.out.println("Closing Connection");
		fromClient.close();
		toClient.close();
		client.close();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendMove(Move move) {
		toClient.println(move.toString());

	}

	@Override
	public ArrayList<String> initServer(int port) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(port);) {
			System.out.println("Server: gestartet warte auf Client");

			client = serverSocket.accept();

			System.out.println("Server: Verbinung hergestellt");

			fromClient = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			toClient = new PrintWriter(client.getOutputStream(), true);

			ArrayList<String> result = generateMap();

			if (result != null) {
				System.out.println("Server: " + fromClient.ready());
				return result;
			} else {
				System.out.println("Server: Fehler");
				endConnection();
				return null;
			}

		}
	}

	private ArrayList<String> generateMap() throws IOException {

		ArrayList<String> resultList = new ArrayList<String>();
		String line = fromClient.readLine();
		Matcher matcher;

		if (!line.matches("[0-9]+")) {
			endConnection();
			return null;
		}

		resultList.add(line);

		while ((line = fromClient.readLine()).length() > 0) {
			matcher = MAPPATTERN.matcher(line);

			if (matcher.matches()) {
				resultList.add(line);
			} else if ((matcher = MOVEPATTERN.matcher(line)).matches()) {
				System.out.println(fromClient.ready());
				break;
			} else {
				resultList = null;
				break;
			}
		}

		return resultList;

	}

	/*
	 * public static void main(String[]args){ Server server = new Server(); try
	 * { server.initServer(3142);
	 * 
	 * server.readMove(); } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } }
	 */

}
