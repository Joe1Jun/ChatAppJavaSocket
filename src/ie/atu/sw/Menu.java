package ie.atu.sw;

import java.util.Scanner;

public class Menu {

	private Scanner input = new Scanner(System.in);
	private boolean keepRunning = true;
	
	
	
	
	
	
	public int start() throws Exception {

		input = new Scanner(System.in);

		while (keepRunning) {
			// The while loop will continue to run until the user selects the quit option
			// which sets the keepRunning boolean value to false.
			showOptions();
			// Read the user's input as a string first
	        String userInput = input.nextLine().trim();

			try {
				// Parse the user input to an Integer.
				int choice = Integer.parseInt(userInput);

				return choice;
				// Catch block will print a error message to the user if they don't enter a
				// valid input.
				
			} catch (NumberFormatException e) {
				//Catch InputMismatchException (non-integer input) and ask the user to enter a valid choice.
	            ConsoleUtils.printError("Invalid input. Please enter a number between 1 and 6.");
	            
	            
			}

		}
		return 0;

	}
	
	
	
		
//		private void processChoice(int choice) throws Exception {
//			switch (choice) {
//			case 1 -> 
//			case 2 ->
//			case 3 ->
//
//			
//			default -> ConsoleUtils.printError("Invalid input. Please select a number from 1 to 3.");
//			}
//		}
		
	

	
	
  private void showOptions() {
		
		
		
		
		
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*             Java Socket Chat App                         *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Enter the chat room");
		System.out.println("(2) Download full conversation");
		System.out.println("(3) Private chat");
		System.out.print("Select Option");
		System.out.println();
	}
	
	



}
