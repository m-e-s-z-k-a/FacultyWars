package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import agh.ics.oop.gui.App;

public class SimulationEngine
{
    private App app;
    private final int numberOfRounds;
    private final int numberOfPlayers;
    private final GameMap gameMap;
    private final List<Civilization> civilizationList = new ArrayList<>();

    // TODO losowanie jednostek
    public SimulationEngine(int width, int height, int numberOfPlayers, int numberOfRounds, App app)
    {
        this.gameMap = new GameMap(width, height);
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfRounds = numberOfRounds;
        this.app = app;
        for(int i = 0; i < numberOfPlayers; i++)
        {

        }
    }

    public GameMap getGameMap()
    {
        return this.gameMap;
    }

    public void run()
    {
        // app.mapUpdate();
    }
}

