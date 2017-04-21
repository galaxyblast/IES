package simulation.gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import simulation.Simulation;

public class PopupMenu
{
	private AnchorPane canvas = new AnchorPane();
	private Scene popup = new Scene(canvas, 300, 200);
	
	public PopupMenu(Stage stage)
	{
		TextField seed = new TextField();
		seed.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
		seed.setLayoutX(130.0D);
		seed.setLayoutY(10.0D);
		
		TextField landPasses = new TextField("20"); //land frequency
		landPasses.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
		landPasses.setLayoutX(130.0D);
		landPasses.setLayoutY(40.0D);
		
		TextField mountainPasses = new TextField("3");
		mountainPasses.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
		mountainPasses.setLayoutX(130.0D);
		mountainPasses.setLayoutY(70.0D);
		
		TextField pops = new TextField("4");
		pops.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
		pops.setLayoutX(130.0D);
		pops.setLayoutY(100.0D);
		
		TextField birth = new TextField("90");
		birth.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
		birth.setLayoutX(130.0D);
		birth.setLayoutY(130.0D);

		Label sLabel = new Label("Seed");
		Label lLabel = new Label("Land Frequency");
		Label mLabel = new Label("Mountin Frequency");
		Label pLabel = new Label("Starting Populations");
		Label bLabel = new Label("Birth Rate");

		sLabel.setLayoutX(5.0D);
		sLabel.setLayoutY(10.0D);
		lLabel.setLayoutX(5.0D);
		lLabel.setLayoutY(40.0D);
		mLabel.setLayoutX(5.0D);
		mLabel.setLayoutY(70.0D);
		pLabel.setLayoutX(5.0D);
		pLabel.setLayoutY(100.0D);
		bLabel.setLayoutX(5.0D);
		bLabel.setLayoutY(130.0D);
		
		this.canvas.getChildren().add(sLabel);
		this.canvas.getChildren().add(lLabel);
		this.canvas.getChildren().add(mLabel);
		this.canvas.getChildren().add(pLabel);
		this.canvas.getChildren().add(bLabel);
		this.canvas.getChildren().add(seed);
		this.canvas.getChildren().add(landPasses);
		this.canvas.getChildren().add(mountainPasses);
		this.canvas.getChildren().add(pops);
		this.canvas.getChildren().add(birth);
		
		GuiButton go = new GuiButton("Begin", 125, 25, (int)(this.getScene().getWidth() / 2 - 62.5D), (int)(this.getScene().getHeight() - 35.0D));
		
		go.setAction(new Action(){
			@Override
			public void start()
			{
				long s = -1L;
				int l = 20, m = 3, p = 4, b = 90;
				
				if(!seed.getText().equals(""))
				{
					s = Long.parseLong(seed.getText().replaceAll(",", ""));
				}
				
				if(!landPasses.getText().equals(""))
				{
					l = Integer.parseInt(landPasses.getText().replaceAll(",", ""));
				}
				
				if(!mountainPasses.getText().equals(""))
				{
					m = Integer.parseInt(mountainPasses.getText().replaceAll(",", ""));
				}
				
				if(!pops.getText().equals(""))
				{
					p = Integer.parseInt(pops.getText().replaceAll(",", ""));
				}
				
				if(!birth.getText().equals(""))
				{
					b = Integer.parseInt(birth.getText().replaceAll(",", ""));
				}
				
				Simulation.instance.begin(stage, s, l, m, p, b);
			}
		});
		
		this.addButton(go);
	}
	
	public void addButton(GuiButton btn)
	{
		this.canvas.getChildren().add(btn.getButton());
	}
	
	public Scene getScene()
	{
		return this.popup;
	}
}
