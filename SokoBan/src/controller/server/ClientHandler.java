package controller.server;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
	public void HandleClient(InputStream inFromClient, OutputStream outToClient) throws Exception; // handle a client by a well defined protocol
}
