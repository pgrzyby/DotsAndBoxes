package assignment2;

import java.util.ArrayList;

/* State class represents the current state of the board. Both the Dots and Boxes
 * are held in ArrayLists. Since my minimax algorithm calculates the best action in place, 
 * there is only ever one State object created.
 */

public class State {

	// hold the Dots and Boxes
	ArrayList<Box> state;
	ArrayList<Dot> dots;

	int playerScore;
	int compScore;

	// size of the board
	int size;

	// Constructor
	public State(int size) {
		this.playerScore = 0;
		this.compScore = 0;
		this.size = size + 1; // dimension refers to number of boxes, not dots

		dots = createDots();
		state = createBoxes();

	}

	// Makes (size^2) Dot objects and puts them in the Array
	private ArrayList<Dot> createDots() {

		ArrayList<Dot> d = new ArrayList<Dot>();
		for (int y = 0; y < this.size; y++) {
			for (int x = 0; x < this.size; x++) {
				d.add(new Dot(x, y, d.size()));
			}
		}
		return d;
	}

	/*
	 * Creates Box objects using groups of four Dot objects Does so by traversing
	 * the Dot array and grouping appropriately Assigns a random value to each box
	 */
	private ArrayList<Box> createBoxes() {

		ArrayList<Box> state = new ArrayList<Box>();

		// Traverse the Dot array
		for (int i = 0; i < (Math.pow(this.size, 2) - this.size); i++) {

			// If the Dot isn't on the right-hand edge of the Board
			if ((i + 1) % this.size != 0) {

				// Assign correct Dots
				Dot one = dots.get(i);
				Dot two = dots.get(i + 1);
				Dot three = dots.get(i + this.size);
				Dot four = dots.get((i + 1) + this.size);

				// Create box
				Box b = new Box(one, two, three, four);

				// Add the Box to ArrayList
				state.add(b);

			}
		}
		return state;
	}

	/*
	 * Joins the dots at the indicies specified in the action[], and makes any score
	 * adjustments necessary. Returns the change in score/value of that move
	 */
	public int joinDots(int[] action, String player) {

		// find the dots at the indicies specified in action array
		Dot one = dots.get(action[0]);
		Dot two = dots.get(action[1]);

		// store the change in score
		int score = 0;

		// if already connected, return
		if (one.isConnect(two))
			return 0;

		// if the Dots can be connected, connect them
		if (one.canConnect(two)) {
			one.connect(two);
			two.connect(one);

			// for each box in the state that contains both these boxes (should be two max)
			for (Box b : state) {
				if (b.isDotInBox(one) && b.isDotInBox(two)) {

					// update the box, and check if it has been completed by this move
					boolean completed = b.update(player); // returns true if it completed, return false if not

					// if it was completed by this move, increase the score
					if (completed)
						score += b.value;
				}
			}
		} else {
			// if they can't be connected, throw exception (primarily for text interface)
			throw new IllegalArgumentException();
		}

		// update score for the correct player
		if (player.equals(Game.COMPUTER)) {
			compScore += score;
		}
		if (player.equals(Game.PLAYER)) {
			playerScore += score;
		}

		// return the value of this move
		return score;
	}

	/*
	 * Disconnects the dots at the indicies specified in the action[], This feature
	 * is used to undo changes, and is implemented so new states don't have to be
	 * created
	 */
	public void undoJoinDots(int[] action, String player) {

		// find the dots at the indicies specified in action array
		Dot one = dots.get(action[0]);
		Dot two = dots.get(action[1]);

		// if they aren't connected, return
		if (!one.isConnect(two))
			return;

		// disconnect them
		if (one.canConnect(two)) {
			one.disconnect(two);
			two.disconnect(one);
		}
		// for each box in the state that contains both these boxes (should be two max)
		for (Box b : state) {
			if (b.isDotInBox(one) && b.isDotInBox(two)) {

				// update the boxes
				b.update(player);
			}
		}
	}

	// Finds the Utility of the board - the difference in scores
	public int findUtility() {
		return compScore - playerScore;
	}

	// Is this a terminal state? Checks if all boxes are completed
	public boolean isTerminal() {
		for (Box i : state) {
			// if any box is incomplete, return false
			if (!i.isComplete())
				return false;
		}
		return true;
	}

	/*Returns an ArrayList of moves able to be taken in the current state.
	 * A "move" is the pair of dots to be connected.
	 * Moves are represented as int arrays, containing the indicies of the dots to be connected
	 */
	public ArrayList<int[]> possibleMoves() {

		//Stores the moves
		ArrayList<int[]> moves = new ArrayList<int[]>();

		// for each box in the current state state
		for (Box b : state) {
			
			// if the box is already completed, skip
			if (b.isComplete())
				continue;
			
			//arrays to hold the Dot (index) pairs
			int[] pair = new int[0];
			int[] revPair = new int[0];
			
			//For each pair of free Dots in that box
			for (ArrayList<Dot> a : b.getFreeMoves()) {
				
				//convert to an array of indicies (this is primarily for text interface)
				pair = new int[] { dots.indexOf(a.get(0)), dots.indexOf(a.get(1)) };
				revPair = new int[] { dots.indexOf(a.get(1)), dots.indexOf(a.get(0)) };
				
				//if it's not already in our list of moves, add it
				if (moves.contains(pair) || moves.contains(revPair))
					continue; 
				moves.add(pair);

			}
		}
		return moves;
	}

	// Prints the board in a player-friendly format
	public void printState() {
		String result = "";
		for (int y = 0; y < this.size - 1; y++) {
			// result+="\n";
			if (y == 0) {
				for (int x = 0; x < this.size - 1; x++) {
					if (x % this.size == 0) {
						result += state.get(x + y).one.toString();
						if (state.get(x + y).checkForTop())
							result += "----";
						else
							result += "    ";
						result += state.get(x + y).two.toString();
					} else {
						if (state.get(x + y * (this.size)).checkForTop())
							result += "----";
						else
							result += "    ";
						result += state.get(x + y * (this.size)).two.toString();
					}
				}
			}
			result += "\n";
			for (int x1 = 0; x1 < this.size - 1; x1++) {
				if (x1 % this.size == 0) {
					// System.out.println(state.get(x1+y).toString());
					if (state.get(x1 + y * (this.size - 1)).checkForLeft()
							&& state.get(x1 + y * (this.size - 1)).checkForRight())
						result += " |   " + state.get(x1 + y * (this.size - 1)).value + "   |";
					else if (state.get(x1 + y * (this.size - 1)).checkForLeft())
						result += " |   " + state.get(x1 + y * (this.size - 1)).value + "   ";
					else if (state.get(x1 + y * (this.size - 1)).checkForRight())
						result += "     " + state.get(x1 + y * (this.size - 1)).value + "   |";
					else
						result += "     " + state.get(x1 + y * (this.size - 1)).value + "   ";
				} else {
					if (state.get(x1 + y * (this.size - 1)).checkForRight())
						result += "    " + state.get(x1 + y * (this.size - 1)).value + "  |";
					else
						result += "    " + state.get(x1 + y * (this.size - 1)).value + "   ";
				}
			}
			result += "\n";
			for (int x2 = 0; x2 < this.size - 1; x2++) {
				if (x2 % this.size == 0) {

					result += state.get(x2 + y * (this.size - 1)).three.toString();
					if (state.get(x2 + y * (this.size - 1)).checkForBottom())
						result += "----";
					else
						result += "    ";
					result += state.get(x2 + y * (this.size - 1)).four.toString();
				} else {
					if (state.get(x2 + y * (this.size - 1)).checkForBottom())
						result += "----";
					else
						result += "    ";
					result += state.get(x2 + y * (this.size - 1)).four.toString();
				}

			}

		}
		System.out.println(result);
		System.out.println("Human: " + playerScore + "  Computer: " + compScore);
	}
}
