/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roguelikeengine.largeobjects;

import roguelikeengine.area.AreaLocation;
import roguelikeengine.area.Location;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.item.CompositeItem;

/**
 *
 * @author Greg
 */
public class Feature extends CompositeItem implements Entity {

    public Feature(String name, DisplayChar symbol) {
        super(name, symbol);
    }

    /**
     * @return the location
     */
    @Override
    public AreaLocation getLocation() {
        return (AreaLocation) super.getLocation();
    }
    
    /**
     * 
     * @param l The location to check.
     * @return true if this body occupies that location, false otherwise.
     */
    @Override
    public boolean occupies(Location l) {
        if (l.equals(getLocation()))
            return true;
        else
            return false;
    }

    @Override
    public void beAttacked(Attack a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
