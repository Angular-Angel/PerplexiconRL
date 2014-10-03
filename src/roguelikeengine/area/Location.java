package roguelikeengine.area;

import java.util.ArrayList;
import roguelikeengine.largeobjects.Body;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.item.ItemOnGround;

/**
 * Stores the data for a point on an Area.
 * @author Greg
 */
public abstract class Location {

    /**
     * Default constructor made private.
     */
    
    
    
    public abstract boolean equals(Location l);
    
    protected abstract boolean refactor();
    
    /**
     * @return the x
     */
    public abstract int getX();

    /**
     * @return the y
     */
    public abstract int getY();

    /**
     * @return the area
     */
    public abstract LocalArea getArea();
    
    /**
     * @return The TerrainDefinition at this location.
     */
    
    public abstract TerrainDefinition getTerrain();
    
    /**
     * @return Whether not not this location is passable.
     */
    
    public abstract boolean isPassable();
    
    public abstract boolean isTransparent();
    
    /**
     * 
     * @return The symbol of the terrain at this location, or of the body here if there is one.
     * @throws NonexistentLocationException if the terrain at this location is null
     */
    
    public abstract DisplayChar getSymbol() throws NonexistentLocationException;
    
    public abstract Body bodyAt();
    
    public int distance(Location l) {
        return Math.max(Math.abs(getX() - l.getX()), 
                        Math.abs(getY() - l.getY()));
    }
    
    public abstract String getString();
    
}
