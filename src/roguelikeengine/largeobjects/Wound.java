/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.largeobjects;

import java.util.HashMap;
import roguelikeengine.item.Item;
import roguelikeengine.item.ItemMod;

/**
 *
 * @author Greg
 */
public class Wound implements StatusEffect{
    private String name;
    private HashMap<Item, ItemMod> mods;

    public Wound(String name) {
        this.name = name;
        mods = new HashMap<>();
    }
    
    /**
     * @return the Name
     */
    public String getName() {
        return name;
    }
    
    
}
