package simulation;

import java.util.ArrayList;
import java.util.Random;

public class Member
{
	private Tile homeTile;			// The tile that this member exists on
	private Population population;	// The population this member belongs to
	private Random random;			// Responsible for all random values of this member
	private int needs;				// The resources this member needs to consume each cycle
	private int repoRate;			// The rate at which this member reproduces
	

	/*
	 *	This is the constructor for the Member object
	 *		home:	The tile this member will belong too
	 *		pop:	The population this member will belong too
	 *		rand:	The Random object that will be responsible for all random values in this member
	 */
	public Member(Tile home, Population pop, Random rand)
	{
		this.homeTile = home;
		this.random = rand;
		this.population = pop;
		this.needs = pop.getpopNeeds();
		this.repoRate = pop.getPopRate();
		
		home.getInhabitants().add(this); // Ensure the tile knows that this member exists
	}

	/*
	 *	This method will work like an event in a way.
	 *	Every time the simulation cycles, this method is called.
	 *	It's responsible for the member performing its necessary tasks to live
	 */
	public void cycle() {
		consumeResources();
		reproduce();
	}

	/*
	 *	Responsible for the creation of new members 
	 */
	private void reproduce() {
		// decide if this member should reproduce based on the reproduction rate
		if (this.random.nextInt() % 100 >= 100 - this.repoRate) {
			// If reproduction should happed:
			// Get a new tile for the home of the new member
			Tile home = null;

			if (this.homeTile.isFull()) {
				// If the current tile is full get it's neighbors
				ArrayList<Tile> neighbors = Simulation.instance.getSurroundingTiles(this.homeTile);
				for (int i = 0; i < neighbors.size(); i++) {
					// Get a random neighbor
					int rand = this.random.nextInt(neighbors.size());
					Tile tile = neighbors.get(rand);
					
					// If the neighbor is suitable, set the new home to it
					// NOTE: suitable means it is not full and not owned by a rival population
					if (!(tile.isFull() || (tile.getOwner() != this.population && tile.getOwner() != null))) {
						home = tile;
						break;
					}
				}
				// If no neighbors are suitable than just stop. No member can be born
				if (home == null)
					return;
			} else {
				// Otherwise this current tile will be suitable
				home = this.homeTile;
			}

			// Create the new member, give it a home, and make sure it's been added to the total list
			Member newMember = new Member(home, this.population, this.random);
			if(home != this.homeTile)
				this.population.AddTile(home);
			this.population.AddMemList(newMember);
			return;
		}
	}

	/*
	 *	This method will handle this member's resource consumption
	 */
	public void consumeResources() {
		int renewable = this.homeTile.getRenewableResources(); // Get the available renewable resources for this member
		
		// If there are enough renewable resources, consume them
		// Otherwise decide how to consume remaining resources
		if (renewable >= this.needs)
			this.homeTile.setRenewableResources(renewable - this.needs);
		else {
			int nonrenewable = this.homeTile.getNonRenewableResources();
			// If there are enough remaining resources consume them
			// Otherwise die
			if (nonrenewable+renewable >= this.needs)
			{
				// Ensure we finish off the renewable  resources before we consume the nonrenewable
				int difference = this.needs - renewable;
				this.homeTile.setRenewableResources(0);
				this.homeTile.setNonRenewableResources(nonrenewable - difference);
			}
			else
				die();
		}
	}

	/*
	 * 	Remove the member of existence
	 *	Bye Bye
	 */
	private void die() {
		this.homeTile.getInhabitants().remove(this);
		this.population.DelMemList(this);
	}
}
