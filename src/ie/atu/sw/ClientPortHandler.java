package ie.atu.sw;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientPortHandler {

    private Scanner input;
    private int port;
    
    
    public ClientPortHandler() {
        this.input = new Scanner(System.in);
    }

    public int getPortInput() {
        

        // Keep asking for a valid port number until we get a valid one
        while (true) {
            System.out.println("Enter port number for the server: ");
            try {
                String portInput = input.nextLine().trim();

                // Validate input is an integer
                port = Integer.parseInt(portInput);

                // Check if the port is valid (0 to 65535 range)
                if (port < 0 || port > 65535) {
                    System.out.println("Invalid port number. Please enter a value between 0 and 65535.");
                   
                }

                // Try to bind to the port and check if it's already in use
                if (isPortAvailable(port)) {
                    System.out.println("Server port " + port + " is running.");
                    
                    return port;
                    
                } else {
                    System.out.println("Server port " + port + " not running. Please enter a different port.");
                    
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid port number.");
            }
        }

       
    }

    // Method to check if a port is available by trying to bind to it
    private boolean isPortAvailable(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
        	return false;  // Connection successful, server is running
        } catch (IOException e) {
        	return true; // Unable to connect, server is not running on the port
        }
    }
}


//public static void main(String[] args) {
//    Scanner input = new Scanner(System.in);
//    
//    // Create an instance of the menu (for client actions)
//    MenuForClient menuForClient = new MenuForClient(input);
//    int choice;
//
//    try {
//        choice = menuForClient.start();
//        if (choice == 1) {
//            System.out.println("Type 'exit' to leave the chat");
//
//            // Use ClientPortHandler to get the valid port input
//            ClientPortHandler portHandler = new ClientPortHandler();
//            int port = portHandler.getPortInput();  // This will keep prompting for a valid port
//
//            // Now create the client and run it
//            Client client = new Client(port);
//            client.run();
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}
//}

