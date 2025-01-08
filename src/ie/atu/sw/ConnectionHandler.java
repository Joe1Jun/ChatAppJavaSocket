package ie.atu.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;

// This class will handle all connection from each server type.

public class ConnectionHandler implements Runnable {

	private Socket clientSocket;
	private Server server;
	private BufferedReader in;
	// To print information out
	private PrintWriter out;
	private String clientName;

	public ConnectionHandler(Socket clientSocket, Server server) {

		this.clientSocket = clientSocket;
		this.server = server;
	}

	// This 
	@Override
	public void run() {
		try (Socket socket = this.clientSocket; // Auto-closeable socket
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

			// This will read in the input stream from the client
			this.in = in;
			// This is the output stream initialised and the auto flush set to through so
			// there is no need to manually flush the output stream
			this.out = out;

			// Get client's name

			enterName();

			// This reads in each message as it sent to the server and uses
			// the broadcast message in the server class to broadcast the message
			// to the

			String message;
			while ((message = in.readLine()) != null) {
				if (message.equalsIgnoreCase("exit")) {

					closeConnection();

					break;
				}
				server.broadcast(clientName + ": " + message); // Broadcast messages to all clients
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
			out.println("Enter your name: ");
			try {
				clientName = in.readLine();

				// Validate client name
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
		server.removeClient(this, clientName);

	}
}
