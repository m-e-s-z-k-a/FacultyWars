package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import agh.ics.oop.gui.App;

public class SimulationEngine
{
    private App app;
    private final int numberOfRounds;
    private final int numberOfPlayers;
    private int civilizationOnMove;
    private boolean duringMove = true;
    private final int width;
    private final int height;
    private final GameMap gameMap;
    private final List<Civilization> civilizationList = new ArrayList<>();

    public SimulationEngine(int width, int height, int numberOfPlayers, int numberOfRounds, App app)
    {
        this.width = width;
        this.height = height;
        this.gameMap = new GameMap(width, height);
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfRounds = numberOfRounds;
        this.app = app;

        Random random = new Random();
        for(int i = 0; i < numberOfPlayers; i++)
        {
            Civilization civ = new Civilization(i + 1);
            civilizationList.add(civ);

            int cities_put = 0;
            do
            {
                int x_coord = random.nextInt(this.width);
                int y_coord = random.nextInt(this.height);
                Vector2d init_position = new Vector2d(x_coord, y_coord);
                if (!this.gameMap.isOccupied(init_position))
                {
                    this.gameMap.placeCityAtTheBeginning(civ, init_position);
                    cities_put += 1;
                }
            }
            while (cities_put < 3);

            int settlers_put = 0;
            do
            {
                int x_coord = random.nextInt(this.width);
                int y_coord = random.nextInt(this.height);
                Vector2d init_position = new Vector2d(x_coord, y_coord);
                if (!this.gameMap.isOccupied(init_position))
                {
                    Individual ind = new Individual(civ, init_position, IndividualType.SETTLER, this.gameMap);
                    this.gameMap.placeIndividual(ind, init_position);
                    settlers_put = 1;
                }
            }
            while (settlers_put < 1);

            IndividualType[] indTypes = IndividualType.values();

            int rest_of_chars_put = 0;
            do {
                int x_coord = random.nextInt(this.width);
                int y_coord = random.nextInt(this.height);
                Vector2d init_position = new Vector2d(x_coord, y_coord);
                int ind_type = random.nextInt(6);
                if (!this.gameMap.isOccupied(init_position)  &&
                        indTypes[ind_type].canWalkThrough(this.gameMap.getFieldElement(init_position)))
                {
                    Individual ind = new Individual(civ, init_position, indTypes[ind_type], this.gameMap);
                    this.gameMap.placeIndividual(ind, init_position);
                    rest_of_chars_put += 1;
                }
            } while (rest_of_chars_put < 3);

        }
    }

    public GameMap getGameMap()
    {
        return this.gameMap;
    }

    public void run()
    {
        for(int currentRound = 1; currentRound < numberOfRounds + 1; currentRound++)
        {
            gameMap.refreshIndividualsMovePoints();
            gameMap.refreshCities();

            for(int i = 1; i < numberOfPlayers + 1; i++)
            {
                synchronized (this)
                {
                    while(duringMove) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        // app.mapUpdate();
    }
}

