package simulation;

import java.util.ArrayList;
import java.util.Random;

public class Population
{
	private ArrayList<Member> MemList = new ArrayList<Member>(); // An array of all members in the population.
	private ArrayList<Tile> OwnedTiles = new ArrayList<Tile>(); // An array of all Owned Tiles of the population.
	
	private float popRate;
	private int popNeeds;
	private Random seed;
	
	private static int  totalPopulation = 0;

	public float getPopRate() {
		return popRate;
	}
	
	public int getpopNeeds(){
		return popNeeds;
	}
	
	public void cycle(){
		for(Member i: MemList){
			i.cycle();
		}
		for(Tile i: OwnedTiles){
			if(i.isEmpty())
				RemoveTile(i);
				
		}
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

	public Population(Random seed, int needs, float repRate){
		this.seed = seed;
		this.popRate = repRate;
		this.popNeeds = needs;
	}
	
}
