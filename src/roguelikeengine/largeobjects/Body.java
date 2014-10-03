package roguelikeengine.largeobjects;

import roguelikeengine.stat.NoSuchStatException;
import roguelikeengine.controller.DijkstraMap;
import roguelikeengine.controller.Controller;
import roguelikeengine.item.Item;
import roguelikeengine.item.CompositeItem;
import roguelikeengine.area.Location;
import roguelikeengine.area.AreaLocation;
import java.util.*;
import roguelikeengine.largeobjects.Attack;
import roguelikeengine.largeobjects.BodyDefinition;
import roguelikeengine.largeobjects.Entity;
import roguelikeengine.largeobjects.StatusEffect;
import roguelikeengine.area.*;
import roguelikeengine.controller.*;
import roguelikeengine.item.*;
import roguelikeengine.stat.*;

/**
 *
 * @author greg
 */
public class Body extends CompositeItem implements Entity {
    private String name;
    private Controller controller;
    private BodyDefinition def;
    private ArrayList<Item> inventory;
    private DijkstraMap map;
    private int moves;
    private ArrayList<StatusEffect> effects;
    private Item weapon;
    
    public Body(AreaLocation location, BodyDefinition bodyDef) {
        this("", location, bodyDef);
    }

    public Body(String name, AreaLocation location, BodyDefinition bodyDef) {
        super(name, bodyDef.getSymbol(), bodyDef.viewStats(), null);
        setLocation(location);
        this.def = bodyDef;
        this.name = name;
        inventory = new ArrayList<>();
        effects = new ArrayList<>();
        moves = 0;
        weapon = null;
        for (Item i : bodyDef.bodyTemplate.getParts()) {
            addPart(i.copy());
        }
        
        refactor();
        
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
    
    public boolean isAlive() {
        return getDef().getBioScript().isAlive(this);
    }
    
    public void step() {
        getDef().getBioScript().step(this);
    }
    
    @Override
    public void setLocation(Location location) {
        if (location instanceof AreaLocation) {
            if (getLocation() != null && location.getArea() != getLocation().getArea()) {
                getLocation().getArea().removeEntity(this);
                location.getArea().addEntity(this);

            }
            super.setLocation((AreaLocation) location);
        }
    }

    /**
     * @return the controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * @return the BodyDefinition
     */
    public BodyDefinition getDef() {
        return def;
    }
    
    /**
     * 
     * @param l The location to move to.
     * @return true if the body moved, false otherwise.
     */
    
    public boolean moveTo(AreaLocation l) {
        if (l.isPassable()) {
            setLocation(l);
            addMoves(-100);
            if (getMap() != null) {getMap().fill(getLocation());}
            return true;
        } return false;
    }
    
    public void addItem(Item i) {
        inventory.add(i);
        addPart(i);
    }
    
    public void removeItem(Item i) {
        inventory.remove(i);
        removePart(i);
    }

    /**
     * @return the inventory
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * @return the map
     */
    public DijkstraMap getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(DijkstraMap map) {
        this.map = map;
    }

    /**
     * @return the moves
     */
    public int getMoves() {
        return moves;
    }

    /**
     * @param moves the moves to add
     */
    public void addMoves(int moves) {
        this.moves += moves;
    }

    public void addMoves() {
        try {
            if (getScore("Speed") > 0) {
                this.moves += getScore("Speed");
            }
        } catch (NoSuchStatException e) {
            this.moves += 100;
        }
    }
    
    public void addStatusEffect(StatusEffect e) {
        effects.add(e);
    }
    
    public void beAttacked(Attack a) {
        //code for dodging goes here.
        //code for blocking goes here.
        //Determine hit location.
                
        def.getBioScript().beAttacked(this, a);
        
//        int hitLoc = (int) (Math.random() );
//        Item i = null;
//        
//        for (Item e : parts) {
//            try {
//                if (hitLoc > e.getScore("Size")) {hitLoc -= e.getScore("Size");}
//                else {i = e; break;}
//            } catch (NoSuchStatException ex) {
//                Logger.getLogger(Body.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        if (i != null) {
//            String result = a.attack(i);
//            if (result.contains("destroy")) {
//                removeItem(i);
//                removePart(i);
//            }
//            System.out.println(result);
//        }
        //Code for taking damage.
        
    }

    @Override
    public String getName() {
        return name;
    }
    /**
     * @return the weapon
     */
    public Item getWeapon() {
        return weapon;
    }

    /**
     * @param weapon the weapon to set
     */
    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }
    
    
    
}
