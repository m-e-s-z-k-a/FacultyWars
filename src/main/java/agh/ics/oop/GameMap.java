package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static agh.ics.oop.IndividualType.SETTLER;
import static java.lang.Math.max;

public class GameMap
{
    public final int width;
    public final int height;
    private final Map<Vector2d, MapElement> mapElements = new HashMap<>();
    private final Map<Vector2d, City> cities = new HashMap<>();
    private final Map<Vector2d, Individual> individuals = new HashMap<>();

    public GameMap(int width, int height)
    {
        this.width = width;
        this.height = height;
        Random random = new Random();
        MapElement[] fieldTypes = MapElement.values();
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                mapElements.put(new Vector2d(i, j), fieldTypes[random.nextInt(fieldTypes.length)]);
            }
        }
    }

    private boolean isWithinBounds(Vector2d newPosition)
    {
        return newPosition.x <= width - 1 && newPosition.x >= 0 &&
                newPosition.y <= height - 1 && newPosition.y >= 0;
    }

    private boolean canMoveTo(Individual ind, Vector2d newPosition)
    {
        return isWithinBounds(newPosition) && ind.getType().canWalkThrough(mapElements.get(newPosition));
    }

    public void move(Individual ind, Direction direction)
    {
        Vector2d oldPosition = ind.getPosition();
        Vector2d newPosition = ind.getPosition().add(direction.toUnitVector());
        if (canMoveTo(ind, newPosition) && ind.useMovePoint())
        {
            if (individuals.containsKey(newPosition))
                fight(ind, individuals.get(newPosition));
            else if (cities.containsKey(newPosition))
                cityAttack(ind, cities.get(newPosition));
            else
            {
                ind.changePosition(newPosition);
                this.individuals.put(newPosition, ind);
                this.individuals.remove(oldPosition, ind);
            }
        }
    }

    /** attacker - the one making the move, defender - the one defending theirs position */
    private void fight(Individual attacker, Individual defender)
    {
        if (attacker.getCivilization() != defender.getCivilization())
        {
        Vector2d attackerPosition = attacker.getPosition();
        Vector2d defenderPosition = defender.getPosition();
        MapElement mapElement = mapElements.get(defenderPosition);
        int attackerFightPoints = attacker.getHealthPoints() + attacker.getAttackPoints() + attacker.getDefencePoints() +
                attacker.getType().fightProfit(mapElement) + attacker.getType().defenceProfit(mapElement);
        int defenderFightPoints = defender.getHealthPoints() + defender.getAttackPoints() + defender.getDefencePoints() +
                defender.getType().fightProfit(mapElement) + defender.getType().defenceProfit(mapElement);
        if (defenderFightPoints >= attackerFightPoints)
        {
            defender.getCivilization().changePrestigeResources(20);
            attacker.getCivilization().changePrestigeResources(-20);
            individuals.remove(attackerPosition);
            defender.setHealthPoints(max(defenderFightPoints - attackerFightPoints, 1));
        }
        else
        {
            defender.getCivilization().changePrestigeResources(-20);
            attacker.getCivilization().changePrestigeResources(20);
            individuals.remove(defenderPosition);
            individuals.put(defenderPosition, attacker);
            attacker.setPosition(defenderPosition);
            defender.setHealthPoints(max(attackerFightPoints - defenderFightPoints, 1));
        }}
    }

    private void cityAttack(Individual attacker, City city)
    {
        if (attacker.getCivilization() != city.getCivilization() && attacker.useMovePoint())
        {
        MapElement indMapElement = mapElements.get(attacker.getPosition());
        int indFightPoints = attacker.getAttackPoints() + attacker.getDefencePoints() +
                attacker.getType().fightProfit(indMapElement) + attacker.getType().defenceProfit(indMapElement);

        city.setNumberOfCitizens(max(city.getNumberOfCitizens() - indFightPoints/10, 0));
        city.setNumberOfHammers((int)(city.getNumberOfHammers()/2.0));
        attacker.setHealthPoints((int)(attacker.getHealthPoints()/2.0));
        if (attacker.getHealthPoints() == 0)
            individuals.remove(attacker.getPosition());
        if (city.getNumberOfCitizens() == 0)
            cities.remove(city.getLocation());
        }
    }

    public void createCity(Individual ind)
    {
        if (ind.getType() == SETTLER && ind.getHealthPoints() > 50)
        {
            City city = new City(ind.getPosition(), ind.getCivilization());
            cities.put(ind.getPosition(), city);
            individuals.remove(ind.getPosition());
        }

        ind.getCivilization().changePrestigeResources(50);
    }

    public void placeCityAtTheBeginning(Civilization civ, Vector2d pos)
    {
        City city = new City(pos, civ);
        cities.put(pos, city);
    }

    public boolean isOccupied(Vector2d position)
    {
        return this.individuals.containsKey(position) || this.cities.containsKey(position);
    }

    public Object objectAt(Vector2d position)
    {
        if (cities.containsKey(position))
            return cities.get(position);

        if (individuals.containsKey(position))
            return individuals.get(position);

        return null;
    }

    public void placeIndividual(Individual ind, Vector2d position)
    {
        if (!isOccupied(position))
        {
            this.individuals.put(position, ind);
        }
    }

    public MapElement getFieldElement(Vector2d position)
    {
        return mapElements.get(position);
    }

    public void refreshIndividualsMovePoints()
    {
        for(Vector2d key: individuals.keySet())
            individuals.get(key).refreshMovePoints();
    }

    public void refreshCities()
    {
        for(Vector2d key: cities.keySet())
        {
            City city = cities.get(key);
            // order of function calls IMPORTANT!!!
            city.updateStatistics();
            city.createNewCitizens();
            city.produceFoodAndHammers();
        }
    }

    public Map<Vector2d, Individual> getIndMap()
    {
        return this.individuals;
    }

    public Map<Vector2d, City> getCityMap()
    {
        return this.cities;
    }
}
