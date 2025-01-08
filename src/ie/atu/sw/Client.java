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
		try (Socket socket = new Socket("localhost", port); // Auto-closeable socket
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

			this.client = socket; // Assign the socket to the instance variable
			this.in = in; // Assign BufferedReader to instance variable
			this.out = out; // Assign PrintWriter to instance variable
			// Establish a connection to the server

			System.out.println("Connected to server on port " + port);

			// Start a separate thread for user input
			pool.execute(new ClientHandler());

			// The client created listens
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

	

 private class ClientHandler implements Runnable {

		@Override
		public void run() {

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
						// Ensures the main loop exits
	                     keepRunning = false; 
						// and output streams
						

					}

					// Prints the message to the console
					sendMessage(message);
				}

			} catch (Exception e) {
				// Handle any exceptions that occur while reading user input
				e.printStackTrace();
				

		}

	}
	}

//	public static void main(String[] args) {
//
//		Scanner input = new Scanner(System.in);
//
//		MenuForClient menuForClient = new MenuForClient(input);
//		int choice;
//		try {
//			choice = menuForClient.start();
//			if (choice == 1) {
//				System.out.println("Type exit to leave the chat");
//				System.out.println("Enter port number for server");
//				int port = input.nextInt();
//				
//				
//				Client client = new Client(port);
//				client.run();
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	}
 
 
public static void main(String[] args) {
Scanner input = new Scanner(System.in);

// Create an instance of the menu (for client actions)
MenuForClient menuForClient = new MenuForClient(input);
int choice;

try {
   choice = menuForClient.start();
   if (choice == 1) {
       System.out.println("Type 'exit' to leave the chat");

       // Use ClientPortHandler to get the valid port input
       ClientPortHandler portHandler = new ClientPortHandler();
       int port = portHandler.getPortInput();  // This will keep prompting for a valid port

       // Now create the client and run it
       Client client = new Client(port);
       client.run();
   }
} catch (Exception e) {
   e.printStackTrace();
}
}}

