package ie.atu.sw;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

	
	// Thread safe array list needed when using threads
	private CopyOnWriteArrayList<ConnectionHandler> clientConnections = new CopyOnWriteArrayList<>();
	private int port;
	private boolean keepRunning;

	public Server(int port) throws IOException {
		this.port = port;
		this.keepRunning = true;

	}

	@Override
	public void run() {
		// Try-with-resources to manage the virtual thread pool and ServerSocket
		// Infinite loop to continuously accept and handle client connections.
		System.out.println("To shut down the server type shutdown");

		try (ServerSocket serverSocket = new ServerSocket(port);
				ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor()) {
			System.out.println("Server started on PORT " + port);

			while (keepRunning) {
				System.out.println("Waiting for clients......");

				// Waits for an incoming client connection and returns a socket representing it.
				Socket connection = serverSocket.accept();
				// Prints the client and the address they connected from
				System.out.println("Client connected from " + connection.getInetAddress() + ":" + connection.getPort());
				// Created a new instance of the object that handles all client connections.
				ConnectionHandler connectionHandler = new ConnectionHandler(connection, this);
				// This add all the connections to the connection array list
				clientConnections.add(connectionHandler);
				pool.execute(connectionHandler);
			}
		} catch (IOException ex) {
			// Handle exceptions that occur while accepting a connection.
			System.err.println("Error accepting connection: " + ex.getMessage());
			shutdown();
		}
	}

	// This will broadcast a message to all client currently connected
	// This will loop through the connection arrayList and send the message using
	// the command line

	public void broadcast(String message) {

		System.out.println("Broadcasting message: " + message); // Log the message
		// Sends the message sent by each thread to all the connections saved to the
		// arrayList
		for (ConnectionHandler ch : clientConnections) {
			// If their are connections a message will be sent
			if (ch != null) {
				ch.sendMessage(message);
			}
		}
	}

	public void removeClient(ConnectionHandler clientConnection, String name) {
         // Removes the client from the array list
		clientConnections.remove(clientConnection);
		// Broadcasts to the whole chat that the client has left the chat
		broadcast(name + " has left the chat");
	}

	public void shutdown() {
		// Notify all clients about the shutdown
		broadcast("Server is shutting down");

		// Stop accepting new clients
		keepRunning = false;

		// Close all active client connections
		// Runs a for each loop to close all the connections 
		for (ConnectionHandler ch : clientConnections) {
			try {
				ch.closeConnection();
			} catch (Exception e) {
				System.err.println("Failed to close client connection: " + e.getMessage());
			}
		}

		System.out.println("Server has been shut down.");
	}

	public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Menu menu = new Menu(input);
        int choice;

        try {
            choice = menu.start();
            if (choice == 1) {
            	ConsoleUtils.printHeader("Type 'shutdown' to shutdown server");

                // Use the PortInputHandler to get a valid port number
                // Thgis class runs all the validations for the server port input
                ServerPortHandler portInputHandler = new ServerPortHandler(input);
                int port = portInputHandler.getPortInput();

                // Create new server object with the returned port number
                Server server = new Server(port);
                // Use a seperate thread here to  run the method in the server class 
                // This means that the main thread will not be affected which allows the user to
                // shutdown the server with a command.
                // This allow the users to recieve a message that the server is shutting down
                Thread serverThread = new Thread(server);
                serverThread.start();

                while (true) {
                    String command = input.next();
                    if ("shutdown".equalsIgnoreCase(command)) {
                        server.shutdown();
                        
                        break; // Exit the loop and stop the program
                    }
                }

            } else {
            	ConsoleUtils.printPrompt("Please select a valid option");
                menu.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



