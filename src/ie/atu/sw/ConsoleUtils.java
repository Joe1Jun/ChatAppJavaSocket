package ie.atu.sw;

// This class was created as part of my project from the summer 2023 for the OOP module delivered by 
// John Healy.

public class ConsoleUtils {
	
	
	
	
	 
    public static void printlnColored(String message, ConsoleColour colour) {
        System.out.print(colour);
        System.out.println(message);
        System.out.print(ConsoleColour.RESET);
        
    }
    
    
    // Method to print error messages (Red color)
    public static void printError(String message) {
        printlnColored(message, ConsoleColour.RED_BRIGHT);
    }
    
   

    // Method to print success messages (Green color)
    public static void printSuccess(String message) {
        printlnColored(message, ConsoleColour.GREEN);
    }
    
  

   
    public static void printPrompt(String message) {
        printlnColored(message, ConsoleColour.YELLOW_BRIGHT);
    }

    

    
    public static void printHeader(String message) {
        printlnColored(message, ConsoleColour.CYAN);
    }


}
