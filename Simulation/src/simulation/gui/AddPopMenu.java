package simulation.gui;

import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import simulation.Population;
import simulation.Simulation;

public class AddPopMenu {
	private AnchorPane canvas = new AnchorPane();		// The canvas to hold our form's items
	private Scene popup = new Scene(canvas, 300, 200);	// The scene to hold the this form's canvas
	
	/*
	 *	AddPopMenu is the constructor for the AddPopMenu window.
	 *	This window is responsible for getting input form the user to create a new population
	 *		stage:		The stage that this scene will be displayed on
	 *		popLabel:	A label on the PopupMenu that called this constructor that will be updated with the new number of populations
	 */
	public AddPopMenu(Stage stage, Label poplabel) {

		// Set up the label and text field where the user will enter the amount of resources this population will "need"
		Label nLabel = new Label("Needs: ");
		TextField needsText = new TextField();
		needsText.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
		
		// Set up the label and text field where the user will enter the reproduction rate of the population
		Label rLabel = new Label("Reproduction Rate: ");
		TextField repoText = new TextField();
		repoText.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
		
		// Set the coordinates for the text fields
		needsText.setLayoutX(125.0D);
		needsText.setLayoutY(50.0D);
		repoText.setLayoutX(125.0D);
		repoText.setLayoutY(90.0D);
		
		// Set the coordinates for the labels
		nLabel.setLayoutX(5.0D);
		nLabel.setLayoutY(50.0D);
		rLabel.setLayoutX(5.0D);
		rLabel.setLayoutY(90.0D);

		// Create the button 
		GuiButton addButton = new GuiButton("Add", 125, 25, (int)(this.popup.getWidth() / 2 - 62.5D), (int)(this.popup.getHeight() - 35.0D));
		
		// Give the button functionality
		addButton.setAction(new Action(){
			@Override
			public void start() {
				Random rand = new Random();
				int needs = 0;
				int repRate = 0;
				
				if (!needsText.getText().equals(""))
					needs = Integer.parseInt(needsText.getText().replaceAll(",", ""));
				if (!repoText.getText().equals(""))
					repRate = Integer.parseInt(repoText.getText().replaceAll(",", ""));
				
				// Here we ensure that the reproduction rate is in the right range of numbers (0-100)
				if (repRate < 0)
					repRate *= -1;
				if (repRate > 100)
					repRate = 100;

				// Create a new population and add it to the list of populations
				Population newPop = new Population(rand, needs, repRate);
				Simulation.instance.getPopulationList().add(newPop);
				
				// Update the label with the new amount of populations
				int len = Simulation.instance.getPopulationList().size();
				poplabel.setText(Integer.toString(len));
				
				// Close this window
				stage.close();
			}
		});
		
		// Actually display the objects we've created
		canvas.getChildren().add(nLabel);
		canvas.getChildren().add(needsText);
		canvas.getChildren().add(rLabel);
		canvas.getChildren().add(repoText);
		canvas.getChildren().add(addButton.getButton());
		
	}
	
	/*
	 *	A getter to get this scene
	 */
	public Scene getScene() {
		return this.popup;
	}
}
