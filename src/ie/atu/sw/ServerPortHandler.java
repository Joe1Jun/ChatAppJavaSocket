package ie.atu.sw;

import java.io.IOException;
import java.net.ServerSocket;
import java.security.CodeSource;
import java.util.Scanner;

public class ServerPortHandler {
	
	
	private Scanner input;
	private int port;

    public ServerPortHandler(Scanner input) {
        this.input = input;
    }

    public int getPortInput() {
       

        // Keep asking for a valid port number until a valid one is chosen
        while (true) {
        	ConsoleUtils.printPrompt("Enter port number for the server: ");
            try {
                String portInput = input.nextLine().trim();

                // Validate input is an integer
                port = Integer.parseInt(portInput);

                // Check if the port is valid (0 to 65535 range)
                if (port < 0 || port > 65535) {
                	ConsoleUtils.printPrompt("Invalid port number. Please enter a value between 0 and 65535.");
                    continue;  // Skip the rest of the loop and ask for input again
                }

                // Try to bind to the port and check if it's already in use
                if (isPortAvailable(port)) {
                    // breaks if the port is not in use
                	break;
                }
            } catch (NumberFormatException e) {
            	// If not an integer the catch will catch it
            	ConsoleUtils.printError("Invalid input. Please enter a valid port number.");
            }
        }
      // returns the port if valid
        return port;
    }

    // Method to check if a port is available by trying to bind to it
    // The opposite to the CLientPortHandler
    // Here the program is looking for a port thats not in use
    private boolean isPortAvailable(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            return true; // Port is available
        } catch (IOException e) {
        	ConsoleUtils.printPrompt("Port in use. Please select another port.");
            return false; // Port is in use
        }
    }
	

}
