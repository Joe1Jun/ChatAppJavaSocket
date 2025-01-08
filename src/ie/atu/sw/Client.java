package ie.atu.sw;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client implements Runnable {

	

	private PrintWriter out;
	private boolean keepRunning = true;
	
	private int port;


    
	public Client(int port) {
		this.port = port;
		
		
	}

	@Override
	public void run() {
		// Try-with-resources to manage the virtual thread pool, the socket and the input and output readers
		// This means there doesnt need to be any manual closing down of these resources
		// The try with resources handles this.
		try (Socket socket = new Socket("localhost", port); // Auto-closeable socket
				ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor();
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
			
			// Assign PrintWriter to instance variable
			this.out = out; 
			// Establish a connection to the server

			System.out.println("Connected to server on port " + port);

			// Start a separate thread for user input
			// This thread is submitted to the thread pool for execution
			// Have to create seperate threads to handle input as otherwise the listening and 
			// typing would not run in parrellel
			pool.execute(new ClientHandler());

			// Declare a variable to hold incoming messages from the server
			String message;
			// Continuously listen for incoming messages as long as the 'keepRunning' flag is true and the input stream is not null
			//This listens on the particular instance of the socket created in the try with resources
			while (keepRunning && (message = in.readLine()) != null) {
				// Print the received message from the server to the console with a label
			      ConsoleUtils.printHeader("ChatApp: " + message);
			}

		} catch (IOException e) {
             // If error occurs the messsage is printed
			System.err.println("Connection error. Please enter a valid port number of the server.");
			System.err.println("");

		}
	}
	
  // The message sent from the input handler thread to the be sent to the server
	public void sendMessage(String message) {
		if (out != null) {
			//Send the message through the output stream to the server
			out.println(message);
		}
	}
	
	// This was left as a private class as there as its very small

	private class ClientHandler implements Runnable {

		@Override
		public void run() {
            // Try with resources that will auto close the buffered reader
			try (BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in))) {
				// Create a BufferedReader to read user input from the console

				// Loop continuously until the keepRuning flag is set to false
				while (keepRunning) {
					// Read a line of input from the user
					
					String message = inReader.readLine();
					if (message.equalsIgnoreCase("exit")) {
						System.out.println("Shutting down connection....");
						//
						sendMessage("is leaving the chat...");
						// Ensures that both loops exit.
						keepRunning = false;
						// and output streams

					}

					// Sends the message to the sendMessage method through output stream to the server
					sendMessage(message);
					
				}

			} catch (Exception e) {
				// Handle any exceptions that occur while reading user input
				e.printStackTrace();

			}

		}
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

     // Create an instance of the menu to handle user choice
		MenuForClient menuForClient = new MenuForClient(input);
		
		int choice;
        // Create the Client object inside a try catch
		try {
			choice = menuForClient.start();
			if (choice == 1) {
				System.out.println("Type 'exit' to leave the chat");

				// Use ClientPortHandler to get the valid port input
				// This class handles all validations for the client port input
				
				ClientPortHandler portHandler = new ClientPortHandler(input);
				int port = portHandler.getPortInput(); 

				// Create client object and run it
				// Thread needed ????
				Client client = new Client(port);
				client.run();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
