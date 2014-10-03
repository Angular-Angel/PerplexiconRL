/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.largeobjects;

import roguelikeengine.item.Item;
import java.util.HashMap;
import roguelikeengine.item.*;
import roguelikeengine.stat.Stat;
import roguelikeengine.stat.StatContainer;
import roguelikeengine.stat.Trait;

/**
 *
 * @author Greg
 */
public abstract class Attack extends Trait{
    
    public Attack(String name, HashMap<String, Stat> stats) {
        super(name, stats);
    }
    
    public abstract String attack(Item i);    
}
