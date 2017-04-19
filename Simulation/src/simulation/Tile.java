package simulation;

import java.util.ArrayList;
import java.util.Random;

public class Tile{
	private int x;
	private int y;
	private int RenewableResources;
	private int NonRenewableResources;
	private int RegenRate;
	private int TerrainModifier; //0 is flat land, 1 has water, 2 hills/mountains
	private int MaxInhabitants;
	private ArrayList<Member> Inhabitants;
	private Population Owner;
	private Random rand;
	
	public Tile(int x, int y, int RenewableResources, int NonRenewableResources, int RegenRate, int TerrainModifier, Random rand)
	{
		this.x = x;
		this.y = y;
		this.RenewableResources = RenewableResources;
		this.NonRenewableResources = NonRenewableResources;
		this.RegenRate = RegenRate;
		this.TerrainModifier = TerrainModifier;
		this.rand = rand;
		this.MaxInhabitants = generateMaxInhabitants(TerrainModifier);
		this.Inhabitants = new ArrayList<Member>() ;
	}
	
	public int generateMaxInhabitants(int tm)
	{
		int maxInhabReturn;
		Random rng = this.rand;
		
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
	
	public boolean isFull(){
		if(this.Inhabitants.size() == this.MaxInhabitants)
			return true;
		else
			return false;
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


	public Population getOwner() {
		return Owner;
	}

	public void setOwner(Population owner) {
		Owner = owner;
	}

	public int getMaxInhabitants() {
		return MaxInhabitants;
	}

	public void setMaxInhabitants(int maxInhabitants) {
		MaxInhabitants = maxInhabitants;
	}

	public ArrayList<Member> getInhabitants() {
		return Inhabitants;
	}

	public void setInhabitants(ArrayList<Member> inhabitants) {
		Inhabitants = inhabitants;
	}

	
}