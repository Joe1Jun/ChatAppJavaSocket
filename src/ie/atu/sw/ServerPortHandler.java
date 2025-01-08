package ie.atu.sw;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class ServerPortHandler {
	
	
	private Scanner input;
	private int port;

    public ServerPortHandler() {
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
                    port = -1; // Reset to ask for a valid port again
                }

                // Try to bind to the port and check if it's already in use
                if (isPortAvailable(port)) {
                    
                	break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid port number.");
            }
        }

        return port;
    }

    // Method to check if a port is available by trying to bind to it
    private boolean isPortAvailable(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            return true; // Port is available
        } catch (IOException e) {
        	System.out.println("Port in use. Please select another port.");
            return false; // Port is in use
        }
    }
	

}
