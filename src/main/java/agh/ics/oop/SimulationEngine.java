package agh.ics.oop;

import java.util.*;

import agh.ics.oop.gui.App;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static agh.ics.oop.IndividualType.SETTLER;

public class SimulationEngine implements Runnable
{
    private App app;
    private final int numberOfRounds;
    private final int numberOfPlayers;
    private boolean duringMove;
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
                    Individual ind = new Individual(civ, init_position, SETTLER, this.gameMap);
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

    private void computerMoves()
    {
        Random random = new Random();
        Map<Vector2d, Individual> indCopy = new HashMap<>(this.getGameMap().getIndMap());
        for(Vector2d key: indCopy.keySet())
        {
            if (indCopy.get(key).getCivilization().getOrdinal() != 1)
            {
                if (indCopy.get(key).getType() == SETTLER)
                {
                    if (random.nextInt(2) == 1)
                    {
                        this.gameMap.createCity(indCopy.get(key));
                    }
                }
                Direction[] dirs = Direction.values();
                Individual ind = this.gameMap.getIndMap().get(key);
                if (ind != null)
                    this.gameMap.move(ind, dirs[random.nextInt(4)]);
            }
        }
        Map<Vector2d, City> cityCopy = new HashMap<>(this.getGameMap().getCityMap());
        for(Vector2d key: cityCopy.keySet())
        {
            if (cityCopy.get(key).getCivilization().getOrdinal() != 1)
            {
                this.gameMap.getCityMap().get(key).buildNewBuilding();
            }
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
            if (currentRound != 1 && hasGameEnded(currentRound))
                break;

            duringMove = true;

            gameMap.refreshIndividualsMovePoints();
            gameMap.refreshCities();

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

            computerMoves();
            app.mapUpdate();
        }
        app.closeMap();
        endGame();
    }

    public void endMove()
    {
        synchronized (this)
        {
            this.duringMove = false;
            notifyAll();
        }
    }

    private void endGame()
    {
        Platform.runLater(() -> {
            int winner = 1;
            int max_points = 0;

            for(Civilization civilization: civilizationList)
            {
                civilization.changePrestigeResources(civilization.getGoldResources()/50);
                if (max_points < civilization.getPrestigeResources())
                {
                    max_points = civilization.getPrestigeResources();
                    winner = civilization.getOrdinal();
                }
            }

            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(new Text("Civilization " + winner + " won!"));
            Scene scene = new Scene(vbox, 200, 200);
            Stage stage  = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e -> System.exit(0));
        });
    }

    public List<Civilization> getCivilizationList()
    {
        return this.civilizationList;
    }

    public int getNumberOfPlayers()
    {
        return this.numberOfPlayers;
    }


    private boolean hasGameEnded(int currentRound)
    {
        if (isCivDead(0))
        {
            // make computer moves till last round then end
            for(int i = currentRound; i < numberOfRounds + 1; currentRound++)
                computerMoves();
            return true;
        }


        for(int i = 1; i < numberOfPlayers; i++)
            if (!isCivDead(i))
                return false;

        return true;
    }

    private boolean isCivDead(int civNumber)
    {
        Civilization civ = civilizationList.get(civNumber);
        var inds = gameMap.getIndMap();
        for (Vector2d key: inds.keySet())
        {
            Individual ind = inds.get(key);
            if (ind.getCivilization().equals(civ))
                return false;
        }

        var cities = gameMap.getCityMap();
        for(Vector2d key: cities.keySet())
        {
            City city = cities.get(key);
            if (city.getCivilization().equals(civ))
                return false;
        }

        return true;
    }
}
