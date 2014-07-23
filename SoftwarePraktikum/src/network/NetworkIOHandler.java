package network;

import java.io.IOException;
import java.util.ArrayList;

import core.Move;

public interface NetworkIOHandler {

	public String readMove() throws IOException;

	public void sendMove(Move move);
}
