package network;

import java.io.BufferedReader;
import java.io.PrintWriter;

import core.Move;

public class Client extends NetworkIO implements ClientIOHandler{
	private BufferedReader fromServer = null;
	private PrintWriter toServer = null;
	
	
	@Override
	public String readMove() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void sendMove(Move move) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void initClient(String map) {
		// TODO Auto-generated method stub
		
	}
	

}
