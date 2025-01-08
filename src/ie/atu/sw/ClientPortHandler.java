package ie.atu.sw;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientPortHandler {

    private Scanner input;
    private int port;
    
    // Initilaise the instance of this class
    // The scanner is passed to avoid issue with multiple scanners
    public ClientPortHandler(Scanner input) {
        this.input = input;;
    }

    public int getPortInput() {
        

        // Keep asking for a valid port number until a valid one is chosen
        while (true) {
            System.out.println("Enter port number for the server: ");
            try {
                String portInput = input.nextLine().trim();

                // Validate input is an integer
                port = Integer.parseInt(portInput);

                // Check if the port is valid (0 to 65535 range)
                if (port < 0 || port > 65535) {
                	ConsoleUtils.printPrompt("Invalid port number. Please enter a value between 0 and 65535.");
                    continue;  // Skip the rest of the loop and ask for input again
                }

               
                // If it can bind then the port it is not running
                if (isPortAvailable(port)) {
                	ConsoleUtils.printPrompt("Server port " + port + " is running.");
                    // return the port if running
                    return port;
                    
                } else {
                	ConsoleUtils.printPrompt("Server port " + port + " not running. Please enter a different port.");
                    
                }
                // This will catch an input that cant be parsed to an integer
            } catch (NumberFormatException e) {
            	ConsoleUtils.printError("Invalid input. Please enter a valid port number.");
            }
        }

       
    }

    // Method to check if a port is available by trying to bind to it
    // If the serverSocket can bind it means the port is not running
    private boolean isPortAvailable(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
        	// Connection successful, server is running
        	return false;  
        } catch (IOException e) {
        	// Unable to connect, server is not running on the port
        	return true; 
        }
    }
}


