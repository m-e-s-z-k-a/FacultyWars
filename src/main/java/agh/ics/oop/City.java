package agh.ics.oop;

public class City
{
    public final Civilization civilization;
    public final Vector2d location;
    private int numberOfCitizens;
    private int hammersProduction;
    private int numberOfHammers;
    private int foodProduction;
    private int foodAmount;
    private int numberOfBuildings;
    // TODO city parameters
    public City(Vector2d location, Civilization civ)
    {
        this.location = location;
        this.numberOfCitizens = 10;
        this.numberOfHammers = 50;
        this.foodAmount = 0;
        this.numberOfBuildings = 1;
        this.foodProduction = this.numberOfBuildings + this.numberOfCitizens/10;
        this.hammersProduction = 5 * this.numberOfBuildings;
        this.civilization = civ;
    }

    public Vector2d getLocation()
    {
        return location;
    }

    public void setNumberOfCitizens(int numberOfCitizens)
    {
        this.numberOfCitizens = numberOfCitizens;
    }

    public void setNumberOfHammers(int numberOfHammers)
    {
        this.numberOfHammers = numberOfHammers;
    }

    public int getNumberOfCitizens()
    {
        return numberOfCitizens;
    }

    public int getNumberOfHammers()
    {
        return numberOfHammers;
    }

    public int getNumberOfBuildings() {return numberOfBuildings;}

    public int getFoodAmount(){return foodAmount;}

    public void buildNewBuilding()
    {
        if (numberOfHammers >= 50)
        {
            numberOfBuildings++;
            numberOfHammers -= 50;
            civilization.changePrestigeResources(30);
        }
    }

    public void createNewCitizens() {
        if (foodAmount > 10)
        {
            numberOfCitizens += foodAmount / 10;
            foodAmount = foodAmount % 10;
        }
    }

    // called at the beginning of each round
    public void updateStatistics()
    {
        this.foodProduction = this.numberOfBuildings + this.numberOfCitizens/10;
        this.hammersProduction = this.numberOfBuildings * 5;
        this.civilization.changeGoldResources(numberOfCitizens*2);
    }

    public void produceFoodAndHammers()
    {
        foodAmount += foodProduction;
        numberOfHammers += hammersProduction;
    }

    public Civilization getCivilization()
    {
        return civilization;
    }
}
