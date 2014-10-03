/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.stat;

import java.util.logging.Level;
import java.util.logging.Logger;
import roguelikeengine.largeobjects.Body;

/**
 *
 * @author Greg
 */
public class DerivedStat implements Stat{
    private StatContainer i;
    private String s1, s2;
    private float score;
    private char type;
    
    public DerivedStat(StatContainer i, String s1, String s2) {
        this(i, s1, s2, '*');
    }
    
    public DerivedStat(StatContainer i, String s1, String s2, char type) {
        this.s1 = s1;
        this.s2 = s2;
        this.type = type;
        this.i = i;
    }
    
    public DerivedStat(String s1, String s2) {
        this(s1, s2, '*');
    }
    
    public DerivedStat(String s1, String s2, char type) {
        this.s1 = s1;
        this.s2 = s2;
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
                case '*': score = i.getScore(s1) * i.getScore(s2);
                    break;
                case '/': score = i.getScore(s1) / i.getScore(s2);
                    break;
                case '+': score = i.getScore(s1) + i.getScore(s2);
                    break;
                case '-': score = i.getScore(s1) - i.getScore(s2);
                    break;
                default: System.out.println("Derived Stat: operator not recognized");
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
        return new DerivedStat(s1, s2, type);
    }
    
}
