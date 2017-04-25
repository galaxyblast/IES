package simulation.gui;

import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import simulation.Population;
import simulation.Simulation;

public class AddPopMenu {
	private AnchorPane canvas = new AnchorPane();
	private Scene popup = new Scene(canvas, 300, 200);
	private Stage stage = null;
	
	public AddPopMenu(Stage stage, Label poplabel) {
		this.stage = stage;

		TextField needsText = new TextField();
		needsText.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));

		Label nLabel = new Label("Needs: ");
		
		TextField repoText = new TextField();
		repoText.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
		
		Label rLabel = new Label("Reproduction Rate: ");
		
		needsText.setLayoutX(125.0D);
		needsText.setLayoutY(50.0D);
		repoText.setLayoutX(125.0D);
		repoText.setLayoutY(90.0D);
		
		nLabel.setLayoutX(5.0D);
		nLabel.setLayoutY(50.0D);
		rLabel.setLayoutX(5.0D);
		rLabel.setLayoutY(90.0D);
		
		GuiButton addButton = new GuiButton("Add", 125, 25, (int)(this.getScene().getWidth() / 2 - 62.5D), (int)(this.getScene().getHeight() - 35.0D));
		addButton.setAction(new Action(){
			@Override
			public void start() {
				System.out.println("Whoop");
				Random rand = new Random();
				int needs = 0;
				if (!needsText.getText().equals(""))
					needs = Integer.parseInt(needsText.getText().replaceAll(",", ""));
				Population newPop = new Population(rand, needs, 0);
				Simulation.instance.getPopulationList().add(newPop);
				
				int len = Simulation.instance.getPopulationList().size();
				
				poplabel.setText(Integer.toString(len));
				
				stage.close();
			}
		});
		
		canvas.getChildren().add(nLabel);
		canvas.getChildren().add(needsText);
		canvas.getChildren().add(rLabel);
		canvas.getChildren().add(repoText);
		canvas.getChildren().add(addButton.getButton());
		
	}
	
	public Scene getScene() {
		return this.popup;
	}
}
