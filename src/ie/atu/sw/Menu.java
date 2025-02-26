package ie.atu.sw;

import java.util.Scanner;

public class Menu {

	private Scanner input;

	public Menu(Scanner input) {

		this.input = input;
	}

	public int start() throws Exception {

		input = new Scanner(System.in);

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
			System.out.println("Invalid input");

		}

		return 0;

	}
// SHows the user options
	private void showOptions() {
		System.out.println(ConsoleColour.PURPLE_BRIGHT);
		System.out.println("************************************************************");
	    System.out.println("*                                                          *");
		System.out.println("*           Server : Java Socket Chat App                  *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Start the server");
		System.out.println("....Type 1 to start the server");
		System.out.print(  "....Select Option");
		System.out.println();
	}

}
