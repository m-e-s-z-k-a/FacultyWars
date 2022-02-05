package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameMap
{
    public static int width;
    public static int height;
    private Map<Vector2d, MapElement> mapElements = new HashMap<>();
    private Map<Vector2d, City> cities = new HashMap<>();
    private Map<Vector2d, Individual> individuals = new HashMap<>();

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

    public boolean isWithinBounds(Vector2d newPosition)
    {
        if (newPosition.x > width-1 || newPosition.x < 0 ||
                newPosition.y > height-1 || newPosition.y < 0)
        {
            return false;
        }
        return true;
    }

    public boolean canMoveTo(Individual ind, Vector2d newPosition)
    {
        if (!isWithinBounds(newPosition) || !(ind.getType().canWalkThrough(mapElements.get(newPosition))))
        {
            return false;
        }
        return true;
    }

    public void move(Individual ind, Direction direction)
    {
        Vector2d oldPosition = ind.getPosition();
        Vector2d newPosition = ind.getPosition().add(direction.toUnitVector());
        if (canMoveTo(ind, newPosition))
        {
            if (mapElements.containsKey(newPosition))
            {
                //napierdalansko ;33
            }
            else
            {
                ind.changePosition(newPosition);
                this.individuals.put(newPosition, ind);
                this.individuals.remove(oldPosition, ind);
            }
        }
    }




}
