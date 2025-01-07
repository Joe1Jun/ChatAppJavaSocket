package ie.atu.sw;

import java.net.Socket;
import java.util.Scanner;
import java.io.*;

public class Client implements Runnable{
	
	// Hostname of the server to connect to, set to localhost for testing on the same machine
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private boolean done = false;
	private int port;
	
	public Client(int port) {
		this.port = port;
	}
	@Override
	public void run() {
		
		try {
			// Create a new socket and connect it to the specified hostname and port
            // This establishes a TCP connection between the client and the server
			client = new Socket("127.0.0.1", port);
			// Initialises the output stream for sending data to the server
			// The 'true' parameter enables auto-flushing, so there is no need to call flush manually after each println
			out = new PrintWriter(client.getOutputStream(), true);
			// Initialises the input stream for receiving data from the server
			// Wraps the socket's input stream with InputStreamReader and BufferedReader for efficient reading of character streams
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		    // Create a new instance of the InputHandler class to handle user input
			InputHandler inputHandler = new InputHandler();
			// Create a new thread to run the InputHandler
		    // This allows user input to be handled concurrently with receiving messages from the server
			Thread t = new Thread(inputHandler);
			t.start();
			// Continuously read messages from the server
		    // The server's messages are read line-by-line and printed to the console
		    
			String message;
			while((message = in.readLine()) != null) {
				System.out.println(message);
			}
			
		} catch (IOException e) {
			 // Handle any exceptions that occur while reading user input
            e.printStackTrace();
          // Clean up resources and terminate the client
            shutdown();
		}
		
	}
	
	public void shutdown() {
		done  = true;
		
		try {
			
			in.close();
			out.close();
			if(!client.isClosed()) {
				client.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	class InputHandler implements Runnable {
		
		
		

		@Override
		public void run() {
			
			try {
				// Create a BufferedReader to read user input from the console
				BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
				
				// Loop continuously until the done flag is set to true
				while(!done) {
					 // Read a line of input from the user
					String message = inReader.readLine();
					// If the user inputs the "/q" command, initiate a shutdown
					if(message.equals("/q")) {
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
