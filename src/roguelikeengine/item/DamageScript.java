/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import roguelikeengine.largeobjects.Attack;
import roguelikeengine.stat.NoSuchStatException;

/**
 *
 * @author greg
 */
public interface DamageScript {
    
    public void run(Attack a, Item i) throws NoSuchStatException;
    
}
