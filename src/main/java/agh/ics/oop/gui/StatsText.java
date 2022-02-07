package agh.ics.oop.gui;

import agh.ics.oop.SimulationEngine;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class StatsText extends HBox
{
    private SimulationEngine engine;
    public StatsText(SimulationEngine engine)
    {
        this.engine = engine;
        for (int i = 0; i < engine.getNumberOfPlayers(); i++)
        {
            this.getChildren().add(new Text("civ: " + (i+1) + " gold: " + engine.getCivilizationList().get(i).getGoldResources() + " prestige: " + engine.getCivilizationList().get(i).getPrestigeResources()));
        }
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
    }

    public void update()
    {
        this.getChildren().clear();
        for (int i = 0; i < engine.getNumberOfPlayers(); i++)
        {
            this.getChildren().add(new Text("civ: " + (i+1) + " gold: " + engine.getCivilizationList().get(i).getGoldResources() + " prestige: " + engine.getCivilizationList().get(i).getPrestigeResources()));
        }
    }
}
