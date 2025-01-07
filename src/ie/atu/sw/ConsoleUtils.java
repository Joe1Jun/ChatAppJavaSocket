package ie.atu.sw;

/**
 * The {@code ConsoleUtils} class provides coloured messages to the user.
 * The class is static so it is accessible globally.
 * The colours are accessed via the {@link ConsoleColour} enum.
 * 
 * @see ConsoleColour
 * 
 * @author Joe Junker
 * @version 1.0
 * @since 21
 */
public class ConsoleUtils {
	
	
	
	/**
	 * Prints the message passed from the other methods in the colour passed
	 * from the other methods.
	 * 
	 * @param message The message passed from the other methods in this class
	 * @param color    The colour passed from the other methods in this class
	 */
    public static void printlnColored(String message, ConsoleColour colour) {
        System.out.print(colour);
        System.out.println(message);
        System.out.print(ConsoleColour.RESET);
        
    }
    
    /**
     * Prints in red colour.
     * Used for printing errors.
     * 
     * @param message The message passed from any class that uses this method.
     */

    // Method to print error messages (Red color)
    public static void printError(String message) {
        printlnColored(message, ConsoleColour.RED_BRIGHT);
    }
    
    /**
     * Prints in green.
     * Used when the action is a success.
     * 
     * @param message The message passed from any class that uses this method.
     */

    // Method to print success messages (Green color)
    public static void printSuccess(String message) {
        printlnColored(message, ConsoleColour.GREEN);
    }
    
    /**
     * Print promts in yellow colour.
     * 
     * @param message The message passed from any class that uses this method.
     */

   
    public static void printPrompt(String message) {
        printlnColored(message, ConsoleColour.YELLOW_BRIGHT);
    }

    /**
     * Print headers in Bold Bright Green color
     * 
     * @param message The message passed from any class that uses this method.
     */

    
    public static void printHeader(String message) {
        printlnColored(message, ConsoleColour.CYAN);
    }


}
