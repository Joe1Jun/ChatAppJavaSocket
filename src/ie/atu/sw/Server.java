package ie.atu.sw;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
	
	private List<ConnectionHandler> connections;
	private ServerSocket server;
	private boolean done;
	private ExecutorService pool;
	
	public Server () {
		connections = new ArrayList<>();
		done = false;
	}

	private int PORT = 13;
	@Override
	public void run() {
		try {
			// This creates a server socket bound to a specific port
			 server = new ServerSocket(PORT);
			 // initialise threadPool
			 // Cached thread pool doesnt use a fixed amount of threads and can 
			 // expand dynamically based on load.
			 // If threads become idle they are terminated after a default length of time
			 // Therefore the amount of threads dont need to be defined
			 pool = Executors.newCachedThreadPool();
			 while(!done) {
			// This waits for the client connection and accepts it 
			Socket client = server.accept();
			// This creates a new instance of the connection handler for each client that connects to the server
			ConnectionHandler handler = new ConnectionHandler(client);
			//This adds that connection to array of connections
			connections.add(handler);
			pool.execute(handler);
			
			 }
		} catch (Exception e) {
			shutdown();
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
    	// Notify all clients about the shutdown
        broadcast("Server is shutting down. Please disconnect.");
    	
    	try {
    		// Close the server socket to stop accepting new connections
    		done = true;
        	if(!server.isClosed()) {
        		server.close();
        	}
        	// Close each client connection
        	for(ConnectionHandler ch : connections) {
        		ch.shutdown();
        	}
        	 System.out.println("Server has been shut down.");
    		
		} catch (Exception e) {
			// TODO: handle exception
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
						
						broadcast(name + " left the chat !");
					}else {
						 // For all other messages, broadcast the message to all connected clients
						broadcast(name + " : " + message);
					}
				}
				
				
			} catch (IOException e) {
				shutdown();
				
				
			}
			
			
			
		}
		
		
		
		
		public void sendMessage(String message) {
			
			out.println(message);
		}
		
		public void shutdown() {
			
			try {
				// Close the input stream (used to receive messages from the client)
				in.close();
				// Close the output stream (used to send messages to the client)
				out.close();
				// Check if the client's socket is still open
				// This avoids redunant operation if the client connection is already closed
				if(!client.isClosed()) {
					// Close the client socket, disconnecting it from the server
					client.close();
					
				}
				
			} catch (IOException e) {
				// Handle any exceptions that occur during the shutdown process
		        // This could be caused by issues while closing streams or the socket
			}
			
		}
		
		
	}
	
	

}
