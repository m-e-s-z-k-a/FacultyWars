package agh.ics.oop;

public enum IndividualType {
    AGGRESSOR,
    MAGE,
    PIRATE,
    SANDMAN,
    HIKER,
    MOUNTAIN_MAN,
    SETTLER;

    public boolean canWalkThrough(MapElement field)
    {
        boolean result;
        switch(this)
        {
            case AGGRESSOR -> result =
                switch(field) {
                    case DESERT, RIVER, MOUNTAINS -> false;
                    default -> true;
                };

            case MAGE, SETTLER -> result = true;

            case PIRATE -> result =
                switch(field) {
                case RIVER, PLAIN -> true;
                default -> false;
                };

            case SANDMAN -> result =
                switch(field) {
                    case RIVER, MOUNTAINS -> false;
                    default -> true;
                };

            case HIKER -> result = field == MapElement.RIVER;

            case MOUNTAIN_MAN -> result =
                switch(field) {
                    case RIVER, DESERT -> false;
                    default -> true;
                };
            default -> throw new IllegalStateException("Unexpected value: " + this);
        }

        return result;
    }

    public int fightProfit(MapElement field) // TODO profits
    {
        int result = 0;
        switch(field)
        {
            case DESERT -> {
                if (this == SANDMAN)
                    result = 20;
            }
            case MOUNTAINS -> {
                if (this == MOUNTAIN_MAN)
                    result = 20;
            }
            case RIVER -> {
                if (this == PIRATE)
                    result = 20;
            }
            case PLAIN -> {
                if (this == HIKER)
                    result = 20;
            }
        }
        if (this == MAGE || this == AGGRESSOR)
            result = 20;
        return result;
    }

    public int defenceProfit(MapElement field)
    {
        int result = 0;
        switch(field)
        {
            case DESERT -> {
                if (this == SANDMAN)
                    result = 5;
            }
            case MOUNTAINS -> {
                if (this == MOUNTAIN_MAN)
                    result = 5;
            }
            case RIVER -> {
                if (this == PIRATE)
                    result = 5;
            }
            case PLAIN -> {
                if (this == HIKER)
                    result = 5;
            }
            case FOREST -> result = 5;
        }
        if (this == MAGE)
            result = 5;
        return result;
    }

    public int getMovePoints()
    {
        return switch(this)
        {
            case AGGRESSOR, SANDMAN, MOUNTAIN_MAN, SETTLER -> 1;
            case PIRATE, HIKER -> 2;
            case MAGE -> 3;
        };
    }

    public String getImagePath()
    {
        return switch(this)
        {
            case AGGRESSOR -> "agresor.png";
            case MAGE -> "mag.png";
            case PIRATE -> "pirat.png";
            case SANDMAN -> "sandman.png";
            case HIKER -> "hiker.png";
            case MOUNTAIN_MAN -> "goral.png";
            case SETTLER -> "settler.png";
        };
    }
}
