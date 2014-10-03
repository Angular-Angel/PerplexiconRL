package roguelikeengine.stat;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author greg
 */
public class Trait extends StatContainer {
    
    private String name;
    
    public Trait(String name){
        this(name, new HashMap<String, Stat>());
    }
    
    public Trait(String name, HashMap<String, Stat> stats) {
        super(stats);
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public Trait Copy() {
        Trait ret = new Trait(name);
        for (String s : viewStats().keySet()) {
            try {
                ret.addStat(s, viewStat(s).copy());
            } catch (NoSuchStatException ex) {
                Logger.getLogger(Trait.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

}
