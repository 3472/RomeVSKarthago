package network;

import java.io.IOException;
import java.util.ArrayList;

public interface ClientIOHandler extends NetworkIOHandler{

	public void initClient(ArrayList<String> map, int port,String hostad) throws IOException;
}
