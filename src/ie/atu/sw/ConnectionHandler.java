package ie.atu.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
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

	@Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Get client's name
            
           enterName();
           
           
            String message;
            while ((message = in.readLine()) != null) {
                if (message.equals("\\q")) {
                	server.broadcast(clientName + " has left the chat !");
                	closeClientConnection();
                    break;
                }
                server.broadcast(clientName + ": " + message);
            }
            
            
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
            sendMessage(clientName);
        } finally {
            closeClientConnection();
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
                    sendMessage("Please input a proper name.");  // Send error to client
                } else {
                    break;  // Exit loop if valid name is entered
                }
            } catch (IOException e) {
                sendMessage("Error: Unable to read name. Please try again.");
                e.printStackTrace();
            }
        }

        // After name is entered, broadcast that the client has entered the chat
        server.broadcast(clientName + " has entered the chat");
		
	}

	public void closeClientConnection() {
    	//Removes the current client from the connections list
    	server.removeClient(this);
    	 try {
    	        clientSocket.close();  // Close the client socket to stop communication
    	    } catch (IOException e) {
    	        e.printStackTrace();  // Handle the error gracefully if closing the socket fails
    	    }
    	
}

	public void sendMessage(String message) {
    	//Uses the print writer to print the message from the client to the screen
        out.println(message);
    }
}





//    private void closeConnection() {
//        server.removeClient(this);
//        server.broadcast(clientName + " has left the chat!", this);
//        try {
//            if (socket != null && !socket.isClosed()) {
//                socket.close();
//            }
//        } catch (IOException e) {
//            System.out.println("Error closing client connection: " + e.getMessage());
//        }
//    }
//}

//try {
//	// This is the output stream initialised and the auto flush set to through so there is 
//	// no need to manually flush the output stream
//	out = new PrintWriter(client.getOutputStream(), true);
//	// This will read in the input stream from the client
//	in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//	
//	
//	// ****Add edge cases etc to make this more robust****
//	out.println("Please enter a name");
//	// assigns the variable the value of the line read from the client
//	name = in.readLine();
//	// This will be displayed for that instance of the thread
//	System.out.println(name + " connected");
//	// This will be broadcast and will be seen by all threads connected
//	broadcast(name + " joined the chat");
//	
//	String message;
//	// Loop continuously to handle messages from the clients
//	while((message = in.readLine()) != null) {
//		// Check if the message starts with the "/name" command for renaming the user
//		if(message.startsWith("/name" )) {
//			// Split the message into two parts: the command and the new name
//			String [] messageSplit = message.split(" ", 2);
//			// If the split message contains two parts (command and new name)
//			if(messageSplit.length == 2) {
//				// Broadcast to all clients that the user has renamed themselves
//				broadcast(name + "renamed themselves to " + messageSplit[1]);
//				// Log the renaming action on the server console
//				System.out.println(name + "renamed themselves to " + messageSplit[1]);
//				// Update the user's name
//				name = messageSplit[1];
//				out.println("Successfully changed name to " + name);
//			} else {
//				out.println("No name provided");
//			}
//		}else if(message.equals("/q")) {
//			// If the message is the "/q" command, allow the client to quit the chat
//			
//			broadcast(name + " left the chat !");
//		}else {
//			 // For all other messages, broadcast the message to all connected clients
//			broadcast(name + " : " + message);
//		}
//	}
//	
//	
//} catch (IOException e) {
//	shutdown();
//	
//	
//}
//
//
//
//}
	
	
	
