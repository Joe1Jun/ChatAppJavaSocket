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
	//private ExecutorService pool;
	private int port ;
	
	public Server (int port) throws IOException {
		this.port = port;
		clientConnections = new ArrayList<>();
		keepRunning = true;
		//pool = Executors.newVirtualThreadPerTaskExecutor();
		// Might have to create this in a try block?
		server = new ServerSocket(port);
		
	}

	
	
	
	@Override
    public void run() {
        // Try-with-resources to manage the virtual thread pool
		// Infinite loop to continuously accept and handle client connections.
		
        while (keepRunning) {
        	
            try(ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor()) {
            	
            	
            	System.out.println("Waiting for clients......");
                // Waits for an incoming client connection and returns a socket representing it.
                Socket connection = server.accept();
                //Prints the client and the address they connected from 
                System.out.println("Client connected from " + connection.getInetAddress() + ": PORT " + connection.getPort());
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
        		ch.closeClientConnection();
        	}
        	 System.out.println("Server has been shut down.");
    		
		} catch (Exception e) {
			// TODO: handle exception
		}
    	  
    	
    }
	
    public static void main(String[] args) {
  	  
  	  Scanner input = new Scanner(System.in);
  		
  		Menu menu = new Menu(input);
  		int choice;
  		try {
  			choice = menu.start();
  			if(choice == 1) {
  				System.out.println("Enter port number for server");
  				int port = input.nextInt();
  				Server server = new Server(port);
  				server.run();
  			}
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		
  		
  			
  		}
	
}	
	

