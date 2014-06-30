package network;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Client {
	private BufferedReader fromServer = null;
	private PrintWriter toServer = null;
	

	public BufferedReader getFromServerReader(){
		return fromServer;
	}
	
	public PrintWriter getToServerPrinter(){
		return toServer;
	}
}
