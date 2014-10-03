/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.largeobjects;

import roguelikeengine.area.Location;
import roguelikeengine.area.*;

/**
 *
 * @author Greg
 */
public interface Entity {
    
    public Location getLocation();
    
    public void setLocation(Location location);
    
    public boolean occupies(Location l);
    
    public void beAttacked(Attack a);
    
}
