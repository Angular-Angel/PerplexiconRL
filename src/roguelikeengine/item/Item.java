/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import roguelikeengine.area.Location;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.display.RoguelikeInterface;
import roguelikeengine.largeobjects.Attack;
import roguelikeengine.largeobjects.Body;
import roguelikeengine.stat.NoSuchStatException;
import roguelikeengine.stat.Stat;
import roguelikeengine.stat.StatContainer;
import roguelikeengine.stat.Trait;

/**
 *
 * @author Greg
 */
public abstract class Item extends StatContainer{
    protected ArrayList<ItemMod> mods;
    protected ArrayList<Trait> attacks;
    private Location location;

    public Item() {
        this(new HashMap<String, Stat>());
    }
    
    public Item(HashMap<String, Stat> stats) {
        super(stats);
        mods = new ArrayList<>();
        attacks = new ArrayList<>();
    }

    
    
    public void addAttack(Trait attack) {
        attacks.add(attack);
    }
    
    public void addAttacks(ArrayList<Trait> attacks) {
        this.attacks.addAll(attacks);
    }
    
    public Trait getAttack(String s) {
        for (Trait t : attacks){
            if (t.getName().equals(s)) return t;
        }
        return null;
    }
    
    public abstract boolean containsPart(String s);
    
    public ArrayList<Trait> getAttacks() {
        return (ArrayList<Trait>) attacks.clone();
    }
    
    public abstract String getName();
    
    public abstract DisplayChar getSymbol();
    
    public abstract void use(RoguelikeInterface display, Body b);
    
    public abstract String takeAttack(Attack A);
    
    @Override
    public void refactor() {
        super.refactor();
        for (ItemMod i : mods) {
            for (String s : i.viewStats().keySet()) {
                try {
                    if (hasStat(s)) {
                        getStat(s).modify(i.viewStat(s).getScore());
                        System.out.println(s +": " + i.getScore(s));
                    } else addStat(s, i.viewStat(s));
                    
                } catch (NoSuchStatException ex) {
                    Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public Item addMod(ItemMod i) {
        mods.add(i);
        refactor();
        return this;
    }
    
    public abstract Item copy();
    
    public abstract void destroy();

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }
    
}