package network;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Server {

	private BufferedReader fromClient = null;
	private PrintWriter toClient = null;
	

	public BufferedReader getFromClientReader(){
		return fromClient;
	}
	
	public PrintWriter getToClientPrinter(){
		return toClient;
	}
}
