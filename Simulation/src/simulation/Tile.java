package simulation;

public class Tile{
	private int x;
	private int y;
	private int RenewableResources;
	private int NonRenewableResources;
	private int RegenRate;
	private int TerrainModifier;
	private int MaxInhabitants; //based on terrainmod
	private Member Inhabitants[];
	private Population Owner;
	
	public Tile(int x, int y, int RenewableResources, int NonRenewableResources, int RegenRate, int TerrainModifier){	//x, y, ren, non, regenrate, TerrainMod
		this.x = x;
		this.y = y;
		this.RenewableResources = RenewableResources;
		this.NonRenewableResources = NonRenewableResources;
		this.RegenRate = RegenRate;
		this.TerrainModifier = TerrainModifier;
	}
	
	
}