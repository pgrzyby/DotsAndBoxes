package assignment2;

import java.util.ArrayList;

/* This class represents a Box object, formed by connecting four Dot objects
 * Contains four Dot fields, a value field, and several functions that keep track of which lines
 * are present, and the completeness of the box.
 * 
 */
public class Box {
	
	//Dots that are a part of this Box
	Dot one;
	Dot two;
	Dot three;
	Dot four;
	
	//The possible lines and whether or not they exist
	boolean top;
	boolean bottom;
	boolean left;
	boolean right;
	
	int value;			//random value assigned
	boolean complete;	//is the box complete?
	String owner; 		//if so, who completed it?
	
	//Constructor
	public Box(Dot one, Dot two, Dot three, Dot four) {
	
		this.one = one;
		this.two = two;
		this.three = three; 
		this.four = four; 
		
		this.top= false;
		this.bottom = false;
		this.left = false;
		this.right = false;
		
		this.owner= "";
		this.value = (int)( Math.random() *5) + 1;
		this.complete = false;
	}
		
	//check if these lines exist:
	public boolean checkForBottom() {
		return bottom;
	}
	
	public boolean checkForTop() {
		return top;
	}
	
	public boolean checkForLeft() {
		return left;
	}
	
	public boolean checkForRight() {
		return right;
	}
	
	//is this box complete?
	public boolean isComplete() {
		return complete;
		
	}

	//make updates following a connection/disconnection - update sides and completion
	public boolean update(String player) {
		
		if(one.isConnect(two)) top=true; else top = false;
		if(one.isConnect(three)) left=true; else left = false;
		if(two.isConnect(four)) right = true; else right = false;
		if(three.isConnect(four)) bottom=true; else bottom = false;
		
		if(top&&bottom&&left&&right) {
			complete=true;
			owner=player;
		}
		else {
			complete=false;
			owner="";
		}
		
		return complete;
	
	}
	
	//is the Dot passed a part of this Box?
	public boolean isDotInBox(Dot d) {
		if(d.equals(one) || d.equals(two) || d.equals(three) ||d.equals(four)) return true;
		else return false;
	}
	
	//get the free lines in this box - returns an ArrayList of ArrayLists of Dots 
	//Each ArrayList of Dots represents a move creating a line between said dots
	public ArrayList<ArrayList<Dot>> getFreeMoves(){
		ArrayList<ArrayList<Dot>> free = new ArrayList<ArrayList<Dot>>();
		if(!top) { 
			ArrayList<Dot> pair = new ArrayList<Dot>(); 
			pair.add(one); pair.add(two); 
			free.add(pair); 
			}
		if(!bottom) { 
			ArrayList<Dot> pair = new ArrayList<Dot>(); 
			pair.add(three); pair.add(four); 
			free.add(pair); 
			}
		if(!left) { 
			ArrayList<Dot> pair = new ArrayList<Dot>(); 
			pair.add(one); pair.add(three); 
			free.add(pair);
			}
		if(!right) {
			ArrayList<Dot> pair = new ArrayList<Dot>(); 
			pair.add(two); pair.add(four); 
			free.add(pair); 
			}

		return free;
	}
	
	//Creates String version of the Box
	public String toString() {
		
		String result="";
		
		result += one.toString();
		if(top) result+= " ---- "; else result+="    ";
		result += two.toString();
		if(left && right) result+= "\n|\t\t|\n"+value+"\n|\t\t|"; 
		else if(left) result+= "\n  |\n  |    " + value + "\n";
		else if(right) result+= "\n\t\t|\n\t" + value + "  \t|\n";
		else result+="\n\n      " + value + "\n\n";
		result += three.toString();
		if(bottom) result+= " ---- "; else result+="    ";
		result += four.toString();
		
		return result;
	}
	


}
