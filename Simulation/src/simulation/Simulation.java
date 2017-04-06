package simulation;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simulation.gui.GuiButton;

public class Simulation extends Application
{
	private static final int MAPSIZE = 50;

	public final Pane canvas = new Pane();
	private Scene scene = new Scene(this.canvas, 1024, 768);
	private World world;
	private Tile[][] map = new Tile[MAPSIZE][MAPSIZE];
	private Population[] popList = new Population[5];

	//Use this to call any needed function from here
	public static final Simulation instance = new Simulation();

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
		GuiButton runCycleButton = new GuiButton("Run simulation", 125, 25, (int)(this.canvas.getWidth() / 2 - 62.5D), (int)(this.canvas.getHeight() - 30.0D));

		//Add screen elements
		this.addButton(runCycleButton);
	}

	//Anything that needs to be instantiated with the world goes here
	public void setupWorld()
	{

	}

	//The main function for simulation
	public void cycle(int loops)
	{

	}

	public World getWorld()
	{
		return this.world;
	}

	public Tile getTile(int x, int z)
	{
		if(x >= 0 && x < MAPSIZE && z >= 0 && z < MAPSIZE)
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
}
