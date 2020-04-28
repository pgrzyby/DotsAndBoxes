package assignment2;

import java.util.Scanner;

/*Starts the actual game. Takes input from the Player, and automates the 
 * computer's move by calling the minimax algorithm.
 * Prints the Board and moves taken to the screen. 
 */

public class Client {

	
	   public static void main(String[] args){
		
	    int size;
	    int plies;
	    int[] dots=new int[2];
	    Scanner scan = new Scanner(System.in);
	    
	    System.out.println("Welcome to the Dots and Boxes Game!");
	    System.out.println("Enter dimension of board: "); 
	    size=scan.nextInt();
	    System.out.println("Enter number of plies for minimax search:");
	    plies= scan.nextInt();
	    
	    //Create the game
	    Game g1 = new Game(plies, size);
	    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`");
	    System.out.println(size+ "x" + size + " Board Successfully Created\n");
	    g1.current.printState();
	    
	    do
	    {
	        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
	    	System.out.println("Your move! Input the dots you'd like to connect");
	    
	    	while(true) {
		    	dots[0] = scan.nextInt();
		        dots[1] = scan.nextInt();
		        try {
		        g1.makeMove(dots);
		        break;
		        }catch(IllegalArgumentException e) { System.out.println("Invalid pair, try again!");}
	        }
	    	
		    System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
	        System.out.println("Computer's move!");
	        g1.automatedMove();

	    }
	    while(!g1.isOver()); //while the game isn't over 
	 
	    System.out.println("Final State:");
	    g1.current.printState();
	    System.out.println("GAME OVER!" + g1.getWinner());
	    
	    scan.close();
	    
	}
}
