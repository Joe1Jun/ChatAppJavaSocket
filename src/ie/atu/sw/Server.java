package ie.atu.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server implements Runnable{
	//Will need to be instatiated at some point
	private List<ConnectionHandler> connections;

	private int PORT = 9999;
	@Override
	public void run() {
		try {
			// This creates a server socket bound to a specific port
			ServerSocket server = new ServerSocket(PORT);
			// This waits for the client connection and accepts it 
			Socket client = server.accept();
			// This creates a new instance of the connection handler for each client that connects to the server
			ConnectionHandler handler = new ConnectionHandler(client);
			//This adds that connection to array of connections
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
		
		private String name;
		
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
				name = in.readLine();
				// This will be displayed for that instance of the thread
				System.out.println(name + "connected");
				// This will be broadcast and will be seen by all threads connected
				broadcast(name + "joined the chat");
				
				String message;
				while((message = in.readLine()) != null) {
					if(message.startsWith("/name" )) {
						//handle this
					}else if(message.startsWith("/q")) {
						// client can quit
					}else {
						broadcast(name + " : " + message);
					}
				}
				
				
			} catch (IOException e) {
				// TODO: handle exception
				
				
			}
			
			
			
		}
		
		//This will broadcast a message to all client currently connected
		// This will loop through the connection arrayList and send the message using the command line
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
