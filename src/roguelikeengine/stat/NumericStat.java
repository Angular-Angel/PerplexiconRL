/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.stat;

import roguelikeengine.item.Item;

/**
 *
 * @author Greg
 */
public class NumericStat implements Stat {
    
    private float baseScore;
    private float curScore;

    public NumericStat(float score) {
        this.baseScore = score;
        this.curScore = score;
    }
    
    @Override
    public float getScore() {
        return curScore;
    }

    @Override
    public void setContainer(StatContainer i) {}

    @Override
    public void refactor() throws NoSuchStatException {
        curScore = baseScore;
    }
    
    public void modify(float change) {
        curScore += change;
    }

    @Override
    public void modifyBase(float change) {
        baseScore += change;
    }

    @Override
    public Stat copy() {
        NumericStat ret = new NumericStat(baseScore);
        ret.modify(curScore - baseScore);
        return ret;
    }
    
}
