package simulation;

import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import simulation.gui.Action;
import simulation.gui.GuiButton;
import simulation.gui.GuiTile;
import simulation.gui.GuiTileOwned;
import simulation.gui.PopupMenu;

public class Simulation extends Application
{
	public static final int MAPSIZEX = 25;
	public static final int MAPSIZEY = 50;

	public final AnchorPane canvas = new AnchorPane();
	private Scene scene = new Scene(this.canvas, 640, 480);
	private GuiButton runCycleButton = new GuiButton("Run simulation", 125, 25, (int)(this.canvas.getWidth() / 2 - 62.5D), (int)(this.canvas.getHeight() - 30.0D));
	private GuiButton pauseButton = new GuiButton("Pause", 125, 25, (int)(this.canvas.getWidth()/2 - 200.0D), (int)(this.canvas.getHeight() - 30.0D));
	private World world;
	private Tile[][] map = new Tile[MAPSIZEX][MAPSIZEY];
	private ArrayList<Population> popList = new ArrayList<Population>();
	private ArrayList<Tile> landTiles = new ArrayList<Tile>();
	private ArrayList<GuiTileOwned> ownedTiles = new ArrayList<GuiTileOwned>();

	//Use this to call any needed function from here
	public static Simulation instance;

	private Random rng = new Random();

	private TextField numCycle = new TextField();
	
	private int landPass = 20;
	private int mountPass = 3;
	private int pops;
	private int birthRate = 90;
	private boolean isPaused = false;

	//Main function
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		instance = this;
		this.canvas.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0.0D), new Insets(0.0D))));

		primaryStage.setTitle("Population Simulation");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		primaryStage.setScene(new PopupMenu(primaryStage).getScene());
	}
	
	public void begin(Stage stage, long s, int l, int m, int b)
	{
		if(s != -1L)
			this.rng.setSeed(s);
		
		if(l > 0)
			this.landPass = l;
		
		if(m >= 0)
			this.mountPass = m;
		
		this.pops = this.popList.size();
		
		if(b >= 0)
			this.birthRate = b;
		
		stage.setScene(this.scene);
		
		//Initialize screen elements
		this.runCycleButton.setAction(new Action(){
			@Override
			public void start()
			{
				Simulation.instance.isPaused = false;
				int c = Simulation.instance.getNumCycles();
				System.out.println("Running " + c + " cycles.");

				Simulation.instance.cycle(c);
			}
		});
		this.pauseButton.setAction(new Action(){
			@Override
			public void start()
			{
				Simulation.instance.isPaused = true;
			}
		});
		this.numCycle.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
		this.numCycle.setLayoutX(this.canvas.getWidth() / 2.0D + 64.0D);
		this.numCycle.setLayoutY(this.canvas.getHeight() - 30.0D);

		//Add screen elements
		this.addButton(this.runCycleButton);
		this.addButton(this.pauseButton);
		this.canvas.getChildren().add(this.numCycle);

		//setup world
		this.setupWorld();
	}

	//Anything that needs to be instantiated with the world goes here
	public void setupWorld()
	{
		//this.rng.setSeed(10L);

		GuiTile tmp;

		//generate map
		for(int x = 0; x < MAPSIZEX; x++)
		{
			for(int y = 0; y < MAPSIZEY; y++)
			{
				this.map[x][y] = new Tile(x, y, 0, 0, 0, 1, this.rng);
				tmp = new GuiTile(this.map[x][y]);
				this.addGuiTile(tmp);
			}
		}

		int landPasses = this.rng.nextInt(11) + this.landPass;
		int mountainPasses = this.rng.nextInt(5) + this.mountPass;

		for(int i = 0; i <= landPasses; i++)
		{
			this.genLand(this.rng.nextInt(MAPSIZEX - 4) + 2, this.rng.nextInt(MAPSIZEY - 6) + 3, 0);
		}

		this.smoothLand();

		for(int i = 0; i <= mountainPasses; i++)
		{
			this.genMountain(this.rng.nextInt(MAPSIZEX - 4) + 2, this.rng.nextInt(MAPSIZEY - 6) + 3, 0);
		}

		this.world = new World(this.rng);
		for(int i = 0; i < this.pops; i++)
		{
			for(Tile t : this.world.getPopList().get(i).getOwnedTiles())
			{
				this.ownedTiles.add(new GuiTileOwned(t));
			}
		}
		
		//update map
		this.updateWorld();
	}

	//The main function for simulation
	public void cycle(int loops)
	{
		Task<Void> t = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Staring thread");
				for (int i = 0; i < loops; i++) {
					if (this.isCancelled())
						break;
					if (Simulation.instance.isPaused)
						break;
					Simulation.instance.world.cycle();
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							int newNum = Integer.parseInt(Simulation.instance.numCycle.getText().replaceAll(",", ""));
							newNum--;
							Simulation.instance.numCycle.setText(Integer.toString(newNum));	
							Simulation.instance.updateWorld();
						}
					});
					Thread.sleep(500);
				}
				System.out.println("Ending Thread");
				return null;
			}
		};
		Thread thread = new Thread(t);
		thread.setDaemon(true);
		thread.start();
		
		System.out.println("Finished...");
		this.updateWorld();
	}

	private void updateWorld()
	{
		System.out.println("Graphics");
		GuiTile tmp;

		this.canvas.getChildren().clear();
		this.addButton(this.runCycleButton);
		this.addButton(this.pauseButton);
		this.canvas.getChildren().add(this.numCycle);
		for(int x = 0; x < MAPSIZEX; x++)
		{
			for(int y = 0; y < MAPSIZEY; y++)
			{
				tmp = new GuiTile(this.map[x][y]);
				this.addGuiTile(tmp);
			}
		}
		
		this.ownedTiles = new ArrayList<GuiTileOwned>();
		for(int i = 0; i < this.pops; i++)
		{
			for(int x = 0; x < MAPSIZEX; x++)
			{
				for(int y = 0; y < MAPSIZEY; y++)
				{
					if(this.map[x][y].getOwner() != null)
						this.ownedTiles.add(new GuiTileOwned(this.map[x][y]));
				}
			}
		}
		
		for(GuiTileOwned t : this.ownedTiles)
		{
			this.canvas.getChildren().add(t.getPoly());
		}
	}

	public World getWorld()
	{
		return this.world;
	}

	public Tile getTile(int x, int y)
	{
		if(x >= 0 && x < MAPSIZEX && y >= 0 && y < MAPSIZEY)
			return this.map[x][y];
		else
			return null;
	}

	public Tile[][] getMap()
	{
		return this.map;
	}

	public ArrayList<Population> getPopulationList()
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
		this.map[x][y].setRenewableResources(this.rng.nextInt(100));
		this.map[x][y].setNonRenewableResources(this.rng.nextInt(100));
		this.map[x][y].setRegenRate((int)Math.ceil((double)this.map[x][y].getRenewableResources() / 2.0D));
		this.map[x][y].setMaxInhabitants(this.map[x][y].generateMaxInhabitants(0));
		this.landTiles.add(this.map[x][y]);
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
		{
			this.map[x][y].setTerrainModifier(2);
			this.map[x][y].setMaxInhabitants(this.map[x][y].generateMaxInhabitants(2));
		}
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
		int l = 0;
		ArrayList<Tile> list;
		for(int x = 0; x < MAPSIZEX; x++)
		{
			for(int y = 0; y < MAPSIZEY; y++)
			{
				list = this.getSurroundingTiles(x, y);
				for(int i = 0; i < list.size(); i++)
				{
					if(list.get(i).getTerrainModifier() == 0)
						l++;
				}
				if(l > 4)
				{
					this.map[x][y].setTerrainModifier(0);
				}
				l = 0;
			}
		}
	}

	public ArrayList<Tile> getSurroundingTiles(int x, int y)
	{
		Tile t = this.getTile(x, y);
		ArrayList<Tile> out = new ArrayList<Tile>();

		if(y % 2 == 0)
		{
			t = this.getTile(x - 1, y - 3);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x, y - 2);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x, y - 3);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x, y - 1);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x, y + 2);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x - 1, y - 1);
			if(t != null)
			{
				out.add(t);
			}
		}
		else
		{
			t = this.getTile(x, y + 1);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x, y - 2);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x + 1, y + 1);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x + 1, y + 3);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x, y + 2);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x, y + 3);
			if(t != null)
			{
				out.add(t);
			}
		}

		return out;
	}

	public ArrayList<Tile> getSurroundingTiles(Tile tile)
	{
		int x = tile.getX();
		int y = tile.getY();

		Tile t = this.getTile(x, y);
		ArrayList<Tile> out = new ArrayList<Tile>();

		if(y % 2 == 0)
		{
			t = this.getTile(x - 1, y - 3);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x, y - 2);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x, y - 3);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x, y - 1);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x, y + 2);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x - 1, y - 1);
			if(t != null)
			{
				out.add(t);
			}
		}
		else
		{
			t = this.getTile(x, y + 1);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x, y - 2);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x + 1, y + 1);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x + 1, y + 3);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x, y + 2);
			if(t != null)
			{
				out.add(t);
			}

			t = this.getTile(x, y + 3);
			if(t != null)
			{
				out.add(t);
			}
		}

		return out;
	}

	public ArrayList<Tile> getLandTiles()
	{
		return this.landTiles;
	}
	
	public int getNumCycles()
	{
		if(!this.numCycle.getText().isEmpty())
			return Integer.parseInt(this.numCycle.getText().replaceAll(",", ""));
		
		else
			return 1;
	}
	
	public int getNumPops()
	{
		return this.pops;
	}
	
	public int getBirthRate()
	{
		return this.birthRate;
	}
}
