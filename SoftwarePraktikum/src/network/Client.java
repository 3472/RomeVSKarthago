package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.Move;

public class Client extends NetworkIO implements ClientIOHandler{
	private BufferedReader fromServer = null;
	private PrintWriter toServer = null;
	
	private final Pattern MOVEPATTERN = Pattern.compile("R X|R [0-9]+");
	
	private final String EOL = System.getProperty("line.separator");
	
	Socket server;
	
	
	public Client(Socket server) throws IOException {
		this.server = server;
		fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
		toServer = new PrintWriter(server.getOutputStream(),true);
	}

	
	
	@Override
	public String readMove() throws IOException {
		
		while(!fromServer.ready()){
			
		}
		
		String line = fromServer.readLine();
		Matcher matcher = MOVEPATTERN.matcher(line);
		
		if(matcher.matches()){
			return line;
		}else{
			endConection();
			return null;
		}
	}
	
	public void sendMove(String move) {
		// TODO Auto-generated method stub
		toServer.println(move.toString());
	}
	
	
	public void initClient(ArrayList<String> map, int port) {
		for(String str : map){
			System.out.println("Client: "+str);
			toServer.println(str);
		}
		toServer.println();
	}
	
	private void endConection() throws IOException{
		fromServer.close();
		toServer.close();
		server.close();
		
	}
	
	

}
