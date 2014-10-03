package roguelikeengine.item;

import roguelikeengine.area.LocalArea;
import roguelikeengine.area.Location;
import roguelikeengine.area.NonexistentLocationException;
import roguelikeengine.area.TerrainDefinition;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.largeobjects.Body;

/**
 *
 * @author greg
 */
public class ItemLocation extends Location {
    private CompositeItem container;
    
    public ItemLocation(CompositeItem container) {
        this.container = container;
    }

    @Override
    public boolean equals(Location l) {
        if (l instanceof ItemLocation)
            return true;
        return false;
    }

    @Override
    protected boolean refactor() {return true;}
    
    public CompositeItem getContainer() {
        return container;
    }

    @Override
    public int getX() {
        return container.getLocation().getX();
    }

    @Override
    public int getY() {
        return container.getLocation().getY();
    }

    @Override
    public LocalArea getArea() {
        return container.getLocation().getArea();
    }

    @Override
    public TerrainDefinition getTerrain() {
        return container.getLocation().getTerrain();
    }

    @Override
    public boolean isPassable() {
        return container.getLocation().isPassable();
    }

    @Override
    public boolean isTransparent() {
        return container.getLocation().isTransparent();
    }

    @Override
    public DisplayChar getSymbol() throws NonexistentLocationException {
        return container.getSymbol();}

    @Override
    public Body bodyAt() {
        if (container instanceof Body) return (Body) container;
        else return container.getLocation().bodyAt();
    }

    @Override
    public String getString() {
        return container.getName();
    }

}
