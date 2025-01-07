//package ie.atu.sw;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class ClientHandler implements Runnable {
//	
//	
//	private Socket socket;
//	private Scanner input;
//	private boolean keepRunning = true;
//	
//	
//	public ClientHandler(Socket socket, Scanner input) {
//		super();
//		this.socket = socket;
//		this.input = input;
//		
//	}
//
//
//
//        @Override
//		public void run() {
//			
//			try {
//				// Create a BufferedReader to read user input from the console
//				BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
//				
//				// Loop continuously until the keepRuning flag is set to false
//				while(keepRunning) {
//					 // Read a line of input from the user
//					String message = inReader.readLine();
//					// If the user inputs the "q" command
//					 System.out.println("ChatApp: " + message);
//					
//					if(message.equals("q")) {
//						// Close the console input reader
//						inReader.close();
//						 // Call the shutdown method to terminate the connection
//						shutdown();
//					} else {
//						out.println(message);
//					}
//					
//				}
//				
//			
//				
//			} catch (Exception e) {
//				 // Handle any exceptions that occur while reading user input
//                e.printStackTrace();
//                shutdown();
//			}
//			
//		}
//		
//		
//		
//		
//		
//	}
//}
