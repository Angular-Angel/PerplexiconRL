/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.stat;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import roguelikeengine.item.CompositeItem;
import roguelikeengine.item.Item;
import roguelikeengine.largeobjects.Body;

/**
 *
 * @author Greg
 */
public class AggregateStat implements Stat {
    private CompositeItem i;
    private String s1;
    private float score;
    private int type;
    public static final int sum = 1, lowest = 2, highest = 3, average = 4;
    
    public AggregateStat(String s1, int type) {
        this.s1 = s1;
        this.type = type;
    }
    
    public AggregateStat(CompositeItem i, String s1, int type) {
        this.i = i;
        this.s1 = s1;
        this.type = type;
    }

    @Override
    public float getScore() {
        return score;
    }

    @Override
    public void setContainer(StatContainer sc) {
        if (sc instanceof CompositeItem) {
            this.i = (CompositeItem) sc;
        } else System.out.println("AggregateStat needs CompositeItem for container.");
    }

    @Override
    public void refactor() throws NoSuchStatException {
        score = 0;
        int num = 0;
        try {
            ArrayList<Item> parts = i.getParts();
            if (type == lowest) score = parts.get(0).getScore(s1);
            for (Item i : parts) {
                switch(type) {
                    case average: 
                        num++;
                    case sum: score += i.getScore(s1);
                        break;
                    case highest: 
                        if (score < i.getScore(s1)) {score = i.getScore(s1);}
                        break;
                    case lowest:
                        if (score > i.getScore(s1)) {score = i.getScore(s1);}
                        break;
                }
            }
            if (type == average) score /= num;
        } catch(NoSuchStatException | NullPointerException ex) {
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
        return new AggregateStat(i, s1, type);
    }
    
    
}
