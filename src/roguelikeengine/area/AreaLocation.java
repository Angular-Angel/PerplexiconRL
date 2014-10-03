package roguelikeengine.area;

import java.util.ArrayList;
import roguelikeengine.largeobjects.Body;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.item.ItemOnGround;

/**
 * Stores the data for a point on an Area.
 * @author Greg
 */
public class AreaLocation extends Location {
    private int x, y;
    private LocalArea area;

    /**
     * Default constructor made private.
     */
    
    private AreaLocation() {}
    
    /**
     * Constructor for a location at 0,0 on area.
     * @param area The LocalArea of this location.
     */
    
    public AreaLocation(LocalArea area) {
        this(area, 0, 0);
    }
    
    /**
     * Constructor for a location at x,y on area.
     * @param area The LocalArea of this location.
     * @param x The x coordinate.
     * @param y The Y coordinate.
     */
    
    public AreaLocation(LocalArea area, int x, int y) {
        this.area = area;
        this.x = x;
        this.y = y;
        if (refactor()) throw new IllegalArgumentException(getString());
    }
    
    /**
     * Copy Constructor
     * @param l The Location to copy.
     */
    
    public AreaLocation(AreaLocation l) {
        this.area = l.getArea();
        this.x = l.getX();
        this.y = l.getY();
        refactor();
    }
    
    public AreaLocation(AreaLocation l, int xmod, int ymod) {
        this.area = l.getArea();
        this.x = l.getX() + xmod;
        this.y = l.getY() + ymod;
        refactor();
    }
    
    /**
     * Checks whether the given location points to the same place as this one.
     * @param l The given Location.
     * @return whether the given location points to the same place as this one.
     */
    
    public boolean equals(Location l) {
        return (l instanceof AreaLocation && getX() == l.getX() && 
                getY() == l.getY() && getArea() == l.getArea());
    }
    
    protected boolean refactor() {
       return area.refactor(this);
    }
    
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
        refactor();
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
        refactor();
    }

    /**
     * @return the area
     */
    public LocalArea getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(LocalArea area) {
        this.area = area;
        refactor();
    }
    
    void setLocation(LocalArea l, int x, int y) {
        area = l;
        this.x = x;
        this.y = y;
    }
    
    /**
     * @return The TerrainDefinition at this location.
     */
    
    public TerrainDefinition getTerrain() {
        return getArea().getTerrain(getX(), getY());
    }
    
    /**
     * @return Whether not not this location is passable.
     */
    
    public boolean isPassable() {
        return (getTerrain().isPassable() && getArea().bodyAt(getX(), getY()) == null);
    }
    
    public boolean isTransparent() {
        return (getTerrain().isTransparent());
    }
    
    /**
     * 
     * @return The symbol of the terrain at this location, or of the body here if there is one.
     * @throws NonexistentLocationException if the terrain at this location is null
     */
    
    public DisplayChar getSymbol() throws NonexistentLocationException {
        Body b = getArea().bodyAt(x, y);
        if (b != null)
            return b.getDef().getSymbol();
        ArrayList<ItemOnGround> items = getArea().itemsAt(x, y);
        if (items.size() > 0)
            return items.get(items.size() - 1).getItem().getSymbol();
        
        if (getArea().getTerrain(getX(), getY()) == null) throw new 
                NonexistentLocationException("Tried to access: " + getString());
        return getArea().getTerrain(getX(), getY()).getDisplayChar();
    }
    
    /**
     * moves the location by x and y.
     * @param x The amount to change the x coordinate by.
     * @param y The amount to change the y coordinate by.
     */
    
    public void move(int x, int y) {
        setX(getX() + x);
        setY(getY() + y);
        refactor();
    }
    
    public Body bodyAt() {
        return getArea().bodyAt(getX(), getY());
    }
    
    public int distance(AreaLocation l) {
        return Math.max(Math.abs(getX() - l.getX()), 
                        Math.abs(getY() - l.getY()));
    }
    
    public String getString() {
        return area.getDebugName() + ", " + x + ", " + y;
    }
    
}
