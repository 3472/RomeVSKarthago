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
	private final Pattern MOVEPATTERN = Pattern.compile("C X|C [0-9]+");
	
	
	
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
		
		//System.out.println("recieved move from Server: " + line);
		
		if(matcher.matches()){
			return line;
		}else{
			
			endConection();
			return null;
		}
	}
	
	
	@Override
	public void sendMove(Move move) {
		toServer.println(move.toString());
	}
	
	@Override
	public void initClient(ArrayList<String> map, int port,String hostad) throws IOException {
		
		server = new Socket(hostad,port);
		
		fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
		toServer = new PrintWriter(server.getOutputStream(),true);
		
		for(String str : map){
			System.out.println("Client: "+str);
			toServer.println(str);
		}
	}
	
	public void endConection(){
		System.out.println("Closing Connection");
		try {
			fromServer.close();
			toServer.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}	
	

}
