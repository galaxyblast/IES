package simulation;

import java.util.ArrayList;
import java.util.Random;

public class World
{
	private Population[] popList = new Population[4];
	private Random rng;

	public World(Random r)
	{
		this.rng = r;

		Tile t;
		int c;
		ArrayList<Tile> l = Simulation.instance.getLandTiles();
		for(int i = 0; i < 4; i++)
		{
			popList[i] = new Population();
			c = this.rng.nextInt(l.size());
			t = l.get(c);
			l.remove(c);
			//add the starting tile
		}
	}

	public void cycle()
	{

	}
}
