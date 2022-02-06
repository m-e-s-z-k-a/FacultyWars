package agh.ics.oop;

public class Civilization
{
    private int goldResources;
    private int prestigeResources;

    public Civilization(int goldResources, int prestigeResources)
    {
        this.goldResources = goldResources;
        this.prestigeResources = prestigeResources;
    }

    public void changePrestigeResources(int value)
    {
        this.prestigeResources += value;
    }

    public void changeGoldResources(int value)
    {
        this.goldResources += value;
    }
}
