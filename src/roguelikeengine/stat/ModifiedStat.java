package roguelikeengine.stat;

import java.util.logging.Level;
import java.util.logging.Logger;
import roguelikeengine.largeobjects.Body;

/**
 *
 * @author greg
 */
public class ModifiedStat implements Stat{
    private StatContainer i;
    private String s1;
    private float score, modifier;
    private char type;
    
    public ModifiedStat(StatContainer i, String stat, float mod) {
        this(i, stat, mod, '*');
    }
    
    public ModifiedStat(StatContainer i, String stat, float mod, char type) {
        this.i = i;
        s1 = stat;
        modifier = mod;
        this.type = type;
    }
    
    public ModifiedStat(String s1, float mod) {
        this(s1, mod, '*');
    }
    
    public ModifiedStat(String s1, float mod, char type) {
        this.s1 = s1;
        modifier = mod;
        this.type = type;
    }
    
    @Override
    public float getScore() {
        return score;
    }

    @Override
    public void setContainer(StatContainer i) {
        this.i = i;
    }

    @Override
    public void refactor() {
        try {
            switch(type) {
                case '*': score = i.getScore(s1) * modifier;
                    break;
                case '/': score = i.getScore(s1) / modifier;
                    break;
                case '+': score = i.getScore(s1) + modifier;
                    break;
                case '-': score = i.getScore(s1) - modifier;
                    break;
            }
        } catch(NoSuchStatException ex) {
            Logger.getLogger(Body.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modify(float change) {
        score += change;
    }

    @Override
    public void modifyBase(float change) {}

    @Override
    public Stat copy() {
        return new ModifiedStat(s1, modifier, type);
    }
}
