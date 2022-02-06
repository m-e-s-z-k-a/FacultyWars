package agh.ics.oop.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class ParameterBox extends HBox
{
    Font font = new Font("Comic Sans MS", 12);

    public ParameterBox(String title, int min, int max)
    {
        Label label = new Label(title);
        label.setFont(font);
        Slider slider = new Slider(min, max, (min+max)/2);
        slider.setMajorTickUnit(1.0);
        slider.setShowTickMarks(true);
        slider.setSnapToTicks(true);
        slider.setShowTickLabels(true);
        slider.setShowTickLabels(true);
        this.getChildren().add(label);
        this.getChildren().add(slider);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
    }
}
