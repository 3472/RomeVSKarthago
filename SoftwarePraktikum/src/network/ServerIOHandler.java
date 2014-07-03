package network;

import java.io.IOException;
import java.util.ArrayList;

public interface ServerIOHandler extends NetworkIOHandler {

	public ArrayList<String> initServer(int port) throws IOException;
}
