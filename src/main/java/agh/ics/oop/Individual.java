package agh.ics.oop;


public class Individual
{
    private Civilization belongsTo;
    private Vector2d position;
    private IndividualType type;
    private int healthPoints;
    private int attackPoints;
    private int defencePoints;

    public Individual(Civilization civ, Vector2d position, IndividualType type)
    {
        this.belongsTo = civ;
        this.healthPoints = 100;
        this.position = position;
        this.type = type;

    }

    public IndividualType getType()
    {
        return this.type;
    }

    public Vector2d getPosition()
    {
        return this.position;
    }

    public void changePosition(Vector2d newPosition)
    {
        this.position = newPosition;
    }


}
