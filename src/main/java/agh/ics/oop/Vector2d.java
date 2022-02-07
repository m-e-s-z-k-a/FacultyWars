package agh.ics.oop;

import java.util.Objects;

public class Vector2d {

    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return ("(" + this.x + ", " + this.y + ")");
    }

    public Vector2d add(Vector2d other)
    {
        return new Vector2d (this.x + other.x, this.y + other.y);
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return that.x == this.x && that.y == this.y;
    }

    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}