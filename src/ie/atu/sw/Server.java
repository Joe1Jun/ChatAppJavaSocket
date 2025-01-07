package ie.atu.sw;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Server implements Runnable{
	
	private ServerSocket server;
	
	private List<ConnectionHandler> clientConnections;
	
	private boolean keepRunning;
	private ExecutorService pool;
	
	private int port ;
	
	public Server (int port) throws IOException {
		clientConnections = new ArrayList<>();
		keepRunning = true;
		pool = Executors.newVirtualThreadPerTaskExecutor();
        server = new ServerSocket(port);
		this.port = port;
	}

	
	
	
	@Override
    public void run() {
        // Try-with-resources to manage the virtual thread pool
		// Infinite loop to continuously accept and handle client connections.
        while (keepRunning) {
        	System.out.println("Server started on port " + port);
            try {
                // Waits for an incoming client connection and returns a socket representing it.
                Socket connection = server.accept();
                System.out.println("Client connected from " + connection.getInetAddress() + ":" + connection.getPort());
                // Created a new instance of the object that handles all client connections.
                ConnectionHandler connectionHandler = new ConnectionHandler(connection, this);
                clientConnections.add(connectionHandler);
                pool.execute(connectionHandler);
            } catch (IOException ex) {
                // Handle exceptions that occur while accepting a connection.
                System.err.println("Error accepting connection: " + ex.getMessage());
                shutdown();
            }
        }
        }
	
	
	//This will broadcast a message to all client currently connected
   // This will loop through the connection arrayList and send the message using the command line
			
    public void broadcast(String message) {
		
		for (ConnectionHandler ch : clientConnections) {
			
			if(ch != null) {
				ch.sendMessage(message);
			}
		}
	}
    
    public void removeClient(ConnectionHandler clientConnection) {
        clientConnections.remove(clientConnection);
    }
    
    public void shutdown() {
    	// Notify all clients about the shutdown
        broadcast("Server is shutting down. Please disconnect.");
    	
    	try {
    		// Close the server socket to stop accepting new connections
    		keepRunning = false;
        	if(!server.isClosed()) {
        		server.close();
        	}
        	// Close each client connection
        	for(ConnectionHandler ch : clientConnections) {
        		//ch.shutdown();
        	}
        	 System.out.println("Server has been shut down.");
    		
		} catch (Exception e) {
			// TODO: handle exception
		}
    	  
    	
    }
	
	
	
//	class ConnectionHandler implements Runnable {
//	    // This will hold the value of the current instance of the Socket class;
//		private Socket client;
//		//To read information in 
//		private BufferedReader in;
//		// To print information our
//		private PrintWriter out;
//		
//		private String name;
//		
//		public ConnectionHandler(Socket client) {
//			this.client = client;
//			
//		}
//
//		//This overrides the runnable class and defines the method that 
//		// will be carried out by each thread.
//		@Override
//		public void run() {
//			
//			try {
//				// This is the output stream initialised and the auto flush set to through so there is 
//				// no need to manually flush the output stream
//				out = new PrintWriter(client.getOutputStream(), true);
//				// This will read in the input stream from the client
//				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//				
//				
//				// ****Add edge cases etc to make this more robust****
//				out.println("Please enter a name");
//				// assigns the variable the value of the line read from the client
//				name = in.readLine();
//				// This will be displayed for that instance of the thread
//				System.out.println(name + " connected");
//				// This will be broadcast and will be seen by all threads connected
//				broadcast(name + " joined the chat");
//				
//				String message;
//				// Loop continuously to handle messages from the clients
//				while((message = in.readLine()) != null) {
//					// Check if the message starts with the "/name" command for renaming the user
//					if(message.startsWith("/name" )) {
//						// Split the message into two parts: the command and the new name
//						String [] messageSplit = message.split(" ", 2);
//						// If the split message contains two parts (command and new name)
//						if(messageSplit.length == 2) {
//							// Broadcast to all clients that the user has renamed themselves
//							broadcast(name + "renamed themselves to " + messageSplit[1]);
//							// Log the renaming action on the server console
//							System.out.println(name + "renamed themselves to " + messageSplit[1]);
//							// Update the user's name
//							name = messageSplit[1];
//							out.println("Successfully changed name to " + name);
//						} else {
//							out.println("No name provided");
//						}
//					}else if(message.equals("/q")) {
//						// If the message is the "/q" command, allow the client to quit the chat
//						
//						broadcast(name + " left the chat !");
//					}else {
//						 // For all other messages, broadcast the message to all connected clients
//						broadcast(name + " : " + message);
//					}
//				}
//				
//				
//			} catch (IOException e) {
//				shutdown();
//				
//				
//			}
//			
//			
//			
//		}
//		
//		
//		
//		
//		public void sendMessage(String message) {
//			
//			out.println(message);
//		}
//		
//		public void shutdown() {
//			
//			try {
//				// Close the input stream (used to receive messages from the client)
//				in.close();
//				// Close the output stream (used to send messages to the client)
//				out.close();
//				// Check if the client's socket is still open
//				// This avoids redunant operation if the client connection is already closed
//				if(!client.isClosed()) {
//					// Close the client socket, disconnecting it from the server
//					client.close();
//					
//				}
//				
//			} catch (IOException e) {
//				// Handle any exceptions that occur during the shutdown process
//		        // This could be caused by issues while closing streams or the socket
//			}
//			
//		}
//		
//		
//		
//		
//	}
//	
//	
	

}
