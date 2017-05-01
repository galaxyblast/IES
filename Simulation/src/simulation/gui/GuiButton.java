package simulation.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class GuiButton
{
	private Button button;

	private Action act;

	public GuiButton(String txt, int width, int height, int x, int y)
	{
		Button btn = new Button(txt);
		btn.setPrefHeight(height);
		btn.setMinHeight(height);
		btn.setMaxHeight(height);
		btn.setPrefWidth(width);
		btn.setMinWidth(width);
		btn.setMaxWidth(width);
		btn.setLayoutX((double)x);
		btn.setLayoutY((double)y);
		this.button = btn;
	}

	public Button getButton()
	{
		return this.button;
	}

	public void setAction(Action action)
	{
		this.act = action;
		this.button.setOnAction(new EventClick(this));
	}

	public void action()
	{
		if(this.act != null)
			this.act.start();
	}

	protected void setBackgroundColor(double r, double g, double b, double a)
	{
		this.button.setBackground(new Background(new BackgroundFill(new Color(r, g, b, a), new CornerRadii(0.0D), new Insets(0.0D))));
	}
}

class EventClick implements EventHandler<ActionEvent>
{
	private GuiButton btn;

	public EventClick(GuiButton btn)
	{
		this.btn = btn;
	}

	public void handle(ActionEvent arg0)
	{
		this.btn.action();
	}
}
