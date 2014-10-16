package roguelikeengine.item;

import roguelikeengine.display.RoguelikeInterface;
import roguelikeengine.display.DisplayChar;
import java.util.logging.Level;
import java.util.logging.Logger;
import roguelikeengine.Registry;
import roguelikeengine.area.AreaLocation;
import roguelikeengine.area.Location;
import roguelikeengine.stat.Stat;
import roguelikeengine.largeobjects.Attack;
import roguelikeengine.largeobjects.Body;
import roguelikeengine.stat.NoSuchStatException;
import roguelikeengine.stat.Trait;

/**
 *
 * @author Greg
 */
public class SimpleItem extends Item {
    private ItemDefinition itemDef;
    private MaterialDefinition material;
    
    public SimpleItem(MaterialDefinition m, ItemDefinition itemDef) {
        super();
        this.itemDef = itemDef;
        material = m;
        addAllStats(material.viewStats());
        addAllStats(itemDef.viewStats());
        addAttacks(itemDef.getAttacks());
        for (Stat s : getStats().values()) {
            s.setContainer(this);
        }
        refactor();
    }

    /**
     * @return the itemDef
     */
    public ItemDefinition getItemDef() {
        return itemDef;
    }

    @Override
    public String getName() {
        StringBuilder sb = new StringBuilder();
        for (ItemMod i : mods) {
            if (!sb.toString().contains(i.getAdjective()))sb.append(i.getAdjective() + " ");
        }
        if (getItemDef().defaultMaterial() != getMaterial()) {
            sb.append(getMaterial().getName() + " " + getItemDef().getName(0));
        } else {sb.append(getItemDef().getName(0));}
        return sb.toString();
    }

    @Override
    public DisplayChar getSymbol() {
        return new DisplayChar(getItemDef().getSymbol(), 
                getMaterial().getColor());

    }

    /**
     * @return the material
     */
    public MaterialDefinition getMaterial() {
        return material;
    }

    @Override
    public String takeAttack(Attack a){
        String result = "";
        try {
            material.getDamageScript().run(a, this);
        } catch (NoSuchStatException ex) {
            Logger.getLogger(SimpleItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public void refactor() {
        clearStats();
        addAllStats(material.viewStats());
        addAllStats(itemDef.viewStats());
        for (Stat s : getStats().values()) {
            s.setContainer(this);
        }
        super.refactor();
    }

    @Override
    public Item copy() {
        SimpleItem ret = new SimpleItem(material, itemDef);
        for (Trait t : getAttacks()) {
            addAttack(t.Copy());
        }
        return ret;
    }

    @Override
    public void destroy() {
        System.out.println(getName() + " Destroyed!");
        Location location = getLocation();
        if (location instanceof AreaLocation) {
            AreaLocation areaLocation = (AreaLocation) location;
            areaLocation.getArea().removeItem(this);
        } else if (location instanceof ItemLocation) {
            ItemLocation itemLocation = (ItemLocation) location;
            itemLocation.getContainer().removePart(this);
        }
    }

    @Override
    public boolean containsPart(String s) {
        if (getName().contains(s)) return true;
        return false;
    }

    @Override
    public void use(RoguelikeInterface display, Body b) {
        itemDef.getUseScript().run(display, this, b);
    }
}
