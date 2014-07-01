package network;

import core.Move;


public interface NetworkIOHandler {

	public String readMove();
	public void sendMove(Move move);
}
