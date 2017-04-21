package simulation;

import java.util.ArrayList;
import java.util.Random;

public class World
{
	private Population[] popList = new Population[Simulation.instance.getNumPops()];
	private Random rng;

	public World(Random r)
	{
		this.rng = r;

		Tile t;
		int c;
		ArrayList<Tile> l = Simulation.instance.getLandTiles();
		for(int i = 0; i < Simulation.instance.getNumPops(); i++)
		{
			popList[i] = new Population(r, r.nextInt(3) + 1, (float)r.nextInt(Simulation.instance.getBirthRate()) + 1);
			c = this.rng.nextInt(l.size());
			t = l.get(c);
			l.remove(c);
			popList[i].AddTile(t);

			int s = r.nextInt(5) + 1;
			for(int j = 0; j < s; j++)
			{
				popList[i].AddMemList(new Member(t, popList[i], r));
			}
			popList[i].setID(i);
		}
	}

	public void cycle()
	{
		for(int i = 0; i < Simulation.instance.getNumPops(); i++)
		{
			this.popList[i].cycle();
		}

		Tile[][] map = Simulation.instance.getMap();
		for(int x = 0; x < Simulation.MAPSIZEX; x++)
		{
			for(int y = 0; y < Simulation.MAPSIZEY; y++)
			{
				map[x][y].cycle();
			}
		}
	}
	
	public Population[] getPopList()
	{
		return this.popList;
	}
}
