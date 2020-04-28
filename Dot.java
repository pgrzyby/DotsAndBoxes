package assignment2;

import java.util.ArrayList;

/* Dot class represents the dots in our game.
 * Each dot has an X and Y coordinate to represent it's location on the board.
 * Each dot stores its location in the State's ArrayList as well (for text-interface purposes)
 * Class contains functions to aid with connecting the dots
 */

public class Dot {
	
	//The X and Y coordinate to represent location
	int x;
	int y;
	
	int index;  //index in the State arraylist the Dot is a part of
	
	ArrayList<Dot> connections = new ArrayList<Dot>(); //Lists of the Dots it is connected to
	
	//Constructor
	public Dot(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.index=z;
	}
	
	//Can it be connected with this other dot?
	// (This could be more thorough but is sufficient for the purposes of this project)
	public boolean canConnect(Dot other) {
		return (this.x == other.x || this.y == other.y);
	}
	
	//Is it connected with this other dot?
	public boolean isConnect(Dot other) {
		return connections.contains(other);
	}
	
	//Connects it to another Dot, stores that Dot
	public void connect(Dot other) {
		
		//if it's already connected or can't connect to said dot, 
		if(isConnect(other)) return;
		if(canConnect(other) == false) return;
			
		//add it to the list of connections
		connections.add(other);
		other.connections.add(this);

	}
	
	//Disconnects it from another Dot -- used for unraveling
	public void disconnect(Dot other) {
		
		//if it's not connected, return
		if(!isConnect(other)) return;
		
		//remove from list of connections
		connections.remove(other);
		other.connections.remove(this);
		
	}
	
	//String representation - for text interface
	public String toString() {
		
		if(index<10) return "["+ index + " ]";
		else return "["+ index + "]";
	}
	

}
