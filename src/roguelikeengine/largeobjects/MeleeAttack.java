/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.largeobjects;

import java.util.HashMap;
import roguelikeengine.stat.NoSuchStatException;
import java.util.logging.Level;
import java.util.logging.Logger;
import roguelikeengine.item.Item;
import roguelikeengine.stat.Stat;


/**
 *
 * @author Greg
 */
public class MeleeAttack extends Attack {
    private Item weapon;
    private Body body;

    public MeleeAttack(String name, Item weapon, Body body) {
        this(name, weapon, body, new HashMap<String, Stat>());
    }
    
    public MeleeAttack(String name, Item weapon, Body body, HashMap<String, Stat> stats) {
        super(name, stats);
        this.body = body;
        this.weapon = weapon;
    }

    @Override
    public String attack(Item i){
        return i.takeAttack(this);
        
    }
}
