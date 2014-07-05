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
	
	private final long WAIT = 60000;//1 Minute
	private final Pattern MOVEPATTERN = Pattern.compile("R X|R [0-9]+");
	
	private final String EOL = System.getProperty("line.separator");
	
	Socket server;
	
	
	public Client() {
		
	}

	
	
	@Override
	public String readMove() throws IOException {
		long time = System.currentTimeMillis() + WAIT;
		
		
		while(!fromServer.ready()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(System.currentTimeMillis()> time){
				endConection();
				return null;
			}
		}
		
		
		String line = fromServer.readLine();
		Matcher matcher = MOVEPATTERN.matcher(line);
		
		System.out.println("recieved move from Server: " + line);
		
		if(matcher.matches()){
			return line;
		}else{
			
			endConection();
			return null;
		}
	}
	
	
	@Override
	public void sendMove(Move move) {
		System.out.println("Sending Move To Server: " + move.toString());
		toServer.println(move.toString());
	}
	
	@Override
	public void initClient(ArrayList<String> map, int port) throws IOException {
		
		server = new Socket("localhost",port);
		
		fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
		toServer = new PrintWriter(server.getOutputStream(),true);
		
		for(String str : map){
			System.out.println("Client: "+str);
			toServer.println(str);
		}
		toServer.println();
	}
	
	private void endConection() throws IOException{
		System.out.println("Closing Connection");
		fromServer.close();
		toServer.close();
		server.close();
		System.exit(1);
	}
	
	/*public static void main(String[]args){
		Client client = new Client();
		
		ArrayList<String> bla = new ArrayList<String>();
		bla.add("4");
		bla.add("V 0 C 12 13");
		bla.add("V 1 R 1402 1343");
		bla.add("V 2 N 0 0");
		bla.add("E 1 0");
		
		try {
			client.initClient(bla, 3142);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	

}
