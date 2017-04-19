package simulation;

import java.util.ArrayList;

public class Population
{
	private ArrayList<Member> MemList; // An array of all members in the population.
	private ArrayList<Tile> OwnedTiles; // An array of all Owned Tiles of the population.
	
	private float popRate;
	private int popNeeds;
	
	private static int  totalPopulation = 0;

	public float getPopRate() {
		return popRate;
	}
	
	public int getpopNeeds(){
		return popNeeds;
	}
	
	public Tile UpdateTile(Tile tile){
		// TODO
		return tile; 
	}
	
	public void AddTile(Tile tile) {
		tile.setOwner(this);
		OwnedTiles.add(tile);
	}
	
	public void RemoveTile(Tile tile){
		tile.setOwner(null);
		OwnedTiles.remove(tile);
	}
	
	public void AddMemList(Member member){
		MemList.add(totalPopulation, member);
		totalPopulation++;
	}
	
	public void DelMemList(Member member){
		MemList.remove(member);
		totalPopulation--;
		MemList.trimToSize();
		
	}

	
	
}
