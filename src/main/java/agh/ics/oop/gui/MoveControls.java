package agh.ics.oop.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MoveControls extends HBox
{
    public Button upButton = new Button("up");
    public Button downButton = new Button("down");
    public Button leftButton = new Button("right");
    public Button rightButton = new Button("left");

    public MoveControls()
    {
        super();
        VBox updownButtonsBox = new VBox(upButton, downButton);
        updownButtonsBox.setAlignment(Pos.CENTER);
        updownButtonsBox.setSpacing(5);
        this.getChildren().add(leftButton);
        this.getChildren().add(updownButtonsBox);
        this.getChildren().add(rightButton);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);
    }

}
