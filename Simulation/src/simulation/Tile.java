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
	
	
}