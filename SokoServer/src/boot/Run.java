package boot;

import java.util.Scanner;

import server.SokobanSolvingServer;

public class Run {
	private static final int port = 5555;
	
	public static void main(String[] args) throws Exception {
		SokobanSolvingServer server = new SokobanSolvingServer(port);
		server.start();
		
		Scanner scanner = new Scanner(System.in);
		String str = new String("");
		
		while (!str.equals("stop")) {
			str = scanner.nextLine();
			switch (str) {
			case "stop":
				server.stop();
				break;
			default:
				break;
			}
		}
		scanner.close();
	}

}
