/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import roguelikeengine.stat.StatContainer;
import roguelikeengine.stat.Stat;
import java.awt.Color;
import java.util.HashMap;
import roguelikeengine.stat.*;

/**
 *
 * @author Greg
 */
public class MaterialDefinition extends StatContainer {
    private String name;
    private Color color;
    private DamageScript damageScript;
    

    public MaterialDefinition(String name, Color c, DamageScript script) {
        this(name, c, new HashMap<String, Stat>(), script);
    }
    
    public MaterialDefinition(String name, Color c, 
            HashMap<String, Stat> stats, DamageScript script) {
        super(stats);
        this.name = name;
        this.color = c;
        damageScript = script;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the damageScript
     */
    public DamageScript getDamageScript() {
        return damageScript;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }
}
