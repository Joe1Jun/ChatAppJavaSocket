package ie.atu.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server implements Runnable{
	
	private List<ConnectionHandler> connections;

	private int PORT = 9999;
	@Override
	public void run() {
		try {
			// This creates a server socket bound to a specific port
			ServerSocket server = new ServerSocket(PORT);
			// This waits for the client connection and accepts it 
			Socket client = server.accept();
			ConnectionHandler handler = new ConnectionHandler(client);
			connections.add(handler);
			
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
		
		private String nickname;
		
		public ConnectionHandler(Socket client) {
			this.client = client;
			
		}

		//This overrides the runnable class and defines the method that 
		// will be carried out by each thread.
		@Override
		public void run() {
			
			try {
				// This is the output stream initialised and the auto flush set to through so there is 
				// no need to manually flush the output stream
				out = new PrintWriter(client.getOutputStream(), true);
				// This will read in the input stream from the client
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				
				
				// ****Add edge cases etc to make this more robust****
				out.println("Please enter a nickname");
				// assigns the variable the value of the line read from the client
				nickname = in.readLine();
				System.out.println(nickname + "connected");
				
				
			} catch (IOException e) {
				// TODO: handle exception
				
				
			}
			
			
			
		}
		
		public void broadcast(String message) {
			
			for (ConnectionHandler ch : connections) {
				
				if(ch != null) {
					ch.sendMessage(message);
				}
			}
		}
		
		
		public void sendMessage(String message) {
			
			System.out.println(message);
		}
		
		
	}

}
