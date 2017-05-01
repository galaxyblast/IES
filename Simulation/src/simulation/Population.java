package simulation;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Population
{
	private CopyOnWriteArrayList<Member> MemList = new CopyOnWriteArrayList<Member>(); // An array of all members in the population.
	private CopyOnWriteArrayList<Tile> OwnedTiles = new CopyOnWriteArrayList<Tile>(); // An array of all Owned Tiles of the population.

	private int popRate; // the rate that any population will grow.
	private int popNeeds; // the rate that all members consume resources. 
	private int id; // a number that differentiates the populations when the simulation is run.

	public int getPopRate() { // a getter utilized by the members when they try to reproduce.
		return popRate;
	}

	public int getpopNeeds(){ // a setter for population Id.
		return popNeeds;
	}

	public void setID(int i) // a setter for population Id.
	{
		this.id = i;
	}

	public int getID() // a getter for population Id.
	{
		return this.id;
	}

	public String getName() // a getter for the names of each population.
	{
		switch(this.id)
		{
			case 0:
				return "Red";
			case 1:
				return "Greenland";
			case 2:
				return "Black";
			case 3:
				return "Purple";
			case 4:
				return "Yellow";
			case 5:
				return "Cyan";
			case 6:
				return "Orange";
			case 7:
				return "Brown";
			case 8:
				return "Pink";
			case 9:
			default:
				return "Iceland";
		}
	}
	
	public void cycle(){ // Method that calls all members, within current population, to cycle and remove tiles if needed.
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

	public void AddTile(Tile tile) {// a method that members calls when the population needs to expand and sets the owner of that tile.
		synchronized(OwnedTiles)
		{
			if(!OwnedTiles.contains(tile))
			{
				tile.setOwner(this);
				OwnedTiles.add(tile);
			}
		}
	}

	public void RemoveTile(Tile tile){ //method that removes a tile from a population
		synchronized(OwnedTiles)
		{
			if(OwnedTiles.contains(tile))
			{
				tile.setOwner(null);
				OwnedTiles.remove(tile);
			}
		}
	}

	public void AddMemList(Member member){ // Method that Adds a member to the population
		synchronized(MemList)
		{
			MemList.add(member);
		}
	}

	public void DelMemList(Member member){ // Method that Removes a member from the population.
		synchronized(MemList)
		{
			MemList.remove(member);
		}
	}

	public Population(Random seed, int needs, int repRate){ // constructor for a population
		this.popRate = repRate;
		this.popNeeds = needs;
	}
	
	public CopyOnWriteArrayList<Tile> getOwnedTiles()
	{
		return this.OwnedTiles;
	}
}
