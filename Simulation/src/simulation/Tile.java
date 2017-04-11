package simulation;

import java.util.Random;

public class Tile{
	private int x;
	private int y;
	private int RenewableResources;
	private int NonRenewableResources;
	private int RegenRate;
	private int TerrainModifier; //0 is flat land, 1 has water, 2 hills/mountains
	private int MaxInhabitants; //based on terrainmod
	private Member Inhabitants[];
	private Population Owner;
	
	public Tile(int x, int y, int RenewableResources, int NonRenewableResources, int RegenRate, int TerrainModifier, Member Inhabitants[], Population Owner)
	{
		this.x = x;
		this.y = y;
		this.RenewableResources = RenewableResources;
		this.NonRenewableResources = NonRenewableResources;
		this.RegenRate = RegenRate;
		this.TerrainModifier = TerrainModifier;
		this.MaxInhabitants = generateMaxInhabitants(TerrainModifier);
		this.Inhabitants = Inhabitants;
		this.Owner = Owner;
	}
	
	public int generateMaxInhabitants(int tm)
	{
		int maxInhabReturn;
		Random rng = new Random();
		
		if(tm == 0) //if the terrain is flat land, 
			maxInhabReturn = rng.nextInt(11) + 20;
		else if(tm == 1)
			maxInhabReturn = rng.nextInt(6);
		else if(tm == 2)
			maxInhabReturn = rng.nextInt(11) + 10;
		else
		{
			System.out.println("Invalid tile type");
			maxInhabReturn = 0;
		}
			
		return maxInhabReturn;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRenewableResources() {
		return RenewableResources;
	}

	public void setRenewableResources(int renewableResources) {
		RenewableResources = renewableResources;
	}

	public int getNonRenewableResources() {
		return NonRenewableResources;
	}

	public void setNonRenewableResources(int nonRenewableResources) {
		NonRenewableResources = nonRenewableResources;
	}

	public int getRegenRate() {
		return RegenRate;
	}

	public void setRegenRate(int regenRate) {
		RegenRate = regenRate;
	}

	public int getTerrainModifier() {
		return TerrainModifier;
	}

	public void setTerrainModifier(int terrainModifier) {
		TerrainModifier = terrainModifier;
	}

	public int getMaxInhabitants() {
		return MaxInhabitants;
	}

	public void setMaxInhabitants(int maxInhabitants) {
		MaxInhabitants = maxInhabitants;
	}

	public Member[] getInhabitants() {
		return Inhabitants;
	}

	public void setInhabitants(Member[] inhabitants) {
		Inhabitants = inhabitants;
	}

	public Population getOwner() {
		return Owner;
	}

	public void setOwner(Population owner) {
		Owner = owner;
	}

	
}