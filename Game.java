package assignment2;

import java.util.ArrayList;

/*The Game class represents the Game itself. It holds the minimax algorithm and all helper functions,
 * as well as all of our move-making functions.
 */

public class Game {
	
	public static final String COMPUTER = "Computer";
	public static final String PLAYER = "Player";
	
	//size of board and how many plies used in the minimax search
	int plies;
	int size;
	
	//the current game board represented by state class
	State current; 
	
	//keep track of whose turn 
	boolean player;
	boolean computer; 
		
	//is the game over?
	boolean isOver;
	
	//Constructor
	public Game(int plies, int size) {
		
		this.plies = plies;
		this.size = size;
		
		current = new State(size);
		
		//player goes first
		this.player= true;
		this.computer = false;
		
		this.isOver=false;
		
	}
	
	//Automates the computer's move: makes decision using minimax and conducts the move
	public void automatedMove() {
		
		//check if it is in fact the computer's turn 
		if(!computer) System.out.println("It is not the computer's move");
		
		//Use miniMax method to find the best move to take 
		int[] move = miniMax(); 
		
		//join the dots specified in "move"
		current.joinDots(move,COMPUTER); 
		current.printState(); 
		System.out.println("Computer connected " + move[0] + " and " + move[1]);
		
		//switch the turn 
		turnOver(); 
		
	}
	
	//Automates the computer's move: makes decision using minimax w/ alpha beta pruning and conducts the move
	public void automatedMoveAB() {
		
		//check if it is in fact the computer's turn 
		if(!computer) System.out.println("It is not the computer's move");
		
		//Use miniMaxAB method to find the best move to take 
		int[] move = miniMaxAB(); 
		
		//join the dots specified in "move"
		current.joinDots(move,COMPUTER); 
		current.printState(); 
		System.out.println("Computer connected " + move[0] + " and " + move[1]);
		
		//switch the turn 
		turnOver(); 
		
	}
	
	//Human Player makes move: this groups together the move + the update
	public void makeMove(int[] move) {
		
		//check if it is in fact the player's turn 
		if(!player) System.out.println("It is not the player's move");
		
		//join the dots specifed in "move"
		current.joinDots(move,PLAYER);
		current.printState();
		
		//switch the turn
		turnOver();
	}

	/*Minimax algorithm: returns the action that leads to the outcome with the best utility, 
	 * with assumption that opponent plays to minimize utility
	 */
	public int[] miniMax() {
		
		//used to store the best value thus far, and the action that produced it
		int[] action = null;
		int v=Integer.MIN_VALUE;
		
		//ArrayList to store all the possible actions from current state
		ArrayList<int[]> actions = current.possibleMoves();
		
		//for each possible action 
		for(int[] i: actions) {
		
			//take the action on the current state 
			//save the value returned - this is the change in score
			int value = current.joinDots(i, COMPUTER);
					
			//call the minValue method to find what the opposite player would choose
			int temp = minValue(current, this.plies);
	     
			//check if this value is larger than what we have thus far + make updates 
			if( temp > v) {
				v= temp;
				action = i;
			}
			
			//after the recursion, we undo our changes to get back to the OG state
			current.undoJoinDots(i,COMPUTER);
			current.compScore-=value;
		
		}
		return action; 
		
	}
	
	// Helper function for MiniMax
	private int minValue(State state, int ply) {
		
		//Is this a terminal state?
		if(isTerminal(state,ply)) { return state.findUtility();}

		// store the min value so far, and all the possible actions from this state
		int v = Integer.MAX_VALUE;
		ArrayList<int[]> actions = state.possibleMoves();
		
		//for each possible action..
		for(int[] i: actions) {
						
			//take the action on the current state 
			//save the value returned - this is the change in score
			int increased = state.joinDots(i, PLAYER);
			
			//call max to find what the the other player, and compare it to v
			v = Integer.min(v, maxValue(state,ply-1));

			//after our recursion, we undo our changes to get back to the OG state
			state.undoJoinDots(i, PLAYER);
			state.playerScore-=increased;
		}

		return v;
	}
	
	//helper function for minimax
	private int maxValue(State state, int ply) {
		
		//Is this a terminal state?
		if(isTerminal(state,ply)) { return state.findUtility();}
		
		// store the min value so far, and all the possible actions from this state
		int v = Integer.MIN_VALUE;
		ArrayList<int[]> actions = state.possibleMoves(); 

		//for each possible action...
		for(int[] i: actions) {
			
			//take the action on the current state 
			//save the value returned - this is the change in score
			int increased=state.joinDots(i, COMPUTER);
			
			//call max to find what the the other player will most likely do, and compare it to v
			v = Integer.max(v, minValue(state, ply-1));
			
			//after our recursion, we undo our changes to get back to the OG state
			state.undoJoinDots(i, COMPUTER);
			state.compScore-=increased;
		}
		return v;
	}
	
	//** ATTEMPT AT ALPHA BETA PRUNING **//
	//Alpha beta pruning eliminates some of the game states it has to observe
	
	// Alpha Beta Pruning Minimax algorithm: finds the best action to take given the current state and number of plies
	public int[] miniMaxAB() {
		
		//used to store the best value thus far, and the action that produced it
		int[] action = null;
		int v=Integer.MIN_VALUE;
		
		int alpha=Integer.MIN_VALUE; // highest value choice we've found so far on path for max
		int beta =Integer.MAX_VALUE; // lowest value choice we have found so far on path for min
		
		//ArrayList to store all the possible actions from current state
		ArrayList<int[]> actions = current.possibleMoves();
		
		//for each possible action 
		for(int[] i: actions) {
		
			//take the action on the current state 
			//save the value returned - this is the change in score
			int value = current.joinDots(i, COMPUTER);
					
			//call the minValue method to find what the opposite player would choose
			int temp = minValueAB(current, this.plies,alpha,beta);
	     
			//check if this value is larger than what we have thus far + make updates 
			if( temp > v) {
				v= temp;
				action = i;
			}
			
			//after the recursion, we undo our changes to get back to the OG state
			current.undoJoinDots(i,COMPUTER);
			current.compScore-=value;
		
		}
		return action; 
		
	}
	
	// Helper function for MiniMax
	private int minValueAB(State state, int ply, int alpha, int beta) {
		
		//Is this a terminal state?
		if(isTerminal(state,ply)) { return state.findUtility();}

		// store the min value so far, and all the possible actions from this state
		int v = Integer.MAX_VALUE;
		ArrayList<int[]> actions = state.possibleMoves();
		
		//for each possible action..
		for(int[] i: actions) {
			
			//take the action on the current state 
			//save the value returned - this is the change in score
			int increased = state.joinDots(i, PLAYER);
			
			//call max to find what the the other player, and compare it to v
			v = Integer.min(v, maxValueAB(state,ply-1,alpha,beta));
			//make correct alpha/beta corrections
			if( v<=alpha) { 
			
			state.undoJoinDots(i, PLAYER);
			state.playerScore-=increased;
			return v;
			
			}
			beta=Integer.min(beta, v);

			
			//after our recursion, we undo our changes to get back to the OG state
			state.undoJoinDots(i, PLAYER);
			state.playerScore-=increased;
			}

		return v;
	}
	
	//helper function for minimax
	private int maxValueAB(State state, int ply, int alpha, int beta) {
		
		//Is this a terminal state?
		if(isTerminal(state,ply)) { return state.findUtility();}
		
		// store the min value so far, and all the possible actions from this state
		int v = Integer.MIN_VALUE;
		ArrayList<int[]> actions = state.possibleMoves(); 

		//for each possible action...
		for(int[] i: actions) {
			
			//take the action on the current state 
			//save the value returned - this is the change in score
			int increased=state.joinDots(i, COMPUTER);
			
			//call max to find what the the other player will most likely do, and compare it to v
			v = Integer.max(v, minValueAB(state, ply-1,alpha,beta));
			
			//make proper alpha/beta corrections
			if( v >= alpha) {
				state.undoJoinDots(i, COMPUTER);
				state.compScore-=increased;
				return v;
			}
			beta = Integer.max(beta, v);
			
			//after our recursion, we undo our changes to get back to the OG state
			state.undoJoinDots(i, COMPUTER);
			state.compScore-=increased;
		}
		return v;
	}
	
	/*
	 * Terminal function used within minimax 
	 * Returns true if the state is terminal, or if plies have run out
	 */
	public boolean isTerminal(State state, int ply) {
		
		//if current ply = to the one specified
		if(ply==0) return true;
		
		//is it terminal? 		
		return state.isTerminal();
		
	}
	
	
	//** METHODS FOR CLIENT/TEXT INTERFACE **//
	
	//Checks if the Game is Over - is the state terminal?
	public boolean isOver() {
		return current.isTerminal();	
	}
	
	//Gets the current player: Player or Computer
	public String getCurrent() {
		if(player) return PLAYER;
		else return COMPUTER;
	}
	
	//switches players when turn is over
	public void turnOver() {
		if(player) { player = false; computer = true; } 
		else {player = true; computer = false; } 
	}
	
	public String getScore() {
		return "Player: " + current.playerScore + " Computer: " + current.compScore;
	}
	
	public String getWinner() {
		if(current.playerScore>current.compScore) return "Player wins!";
		else if (current.playerScore<current.compScore) return "Computer wins!";
		else return "It's a tie!";
	}
	
}
			
		

