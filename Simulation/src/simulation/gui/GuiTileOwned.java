package simulation.gui;

import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import simulation.Tile;

public class GuiTileOwned
{
	private Tile tile;
	private Polygon hex;
	private static final double MAPOFFX = 24.0D;
	private static final double MAPOFFY = 32.0D;

	public GuiTileOwned(Tile t)
	{
		this.tile = t;
		this.hex = new Polygon();

		hex.getPoints().addAll(new Double[]{
				4.0D, 0.0D,
				12.0D, 0.0D,
				16.0D, -8.0D,
				12.0D, -16.0D,
				4.0D, -16.0D,
				0.0D, -8.0D
		});
		hex.setStroke(Color.BLACK);
		Color c;

		switch(this.tile.getOwner().getID())
		{
		case 0: //debug color
				c = Color.RED;
				break;
		case 1:
			c = Color.WHITE;
			break;
		case 2:
			c = Color.BLACK;
			break;
		case 3:
		default:
			c = Color.PURPLE;
			break;
		}

		hex.setFill(c);
		hex.setOpacity(0.2D);

		double offX = 0.0D, offY = 0.0D;
		if(this.tile.getY() % 2 == 1) //only offset every other row
		{
			offX = 12.0D;	//offset by 4/3 the x size
			offY = 16.0D;	//offset by twice the y size
		}

		if(this.tile.getY() == 49)  //for whatever reason the last row isn't offset right
		{
			offX = 0.0D;	//offset by 4/3 the x size
			offY = 8.0D;	//offset by twice the y size
		}

		if(this.tile.getY() == 0)  //neither is the first row...
		{
			offX = 12.0D;	//offset by 4/3 the x size
			offY = 8.0D;	//offset by twice the y size
		}

		hex.setLayoutX(((double)this.tile.getX() * 24.0D) + offX + MAPOFFX); //offset each grid point by 8/3 the x size
		hex.setLayoutY(((double)this.tile.getY() * 8.0D) + offY + MAPOFFY); //offset each grid point by the y size
		
		String own = "None";
		if(this.tile.getOwner() != null)
			own = this.tile.getOwner().getName();
		
		Tooltip tip = new Tooltip("X: " + this.tile.getX() +
								"\nY: " + this.tile.getY() +
								"\nOwner: " + own +
								"\nCivilians: " + this.tile.getInhabitants().size() +
								"\nRenewable Resources: " + this.tile.getRenewableResources() +
								"\nNonrenewable Resources: " + this.tile.getNonRenewableResources() +
								"\nMax Inhabitants: " + this.tile.getMaxInhabitants());
		Tooltip.install(hex, tip);
	}

	public Polygon getPoly()
	{
		return this.hex;
	}
}
