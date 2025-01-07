package ie.atu.sw;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client implements Runnable {
    
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean keepRunning;
    private ExecutorService pool;
    private int port;
    
//    
//    //If a single thread tried to listen to server messages and wait for user input:
//
//While you're typing, the client would stop listening to the server.
//If the server sends a critical message during your input (e.g., “Server is shutting down”), you’d miss it.

    
    public Client(int port) {
        this.port = port;
        this.keepRunning = true;
        this.pool = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public void run() {
        try {
            // Establish a connection to the server
            client = new Socket("localhost", port);
            System.out.println("Connected to server on port " + port);

            // Initialise input and output streams
            out = new PrintWriter(client.getOutputStream(), true);
            // 
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            // Start a separate thread for user input
            pool.execute(new InputHandler());

            // Listen for server messages
            String message;
            while (keepRunning && (message = in.readLine()) != null) {
                System.out.println("ChatApp: " + message);
            }
            
        } catch (IOException e) {
            
            System.err.println("Connection error. Please enter a valid port number of the server.");
            System.err.println("");
            
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    public void shutdown() {
        keepRunning = false;
        try {
            in.close();
             out.close();
            if (client != null && !client.isClosed()) client.close();
            pool.shutdown();
            System.out.println("Client disconnected.");
        } catch (IOException e) {
            System.err.println("Error during client shutdown: " + e.getMessage());
        }
    }
	
	class InputHandler implements Runnable {
		
		
		

		@Override
		public void run() {
			
			try {
				// Create a BufferedReader to read user input from the console
				BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
				
				// Loop continuously until the keepRuning flag is set to false
				while(keepRunning) {
					 // Read a line of input from the user
					String message = inReader.readLine();
					// If the user inputs the "q" command
					if(message.equals("q")) {
						// Close the console input reader
						inReader.close();
						 // Call the shutdown method to terminate the connection
						shutdown();
					} else {
						out.println(message);
					}
					
				}
				
				
				
			} catch (Exception e) {
				 // Handle any exceptions that occur while reading user input
                e.printStackTrace();
                shutdown();
			}
			
		}
		
		
		
		
		
	}
	
	public static void main(String[] args) {
		  
		  Scanner input = new Scanner(System.in);
			
			MenuForClient menuForClient = new MenuForClient(input);
			int choice;
			try {
				choice = menuForClient.start();
				if(choice == 1) {
					System.out.println("Enter port number for server");
					int port = input.nextInt();
					Client client = new Client(port);
					client.run();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
				
			}

	
	
	
	

}
