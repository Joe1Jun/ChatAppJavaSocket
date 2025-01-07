package ie.atu.sw;

import java.io.PrintWriter;


public class ConsoleUtils {
	
	
	
	
    public static void printlnColored(PrintWriter out,String message, ConsoleColour colour) {
        System.out.print(colour);
        System.out.println(message);
        System.out.print(ConsoleColour.RESET);
        
    }
    
    
    // Method to print error messages (Red color)
    public static void printError(PrintWriter out, String message) {
        printlnColored(out,message, ConsoleColour.RED_BRIGHT);
    }
    
    

    // Method to print success messages (Green color)
    public static void printSuccess(PrintWriter out,String message) {
        printlnColored(out,message, ConsoleColour.GREEN);
    }
    
   

   
    public static void printPrompt(PrintWriter out,String message) {
        printlnColored(out,message, ConsoleColour.YELLOW_BRIGHT);
    }

   

    
    public static void printHeader(PrintWriter out,String message) {
        printlnColored(out,message, ConsoleColour.CYAN);
    }


}
