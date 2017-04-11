package simulation;

import java.util.ArrayList;

public class Population
{
	private ArrayList<Member> MemList; // An array of all members in the population.
	private ArrayList<Tile> OwnedTiles; // An array of all Owned Tiles of the population.
	
	private float popRate;
	private int popNeeds;
	
	private static int  currentpersonindex = 0;

	public float getPopRate() {
		return popRate;
	}
	
	public int getpopNeeds(){
		return popNeeds;
	}
	
	public Tile UpdateTile(Tile tile){
		
		return tile; 
	}
	
	public Tile AddTile(){
		return null; 
	}
	
	public void RemoveTile(Tile tile){
		
	}
	
	public void AddMemList(Member member){
		MemList.add(currentpersonindex, member);
		currentpersonindex++;
	}
	
	public void DelMemList(Member member){
		
	}

	
	
}
