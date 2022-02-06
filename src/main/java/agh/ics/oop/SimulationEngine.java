package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine
{
    private final int numberOfRounds;
    private final int numberOfPlayers;
    private final GameMap gameMap;
    private final List<Civilization> civilizationList = new ArrayList<>();

    // TODO losowanie jednostek
    public SimulationEngine(int width, int height, int numberOfPlayers, int numberOfRounds)
    {
        this.gameMap = new GameMap(width, height);
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfRounds = numberOfRounds;
        for(int i = 0; i < numberOfPlayers; i++)
        {

        }
    }

    public void run()
    {

    }
}

