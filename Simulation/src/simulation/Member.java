package simulation;

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
	}
	
	public void Cycle() {
		ConsumeResources();
		Reproduce();
	}
	
	private void Reproduce() {
		if (random.nextInt() % 100 >= population.getPopRate()) {
			// TODO: decide new home
			Tile home;
			if (this.homeTile.getMaxInhabitants() >= this.homeTile.getInhabitants().length) {
				home = this.homeTile;	// TODO: Move Function
				return;
			} else {
				home = this.homeTile;
			}

			Member newMember = new Member(home, this.population, this.random);
			this.population.AddMemList(newMember);
			return;
		}
	}
	
	public void ConsumeResources() {
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
				Die();
		}
	}
	
	private void Die() {
		this.population.DelMemList(this);
	}
}
