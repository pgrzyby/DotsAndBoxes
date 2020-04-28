package assignment2;

//import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;

//import java.awt.Graphics2D;
import java.util.ArrayList;

//import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


	public class GameTest {
//		
//		@Test
//		@DisplayName("Make a Box")
//		void testBox(){
//			Dot one = new Dot(0,0);
//			Dot two = new Dot(1,0);
//			Dot three = new Dot(0,1);
//			Dot four = new Dot(1,0);
//			
//			ArrayList<Dot> dots = new ArrayList<Dot>();
//			dots.add(one);
//			dots.add(two);
//			dots.add(three);
//			dots.add(four);
//			
//			Box box = new Box(one, two,three, four);
//			
//			
//		}
//		
//		@Test
//		@DisplayName("Make all the dots upon game creation")
//		void testMakeDots(){
//			
//			Game game = new Game(1, 2); //make 2x2 board
//			game.printState();
//			
//			System.out.println();
//			System.out.println();
//			
//			Game game1 = new Game(1, 3); //make 2x2 board
//			game1.printState();
//			
//			System.out.println();
//			System.out.println();
//			
//			Game game2 = new Game(1, 4); //make 2x2 board
//			game2.printState();
//			
//			System.out.println();
//			System.out.println();
//			
//			Game game3 = new Game(1, 5); //make 2x2 board
//			game3.printState();
//		}
//		
//		@Test
//		@DisplayName("Add some sides")
//		void testConnect(){
//			
//			Game g = new Game(2,2);
//		//	g.joinDots(0,1);
//		//	g.printState();
//			
//			
//			Game g1 = new Game(2,3);
////			System.out.println();
////			System.out.println();
//			g1.printState();
//			g1.joinDots(0,1);
//			g1.joinDots(3,4);
////			System.out.println();
////			System.out.println();
//			g1.printState();
//			
//			g1.joinDots(1,4);
////			g1.joinDots(6, 3);
////			g1.joinDots(5, 8);
//			System.out.println();
//			System.out.println();
//			
//			g1.joinDots(1,2);
//			g1.joinDots(0,3);
//			g1.joinDots(3,4);
//			g1.joinDots(4,5);
//			g1.joinDots(2,5);
//			g1.joinDots(1,2);
//			g1.joinDots(1,2);
//			
//			
//			//System.out.println(g1.state.get(0).toString());
//			g1.printState();
//			
//		}
//		
//	@Test
//	@DisplayName("Test Turn Taking")
//	void testTurns(){
//
//		Game g1 = new Game(2,4);
//		System.out.println();
//		System.out.println();
//		g1.printState();
//		//1
//		g1.joinDots(0,1);
//		g1.printState();
//		g1.turnOver();
//		//2
//		g1.joinDots(1,5);
//		g1.printState();
//		g1.turnOver();
//		//3
//		g1.joinDots(4,5);
//		g1.printState();
//		g1.turnOver();
//		//4
//		g1.joinDots(0,4);
//		g1.printState();
//		g1.turnOver();
//		
//		assertTrue(g1.compScore > g1.humanScore);
//	}
//	
//	@Test
//	@DisplayName("Test Order of dots")
//	void testDotOrder(){
//
//		Game g1 = new Game(2,4);
//		System.out.println();
//		System.out.println();
//		g1.printState();
//		
//		
//		//will it still conect with the larger dot in front
//		g1.joinDots(1,0);
//		g1.printState();
//		g1.turnOver();
//		
//		g1.joinDots(4,0);
//		g1.printState();
//		g1.turnOver();
//
//	}
//	
	@Test
	@DisplayName("")
	void testMoves(){

		Game g1 = new Game(2,4);
		g1.current.printState();
		
		
		ArrayList<int[]> moves = g1.current.possibleMoves();
		
		assertEquals(24, moves.size());
			
		for(int[] a : moves) {
			System.out.println("[" + a[0] +", "+ a[1] + "]");
		}
		
		assertEquals(24, moves.size());
		
		//System.out.println(g1.current.possibleMoves().toString());
		
	//	g1.result(new int[] {1,2}, "comp");
		//

	}
	
	@Test
	void testMiniMax() {
//		Game g1 = new Game(2,4);
//		g1.current.printState();
//		
//		int[] answer = g1.miniMax();
//		System.out.println("Take this move: " + answer[0] + ", " + answer[1]);

		
		//when there's three lines, will it pick the move to close the box?
		Game g2 = new Game(2,3); //2 plies, 3x3
		g2.current.joinDots(new int[] {0,1}, "comp");
		g2.current.printState();
		g2.current.joinDots(new int[] {0,3}, "human");
		g2.current.printState();
		g2.current.joinDots(new int[] {1,4}, "comp");
		
		
		//int[] answer2 = g2.miniMax();
		//System.out.println("Take this move: " + answer2[0] + ", " + answer2[1]);
		
		//when there's 2 potential boxes, will it chose the move to close the larger box?
		Game g3 = new Game(2,3); //2 plies, 3x3
		g3.current.joinDots(new int[] {0,1}, "comp");
		g3.current.joinDots(new int[] {0,3}, "human");
		g3.current.joinDots(new int[] {1,4}, "comp");
		g3.current.joinDots(new int[] {1,2}, "comp");
		g3.current.joinDots(new int[] {2,5}, "human");
		g3.current.printState();
		
		
		int[] answer3 = g3.miniMax();
		System.out.println("Take this move: " + answer3[0] + ", " + answer3[1]);

	}
	
	@Test
	void testMiniMax2() {
		//enough plies to guess the first box?
		Game g2 = new Game(8,3); //2 plies, 3x3
		g2.current.joinDots(new int[] {0,1}, "comp");
		g2.current.printState();
		g2.current.joinDots(new int[] {0,3}, "human");
		g2.current.printState();
		g2.current.joinDots(new int[] {1,4}, "comp");
		
		int[] answer = g2.miniMax();
		System.out.println("Take this move: " + answer[0] + ", " + answer[1]);

	}		
		
		
		
		
		
		
	}
	
	
	
	
