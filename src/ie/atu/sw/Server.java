package ie.atu.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

	private int PORT = 9999;
	@Override
	public void run() {
		try {
			// This creates a server socket bound to a specific port
			ServerSocket server = new ServerSocket(PORT);
			// This waits for the client connection and accepts it 
			Socket client = server.accept();
			
		} catch (IOException e) {
			// Handle this later
		}
		
		
	}
	
	
	
	class ConnectionHandler implements Runnable {
	    // This will hold the value of the current instance of the Socket class;
		private Socket client;
		//To read information in 
		private BufferedReader in;
		// To print information our
		private PrintWriter out;
		
		public ConnectionHandler(Socket client) {
			this.client = client;
			
		}

		@Override
		public void run() {
			
			
		}
		
		
	}

}
