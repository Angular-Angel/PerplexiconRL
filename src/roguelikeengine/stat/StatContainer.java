/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import roguelikeengine.item.CompositeItem;

/**
 *
 * @author Greg
 */
public abstract class StatContainer {
    
    private HashMap<String, Stat> stats;
    private ArrayList<Stat> statOrder;
    
    public StatContainer() {
        this(new HashMap<String, Stat>());
    }
    
    public StatContainer(HashMap<String, Stat> stats) {
        this.stats = new HashMap<>();
        statOrder = new ArrayList<>();
        this.stats.putAll(stats);
        for (Stat s : stats.values()) {
            statOrder.add(s);
        }
    }
    
    public Stat viewStat(String name) throws NoSuchStatException{
        if (stats.containsKey(name)) {return stats.get(name).copy();}
        else {throw new NoSuchStatException("Stat: " + name);}
    }
    
    protected Stat getStat(String name) throws NoSuchStatException{
        if (stats.containsKey(name)) {return stats.get(name);}
        else {throw new NoSuchStatException("Stat: " + name);}
    }
    
    public float getScore(String name) throws NoSuchStatException{
        if (stats.containsKey(name)) {return stats.get(name).getScore();}
        else {throw new NoSuchStatException("Stat: " + name);}
    }
    
    public void addStat(String name, Stat stat) {
        stat.setContainer(this);
        stats.put(name, stat);
        statOrder.add(stat);
    }
    
    public void addAllStats(HashMap<String, Stat> newStats) {
        for (String s : newStats.keySet()) {
            addStat(s, newStats.get(s));
        }
    }
    
    public HashMap<String, Stat> viewStats() {
        HashMap<String, Stat> ret = new HashMap<>();
        for (String s : stats.keySet()) {
            ret.put(s, stats.get(s).copy());
        }
        return ret;
    }
    
    protected HashMap<String, Stat> getStats() {
        return stats;
    }
    
    public boolean hasStat(String s) {
        return stats.containsKey(s);
    }
    
    public void refactor() {
        for (Stat s: statOrder) {
            try {
                s.setContainer(this);
                s.refactor();
            } catch (NoSuchStatException ex) {
                Logger.getLogger(StatContainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    protected void clearStats() {
        stats.clear();
        statOrder.clear();
    }
}
