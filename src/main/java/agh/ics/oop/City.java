package agh.ics.oop;

public class City
{
    public final Civilization belongsTo;
    public final Vector2d location;
    private int numberOfCitizens;
    private int numberOfHammers;
    private int foodProduction;
    private int numberOfBuilding;

    public City(Vector2d location, Civilization civ)
    {
        this.location = location;
        this.numberOfCitizens = 10; // my ustalamy, a nie użytkownik debil
        this.numberOfHammers = 50;
        this.foodProduction = 2;
        this.belongsTo = civ;
    }

    public Vector2d getLocation()
    {
        return location;
    }

    public void setNumberOfCitizens(int numberOfCitizens)
    {
        this.numberOfCitizens = numberOfCitizens;
    }

    public int getNumberOfCitizens()
    {
        return numberOfCitizens;
    }

    public void setNumberOfHammers(int numberOfHammers)
    {
        this.numberOfHammers = numberOfHammers;
    }
}
