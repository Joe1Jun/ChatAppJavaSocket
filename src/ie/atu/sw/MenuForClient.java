package ie.atu.sw;

import java.util.Scanner;

public class MenuForClient {

	private Scanner input;

	public MenuForClient(Scanner input) {

		this.input = input;
	}

	public int start() throws Exception {

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
			// Catch InputMismatchException (non-integer input) and ask the user to enter a
			// valid choice.
			ConsoleUtils.printError("Invalid input. Please enter a number between 1 and 6.");

		}

		return 0;

	}

	private void showOptions() {

		System.out.println("************************************************************");
	    System.out.println("*                                                          *");
		System.out.println("*           Cleint : Java Socket Chat App                  *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Enter chat? ");
		System.out.println("(2) Private chat");
		System.out.println("(3) Save conversation");
		System.out.print("Select Option");
		System.out.println();
	}

}
