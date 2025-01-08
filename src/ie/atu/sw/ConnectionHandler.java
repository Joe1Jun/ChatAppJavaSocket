package ie.atu.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;

// This class will handle all connection from each server type
// Most implementation had this as a private class within server
// This version is a bit cleaner as this class has a lot of methods.

public class ConnectionHandler implements Runnable {

	private Socket clientSocket;
	private Server server;
	private BufferedReader in;
    private PrintWriter out;
	private String clientName;

	// Creates instance of this class with the objects passed from the Server class

	public ConnectionHandler(Socket clientSocket, Server server) {

		this.clientSocket = clientSocket;
		this.server = server;
	}

	// The overridden method from interface runnable
	@Override
	public void run() {

		try {
			// Create streams outside try-with-resources since we need them for the instance
			// lifetime
			this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);

			// Get client's name
			enterName();
			// The server listens for messages send from all the connections via output
			// stream in
			// the client class.
			String message;
			// Messages are read in but the buffered reader unless exit is read in

			while ((message = in.readLine()) != null) {
				if (message.equalsIgnoreCase("exit")) {
					// If the user types exit the close Connection method is triggered
					closeConnection();
					break;
				}
				server.broadcast(clientName + ": " + message);
			}
		} catch (IOException e) {
			System.out.println("Error handling client: " + e.getMessage());
			sendMessage(clientName);
		} finally {
			closeConnection();
		}
	}

// Uses a while loop to make sure the user inputs a non null name
	private void enterName() {

		while (true) {
			// Writes via printwriter to the clients console
			out.println("Enter your name: ");
			try {
				clientName = in.readLine();

				// If the name is empty the loop keepRunning
				if (clientName == null || clientName.isEmpty()) {
					sendMessage("Please input a proper name."); // Send error to client
				} else {
					break; // Exit loop if valid name is entered
				}
			} catch (IOException e) {
				sendMessage("Error: Unable to read name. Please try again.");
				e.printStackTrace();
			}
		}

		// After name is entered, broadcast that the client has entered the chat
		server.broadcast(clientName + " has entered the chat");

	}

	public void sendMessage(String message) {
		// Uses the print writer to print the message from the client to the screen
		out.println(message);
	}

	public void closeConnection() {
		try {
			server.removeClient(this, clientName);
			//Closes the socket which should close the input and output reader of that socket
			if (clientSocket != null) {
				clientSocket.close();
			}
		} catch (IOException e) {
			ConsoleUtils.printError("Error closing connection");
		}
	}
}
