/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import roguelikeengine.area.AreaLocation;
import roguelikeengine.area.Location;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.display.RoguelikeInterface;
import roguelikeengine.largeobjects.Attack;
import roguelikeengine.largeobjects.Body;
import roguelikeengine.stat.NoSuchStatException;
import roguelikeengine.stat.NumericStat;
import roguelikeengine.stat.Stat;
import roguelikeengine.stat.Trait;

/**
 *
 * @author Greg
 */
public class CompositeItem extends Item {
    
    protected ArrayList<Item> parts;
    private String name;
    private DisplayChar symbol;
    private ItemScript useScript;
    
    public CompositeItem(String name, DisplayChar symbol) {
        this(name, symbol, new HashMap<String, Stat>(), null);
    }
    
    public CompositeItem(String name, DisplayChar symbol, HashMap<String, Stat> stats, ItemScript use) {
        super(stats);
        this.name = name;
        this.symbol = symbol;
        useScript = use;
        parts = new ArrayList<>();
        for (Stat s : viewStats().values()) {
            s.setContainer(this);
        }
    }
    
    public void addPart(Item part) {
        parts.add(part);
        part.setLocation(new ItemLocation(this));
    }
    
    public void removePart(Item part) {
        parts.remove(part);
    }
    
    @Override
    public boolean containsPart(String s) {
        if (getName().equals(s)) return true;
        for (Item i : getParts()) {
            if (i.containsPart(s))
                return true;
        }
        return false;
    }
    
    public final ArrayList<Item> getParts() {
        return parts;
    }
    
    public Item getRandomPart() throws NoSuchStatException {
        Random random = new Random();
        float size = getScore("Size");
        int target = random.nextInt((int) size);
        for (Item part : getParts()) {
            if (target < part.getScore("Size"))
                return part;
            else target -= part.getScore("Size");
        }
        return null;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public DisplayChar getSymbol() {
        return symbol;
    }
    
    @Override
    public void refactor() {
        for (Item i : parts) i.refactor();
        for (Stat s : getStats().values())
            s.setContainer(this);
        super.refactor();
    }

    @Override
    public String takeAttack(Attack a) {
        int hitLoc = 0;
        try {
            hitLoc = (int) (Math.random() * getScore("Size"));
        } catch (NoSuchStatException ex) {
            Logger.getLogger(CompositeItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        Item i = null;
        
        for (Item e : parts) {
            try {
                if (hitLoc > e.getScore("Size")) {hitLoc -= e.getScore("Size");}
                else {i = e; break;}
            } catch (NoSuchStatException ex) {
                Logger.getLogger(Body.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (i != null) {
            String result = i.takeAttack(a);
            if (result.contains("Deleted")) {
                removePart(i);
            }
            return result;
        }
        else return "Nothig interesting happened.";
    }

    @Override
    public Item copy() {
        CompositeItem ret = new CompositeItem(name, symbol);
        for (String s : viewStats().keySet()) {
            try {
                ret.addStat(s, viewStat(s).copy());
            } catch (NoSuchStatException ex) {
                Logger.getLogger(Trait.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Trait t : getAttacks()) {
            ret.addAttack(t.Copy());
        }
        for (Item i : parts) {
            ret.addPart(i.copy());
        }
        return ret;
    }

    @Override
    public void destroy() {
        Location location = getLocation();
        if (location instanceof AreaLocation) {
            AreaLocation areaLocation = (AreaLocation) location;
            for (Item part : parts) {
                areaLocation.getArea().addEntity(new ItemOnGround(areaLocation, part));
            }
            areaLocation.getArea().removeItem(this);
        } else if (location instanceof ItemLocation) {
            ItemLocation itemLocation = (ItemLocation) location;
            for (Item part : parts) {
                itemLocation.getContainer().addPart(part);
            }
            itemLocation.getContainer().removePart(this);
        }
    }

    @Override
    public void use(RoguelikeInterface display, Body b) {
        useScript.run(display, this, b);
    }
    
}
