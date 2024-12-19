package ie.atu.sw;

import java.net.Socket;
import java.io.*;

public class Client implements Runnable{
	// Port number on which the server is running
	public static final int PORT = 9999;
	// Hostname of the server to connect to, set to localhost for testing on the same machine
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	
	
	@Override
	public void run() {
		
		try {
			// Create a new socket and connect it to the specified hostname and port
            // This establishes a TCP connection between the client and the server
			client = new Socket("127.0.0.1", PORT);
			// Initialises the output stream for sending data to the server
			// The 'true' parameter enables auto-flushing, so there is no need to call flush manually after each println
			out = new PrintWriter(client.getOutputStream(), true);
			// Initialises the input stream for receiving data from the server
			// Wraps the socket's input stream with InputStreamReader and BufferedReader for efficient reading of character streams
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			
		} catch (IOException e) {
			// TODO: handle exception
		}
		
	}
	
	
	
	

}
