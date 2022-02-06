package agh.ics.oop.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MoveControls extends HBox
{

    public Button upButton = new Button("\u2191");
    public Button downButton = new Button("\u2193");
    public Button leftButton = new Button("\u2190");
    public Button rightButton = new Button("\u2192");

    public MoveControls()
    {
        super();
        VBox upDownButtonsBox = new VBox(upButton, downButton);
        upDownButtonsBox.setAlignment(Pos.CENTER);
        upDownButtonsBox.setSpacing(5);
        this.getChildren().addAll(leftButton, upDownButtonsBox, rightButton);
        this.setSpacing(5); // TODO ?
    }
}
