package simulation;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Population
{
	private CopyOnWriteArrayList<Member> MemList = new CopyOnWriteArrayList<Member>(); // An array of all members in the population.
	private CopyOnWriteArrayList<Tile> OwnedTiles = new CopyOnWriteArrayList<Tile>(); // An array of all Owned Tiles of the population.

	private float popRate;
	private int popNeeds;
	private Random seed;

	private static int  totalPopulation = 0;

	private int id;

	public float getPopRate() {
		return popRate;
	}

	public int getpopNeeds(){
		return popNeeds;
	}

	public void cycle(){
		synchronized(MemList)
		{
			for(Member i: MemList){
				i.cycle();
			}
		}
		synchronized(OwnedTiles)
		{
			for(Tile i: OwnedTiles){
				if(i.isEmpty())
					RemoveTile(i);
			}
		}
	}

	public void AddTile(Tile tile) {
		synchronized(OwnedTiles)
		{
			if(!OwnedTiles.contains(tile))
			{
				tile.setOwner(this);
				OwnedTiles.add(tile);
			}
		}
	}

	public void RemoveTile(Tile tile){
		synchronized(OwnedTiles)
		{
			if(OwnedTiles.contains(tile))
			{
				tile.setOwner(null);
				OwnedTiles.remove(tile);
			}
		}
	}

	public void AddMemList(Member member){
		synchronized(MemList)
		{
			MemList.add(member);
			totalPopulation++;
		}
	}

	public void DelMemList(Member member){
		synchronized(MemList)
		{
			MemList.remove(member);
			totalPopulation--;
		}
	}

	public Population(Random seed, int needs, float repRate){
		this.seed = seed;
		this.popRate = repRate;
		this.popNeeds = needs;
	}

	public void setID(int i)
	{
		this.id = i;
	}

	public int getID()
	{
		return this.id;
	}

	public String getName()
	{
		switch(this.id)
		{
			case 0:
				return "Red";
			case 1:
				return "White";
			case 2:
				return "Black";
			case 3:
			default:
				return "Purple";
		}
	}
	
	public CopyOnWriteArrayList<Tile> getOwnedTiles()
	{
		return this.OwnedTiles;
	}
}
