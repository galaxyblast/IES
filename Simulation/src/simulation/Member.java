package simulation;

import java.util.ArrayList;
import java.util.Random;

public class Member
{
	private Tile homeTile;
	private Population population;
	private Random random;
	private int needs;

	public Member(Tile home, Population pop, Random rand)
	{
		this.homeTile = home;
		this.random = rand;
		this.population = pop;
		this.needs = pop.getpopNeeds();
		
		home.getInhabitants().add(this);
	}

	public void cycle() {
		consumeResources();
		reproduce();
		System.out.println("Member Test");
	}

	private void reproduce() {
		if (random.nextInt() % 100 >= population.getPopRate()) {
			// TODO: decide new home
			Tile home = null;

			if (this.homeTile.isFull()) {
				ArrayList<Tile> neighbors = Simulation.instance.getSurroundingTiles(this.homeTile);
				for (int i = 0; i < neighbors.size(); i++) {
					int rand = random.nextInt(neighbors.size());
					Tile tile = neighbors.get(rand);
					if (!(tile.isFull() || (tile.getOwner() != this.population))) {
						home = tile;
						break;
					}
				}
				if (home == null)
					return;
			} else {
				home = this.homeTile;
			}

			Member newMember = new Member(home, this.population, this.random);
			this.population.AddMemList(newMember);
			return;
		}
	}

	public void consumeResources() {
		int renewable = this.homeTile.getRenewableResources();
		if (renewable >= needs)
			this.homeTile.setRenewableResources(renewable - needs);
		else {
			int nonrenewable = this.homeTile.getNonRenewableResources();
			if (nonrenewable+renewable >= this.needs)
			{
				int difference = this.needs - renewable;
				this.homeTile.setRenewableResources(0);
				this.homeTile.setNonRenewableResources(nonrenewable - difference);
			}
			else
				die();
		}
	}

	private void die() {
		this.homeTile.getInhabitants().remove(this);
		this.population.DelMemList(this);
	}
}
