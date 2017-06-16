package controller.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;

public class SokobanClientHandler extends Observable implements ClientHandler{
	
	@Override
	public void HandleClient(InputStream inFromClient, OutputStream outToClient) throws Exception // handle a client by a well defined protocol
	{

		// Streams initialization
		InputStreamReader InFromClient = new InputStreamReader(inFromClient);	
		BufferedReader fromClient = new BufferedReader(InFromClient);
		PrintWriter toClient = new PrintWriter(outToClient, true);
		
		// Data Declaration
		String inputLine = null;
		String outputLine = new String("Enter the next command please.");	
		boolean stopPlay = false;
		
		// Sending terms to the client
		toClient.println("Enter 'menu' for menu display.");
		toClient.println("Enter 'stop' for stop playing.");
		toClient.println("Enter 'exit' to close the server.");
		
		// Protocol
		while(stopPlay != true)
		{
			toClient.println(outputLine);
			inputLine = fromClient.readLine();
			if(inputLine.compareTo("stop") == 0 || inputLine.compareTo("exit") == 0)
			{	
				stopPlay = true;
				continue;
			}
			setChanged();
			notifyObservers(inputLine);
		}
		
		InFromClient.close();
		fromClient.close();
		toClient.close();

		if(inputLine.compareTo("exit") == 0)
		{
			setChanged();
			notifyObservers(inputLine);
		}
		
		try {Thread.sleep(100);} catch (InterruptedException e) {}
	}
}
