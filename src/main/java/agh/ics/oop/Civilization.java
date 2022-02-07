package agh.ics.oop;

public class Civilization
{
    private int goldResources;
    private int prestigeResources;
    private int ordinal;

    public Civilization(int ordinal)
    {
        this.goldResources = 0;
        this.prestigeResources = 0;
        this.ordinal = ordinal;
    }

    public void changePrestigeResources(int value)
    {
        this.prestigeResources += value;
    }

    public void changeGoldResources(int value)
    {
        this.goldResources += value;
    }

    public int getOrdinal()
    {
        return ordinal;
    }

    public int getPrestigeResources()
    {
        return prestigeResources;
    }

    public int getGoldResources() {
        return goldResources;
    }
}
