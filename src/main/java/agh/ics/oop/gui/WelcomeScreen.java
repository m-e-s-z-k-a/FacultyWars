package agh.ics.oop.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class WelcomeScreen extends VBox
{
    Label title = new Label("Willkommen aus dem Spiel");
    public ParameterBox playersNumberBox = new ParameterBox("number of players", 2, 10);
    public ParameterBox mapWidthBox = new ParameterBox("map width", 10, 100);
    public ParameterBox mapHeightBox = new ParameterBox("map height", 10, 100);
    public Button startButton = new Button("start!");

    public WelcomeScreen()
    {
        super();
        title.setFont(new Font("Comic Sans MS", 20));
        startButton.setFont(new Font("Comic Sans MS", 10));
        this.getChildren().addAll(title, playersNumberBox, mapWidthBox, mapHeightBox, startButton);
        this.setAlignment(Pos.CENTER); // TODO ?
        this.setSpacing(15);
    }
}
