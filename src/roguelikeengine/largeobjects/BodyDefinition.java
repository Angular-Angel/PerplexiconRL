package roguelikeengine.largeobjects;

import roguelikeengine.stat.StatContainer;
import roguelikeengine.stat.Stat;
import java.util.HashMap;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.item.CompositeItem;
import roguelikeengine.stat.*;

/**
 *
 * @author greg
 */
public class BodyDefinition extends StatContainer {
    private final String name;
    private final DisplayChar symbol;
    public CompositeItem bodyTemplate;
    private final BiologyScript bioScript;
    
    public BodyDefinition(String name, DisplayChar d, HashMap<String, Stat> stats, BiologyScript script) {
        super(stats);
        this.name = name;
        this.symbol = d;
        bodyTemplate = new CompositeItem(name, symbol);
        bioScript = script;
    }
    
    /**
     * @return the symbol
     */
    public DisplayChar getSymbol() {
        return symbol;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the bioScript
     */
    public BiologyScript getBioScript() {
        return bioScript;
    }
}
