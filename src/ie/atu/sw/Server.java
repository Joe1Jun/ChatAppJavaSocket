package ie.atu.sw;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable{

	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(9999);
			
			
		} catch (IOException e) {
			// Handle this later
		}
		
		
	}
	
	
	
	class ConnectionHandler implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
		
	}

}
