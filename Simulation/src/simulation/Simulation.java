package simulation;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simulation.gui.GuiButton;
import simulation.gui.GuiTile;

public class Simulation extends Application
{
	private static final int MAPSIZEX = 25;
	private static final int MAPSIZEY = 50;

	public final AnchorPane canvas = new AnchorPane();
	private Scene scene = new Scene(this.canvas, 640, 480);
	private GuiButton runCycleButton = new GuiButton("Run simulation", 125, 25, (int)(this.canvas.getWidth() / 2 - 62.5D), (int)(this.canvas.getHeight() - 30.0D));
	private World world;
	private Tile[][] map = new Tile[MAPSIZEX][MAPSIZEY];
	private Population[] popList = new Population[5];

	//Use this to call any needed function from here
	public static final Simulation instance = new Simulation();
	
	private Random rng = new Random();

	//Main function
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		this.canvas.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0.0D), new Insets(0.0D))));

		primaryStage.setTitle("Population Simulation");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();

		//Initialize screen elements

		//Add screen elements
		this.addButton(this.runCycleButton);
		
		//setup world
		this.setupWorld();
	}

	//Anything that needs to be instantiated with the world goes here
	public void setupWorld()
	{
		//this.rng.setSeed(1L);
		
		GuiTile tmp;
		
		//generate map
		for(int x = 0; x < MAPSIZEX; x++)
		{
			for(int y = 0; y < MAPSIZEY; y++)
			{
				this.map[x][y] = new Tile(x, y, 0, 0, 0, 1, null, null);
				tmp = new GuiTile(this.map[x][y]);
				this.addGuiTile(tmp);
			}
		}

		int landPasses = this.rng.nextInt(11) + 20;
		int mountainPasses = this.rng.nextInt(5) + 3;

		for(int i = 0; i <= landPasses; i++)
		{
			this.genLand(this.rng.nextInt(MAPSIZEX - 4) + 2, this.rng.nextInt(MAPSIZEY - 6) + 3, 0);
		}

		this.smoothLand();
		
		for(int i = 0; i <= mountainPasses; i++)
		{
			this.genMountain(this.rng.nextInt(MAPSIZEX - 4) + 2, this.rng.nextInt(MAPSIZEY - 6) + 3, 0);
		}
		
		//update map
		this.updateWorld();
	}

	//The main function for simulation
	public void cycle(int loops)
	{

	}
	
	private void updateWorld()
	{
		GuiTile tmp;
		
		this.canvas.getChildren().clear();
		this.addButton(this.runCycleButton);
		for(int x = 0; x < MAPSIZEX; x++)
		{
			for(int y = 0; y < MAPSIZEY; y++)
			{
				tmp = new GuiTile(this.map[x][y]);
				this.addGuiTile(tmp);
			}
		}
	}

	public World getWorld()
	{
		return this.world;
	}

	public Tile getTile(int x, int z)
	{
		if(x >= 0 && x < MAPSIZEX && z >= 0 && z < MAPSIZEY)
			return this.map[x][z];
		else
			return null;
	}

	public Tile[][] getMap()
	{
		return this.map;
	}

	public Population[] getPopulationList()
	{
		return this.popList;
	}

	public void addButton(GuiButton btn)
	{
		this.canvas.getChildren().add(btn.getButton());
	}
	
	public void addGuiTile(GuiTile t)
	{
		this.canvas.getChildren().add(t.getPoly());
	}
	
	private void genLand(int x, int y, int steps)
	{
		this.map[x][y].setTerrainModifier(0);
		if(this.rng.nextInt() % 32 != 0 && steps < 100)
		{
			int xOff = this.rng.nextInt(3) - 1;
			int yOff = this.rng.nextInt(3) - 1;
			
			int i = 0;
			boolean flag = false;
			while(!flag && i < 7)
			{
				if(x + xOff < MAPSIZEX - 2 && x + xOff >= 2 && y + yOff < MAPSIZEY - 3 && y + yOff >= 3)
				{
					if(this.map[x + xOff][y + yOff].getTerrainModifier() == 0)
					{
						xOff = this.rng.nextInt(3) - 1;
						yOff = this.rng.nextInt(3) - 1;
						i++;
					}
					else
					{
						this.genLand(x + xOff, y + yOff, steps + 1);
						flag = true;
					}
				}
				else
				{
					xOff = this.rng.nextInt(3) - 1;
					yOff = this.rng.nextInt(3) - 1;
					i++;
				}
			}
		}
	}
	
	private void genMountain(int x, int y, int steps)
	{
		if(this.map[x][y].getTerrainModifier() == 0)
			this.map[x][y].setTerrainModifier(2);
		if(this.rng.nextInt() % 16 != 0 && steps < 24)
		{
			int xOff = this.rng.nextInt(3) - 1;
			int yOff = this.rng.nextInt(3) - 1;
			
			int i = 0;
			boolean flag = false;
			while(!flag && i < 7)
			{
				if(x + xOff < MAPSIZEX - 2 && x + xOff >= 2 && y + yOff < MAPSIZEY - 3 && y + yOff >= 3)
				{
					if(this.map[x + xOff][y + yOff].getTerrainModifier() == 1)
					{
						xOff = this.rng.nextInt(3) - 1;
						yOff = this.rng.nextInt(3) - 1;
						i++;
					}
					else
					{
						this.genMountain(x + xOff, y + yOff, steps + 1);
						flag = true;
					}
				}
				else
				{
					xOff = this.rng.nextInt(3) - 1;
					yOff = this.rng.nextInt(3) - 1;
					i++;
				}
			}
		}
	}
	
	public void smoothLand()
	{
		int l = 0, newX, newY;
		for(int x = 0; x < MAPSIZEX; x++)
		{
			for(int y = 0; y < MAPSIZEY; y++)
			{
				for(int xOff = -2; xOff <= 2; xOff++)
				{
					for(int yOff = -1; yOff <= 1; yOff++)
					{
						newX = x + xOff;
						newY = y + yOff;
						if(newX >= 0 && newX < MAPSIZEX && newX != x)
						{
							if(newY >= 0 && newY < MAPSIZEY && newY != y)
							{
								if(this.map[newX][newY].getTerrainModifier() == 0)
									l++;
							}
						}
					}
				}
				if(l > 4)
				{
					this.map[x][y].setTerrainModifier(0);
				}
				l = 0;
			}
		}
	}
}
