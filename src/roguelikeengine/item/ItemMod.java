/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import java.util.HashMap;
import roguelikeengine.stat.Stat;
import roguelikeengine.stat.Trait;

/**
 *
 * @author Greg
 */
public class ItemMod extends Trait {

    private String adjective;
    
    public ItemMod(String name, String adj) {
        this(name, adj, new HashMap<String, Stat>());
    }

    public ItemMod(String name, String adj, HashMap<String, Stat>  stats) {
        super(name, stats);
        adjective = adj;
    }

    public String getAdjective() {
        return adjective;
    }
}