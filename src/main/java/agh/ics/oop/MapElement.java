package agh.ics.oop;

public enum MapElement {
    DESERT,
    RIVER,
    PLAIN,
    FOREST,
    MOUNTAINS;

    public String getImagePath()
    {
        return switch(this)
        {
            case DESERT -> "pustynia.png";
            case RIVER -> "rzeka.png";
            case PLAIN -> "step.png";
            case FOREST -> "las.png";
            case MOUNTAINS -> "gory.png";
        };
    }
}
