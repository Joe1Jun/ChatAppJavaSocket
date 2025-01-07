package ie.atu.sw;

import java.util.Scanner;

public class Runner {
	
	
	//Might make into 2 seperate mains one to run the server and another to run the client
	
	
public static void main(String[] args) throws Exception {
		
		
		Scanner input = new Scanner(System.in);
		
		Menu menu = new Menu();
		int choice = menu.start();
		
		if(choice == 1) {
			
			System.out.println("Specify the port number for the server");
			 
			 int port  = input.nextInt();
			 
			 Server server = new Server(port);
			    // Run the server in a separate thread
	            Thread serverThread = new Thread(server);
	            serverThread.start();
			 // 
		     Client client = new Client(port);
			
			client.run();
			
		}
}
		
//		Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter server IP address: ");
//        String host = scanner.nextLine();
//        System.out.print("Enter server port: ");
//        int port = scanner.nextInt();
//
//        try {
//            ChatClient client = new ChatClient(host, port);
//            client.start();
//        } catch (IOException e) {
//            System.out.println("Could not connect to server: " + e.getMessage());
//        }
//    }
//}
		

}
