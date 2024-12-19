package ie.atu.sw;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server implements Runnable{
	
	private List<ConnectionHandler> connections;
	private ServerSocket server;
	
	public Server () {
		connections = new ArrayList<>();
	}

	private int PORT = 9999;
	@Override
	public void run() {
		try {
			// This creates a server socket bound to a specific port
			 server = new ServerSocket(PORT);
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
	
	
	//This will broadcast a message to all client currently connected
			// This will loop through the connection arrayList and send the message using the command line
			
    public void broadcast(String message) {
		
		for (ConnectionHandler ch : connections) {
			
			if(ch != null) {
				ch.sendMessage(message);
			}
		}
	}
    
    public void shutdown() {
    	
    	if(!server.isClosed()) {
    		server.close();
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
				// Loop continuously to handle messages from the clients
				while((message = in.readLine()) != null) {
					// Check if the message starts with the "/name" command for renaming the user
					if(message.startsWith("/name" )) {
						// Split the message into two parts: the command and the new name
						String [] messageSplit = message.split(" ", 2);
						// If the split message contains two parts (command and new name)
						if(messageSplit.length == 2) {
							// Broadcast to all clients that the user has renamed themselves
							broadcast(name + "renamed themselves to " + messageSplit[1]);
							// Log the renaming action on the server console
							System.out.println(name + "renamed themselves to " + messageSplit[1]);
							// Update the user's name
							name = messageSplit[1];
							out.println("Successfully changed name to " + name);
						} else {
							out.println("No name provided");
						}
					}else if(message.startsWith("/q")) {
						// If the message is the "/q" command, allow the client to quit the chat
						
					}else {
						 // For all other messages, broadcast the message to all connected clients
						broadcast(name + " : " + message);
					}
				}
				
				
			} catch (IOException e) {
				// TODO: handle exception
				
				
			}
			
			
			
		}
		
		
		
		
		public void sendMessage(String message) {
			
			out.println(message);
		}
		
		
	}

}
