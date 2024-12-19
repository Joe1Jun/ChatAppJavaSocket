package ie.atu.sw;

import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable{
	// Port number on which the server is running
	public static final int DAYTIME_PORT = 9999;
	// Hostname of the server to connect to, set to localhost for testing on the same machine
	private String hostname = "localhost"; 
	@Override
	public void run() {
		
		try {
			// Create a new socket and connect it to the specified hostname and port
            // This establishes a TCP connection between the client and the server
			Socket socket = new Socket(hostname, DAYTIME_PORT);
			
			
			
		} catch (IOException e) {
			// TODO: handle exception
		}
		
	}
	
	
	
	

}
