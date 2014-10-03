/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import roguelikeengine.largeobjects.Attack;
import roguelikeengine.largeobjects.Body;
import roguelikeengine.stat.NoSuchStatException;
import roguelikeengine.stat.Trait;

/**
 *
 * @author greg
 */
public interface AttackScript {
    
    public Attack generateAttack(Body attacker, Item weapon, Trait attack);
    
}
