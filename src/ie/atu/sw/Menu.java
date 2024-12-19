package ie.atu.sw;

import java.util.Scanner;

public class Menu {

	private Scanner input = new Scanner(System.in);
	
	
	public int start() {
		
		showOptions();
		
		try {
			
     	return input.nextInt();
  
        
       
     } catch (NumberFormatException e) {
       
     }
		return 0;
     
 }
	
	
  private void showOptions() {
		
		
		
		
		
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*             Java Socket Chat App                         *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Enter the chat room");
	
		System.out.print("Select Option");
		System.out.println();
	}
	
	



}
