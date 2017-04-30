package simulation;

import java.util.ArrayList;
import java.util.Random;

public class Tile{
	private int x;	//holds x position of tile
	private int y;	//holds y position of tile
	private int RenewableResources;	//holds the quantity of the tile's renewable resources in an integer value
	private int NonRenewableResources;		//holds the quantity of the tile's nonrenewable resources in an integer value
	private int RegenRate;	//the percent value of possibility for reproduction of renewable resources each turn
	private int TerrainModifier; //0 is flat land, 1 has water, 2 hills/mountains
	private int MaxInhabitants;	//integer value for the most amount of members a tile can hold
	private ArrayList<Member> Inhabitants;	//array of all members in the tile
	private Population Owner;	//holds the population that owns this tile
	private Random rand; //holds the random object used throughout the program
	
	public Tile(int x, int y, int RenewableResources, int NonRenewableResources, int RegenRate, int TerrainModifier, Random rand) //constructor for a tile object
	{
		//Sets all values up with values entered
		this.x = x;
		this.y = y;
		this.RenewableResources = RenewableResources;
		this.NonRenewableResources = NonRenewableResources;
		this.RegenRate = RegenRate;
		this.TerrainModifier = TerrainModifier;
		this.rand = rand;
		
		this.MaxInhabitants = generateMaxInhabitants(TerrainModifier); //calls the generateMaxInhabitants method and stores value in MaxInhabitants
		this.Inhabitants = new ArrayList<Member>() ; //initializes the Member arraylist for the tile
	}
	
	public int generateMaxInhabitants(int tm) // utilizes the terrainmodifier entered to return a value for the max number of inhabitants sustainable on the tile
	{
		int maxInhabReturn;
		Random rng = this.rand; //sets up rng as our random number generator 
		
		if(tm == 0) //if the terrain is flat land, 
			maxInhabReturn = rng.nextInt(11) + 20; //maxInhabs is set to random number between 20 and 30
		else if(tm == 1) //if the terrain is water
			maxInhabReturn = rng.nextInt(6); //maxInhabs is set to random number between 0 and 6
		else if(tm == 2) //if the terrain is mountainous
			maxInhabReturn = rng.nextInt(11) + 10; //maxInhabs is set to a random number betwen 10 and 20
		else //for error handling
		{
			maxInhabReturn = 0;
		}
			
		return maxInhabReturn; //returns value generated
	}
	
	public boolean isFull(){ //returns if the tile has met maxInhab limit
		if(this.Inhabitants.size() >= this.MaxInhabitants) //if the size of the inhabitants is greater or equal to maxInhab value
			return true; //say yes
		else
			return false; //say no
	}
	
	public boolean isEmpty(){ //returns if the tile is empty
		if(this.Inhabitants.size() == 0) //compares the size of the inhabitants arraylist with 0
			return true; //says yes
		else
			return false; //says no
	}
	
	public void removeMember(Member member){ //takes a member out of a population
		if(this.Inhabitants.contains(member)) //only if the member is actually in the tile
			this.Inhabitants.remove(member); //remove it
	}
	
	public String getTileTerrainModString(){ //returns a string that coincides with the value of the terrain modifier
		switch(this.TerrainModifier) 
		{
			case(0):
				return "Land";
			case(1):
				return "Water";
			case(2):
				return "Mountain";
		}
		return "Unidentified Tile Type"; //if something terribly wrong happens
	}
	
	public void cycle(){ //updates the amount of renewable resources each cycle
		if(this.RenewableResources + this.RegenRate <= 1000) // if adding to the current renewableresources value won't exceed 1000 
			this.RenewableResources += this.RegenRate; //dyew it
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