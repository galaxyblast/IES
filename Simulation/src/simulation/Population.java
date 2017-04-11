package simulation;

import java.util.ArrayList;

public class Population
{
	private ArrayList<Member> MemList; // An array of all members in the population.
	private ArrayList<Tile> OwnedTiles; // An array of all Owned Tiles of the population.
	
	private float popRate;	
	private static int  currentpersonindex = 0;

	public float getPopRate() {
		return popRate;
	}
	
	public Tile UpdateTile(Tile tile){
		
		return tile; 
	}
	public Tile AddTile(){
		return null; 
	}
	public void RemoveTile(Tile tile){
		
	 
	}

	
	
}
