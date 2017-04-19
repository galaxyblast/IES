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
		System.out.println(l.size());
		for(int i = 0; i < 4; i++)
		{
			popList[i] = new Population(r, 1, r.nextInt(90) + 1);
			c = this.rng.nextInt(l.size());
			t = l.get(c);
			l.remove(c);
			popList[i].AddTile(t);

			int s = r.nextInt(5) + 1;
			for(int j = 0; j < s; j++)
			{
				popList[i].AddMemList(new Member(t, popList[i], r));
			}
		}
	}

	public void cycle()
	{
		System.out.println("World Test");
		for(int i = 0; i < 4; i++)
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
}
