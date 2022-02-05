package agh.ics.oop;

public class City
{
    public final Civilization belongsTo;
    public final Vector2d location;
    private int numberOfCitizens;
    private int numberOfHammers;
    private int foodProduction;

    public City(Vector2d location, Civilization civ)
    {
        this.location = location;
        this.numberOfCitizens = 10; // my ustalamy, a nie użytkownik debil
        this.numberOfHammers = 50;
        this.foodProduction = 2;
        this.belongsTo = civ;
    }



}
