package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>This server handle multiple Sokoban clients </p> 
 * @author Elad Ben Zaken
 *
 */
public class SokobanSolvingServer implements Server {
	private Thread serverThread = null;
	private int port = 0;
	private volatile boolean stop = false;
	private ServerSocket listeningSocket = null;
	private ExecutorService executor = Executors.newCachedThreadPool();

	public SokobanSolvingServer(int port) {
		this.port = port;
	}

	@Override
	public void start() throws Exception {
		serverThread = new Thread(() -> runServer());
		serverThread.start();
	}

	private void runServer() {
		try {
			listeningSocket = new ServerSocket(this.port);

			while(!stop){
				Socket connectionSocket = listeningSocket.accept();

				executor.submit(new Runnable() {
					
					@Override
					public void run() {
						SokobanClientHandler handler = new SokobanClientHandler();
						try {
							handler.handleClient(connectionSocket.getInputStream(), connectionSocket.getOutputStream());
						} catch (Exception e) {
							e.printStackTrace();
						}}});

			}
			
			listeningSocket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void stop() throws Exception {
		this.stop = true;
		listeningSocket.close();
		executor.shutdown();
		System.out.println("Server is closed");
	}

}
