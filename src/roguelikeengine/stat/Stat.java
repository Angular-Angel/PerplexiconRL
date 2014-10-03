/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.stat;

/**
 *
 * @author Greg
 */
public interface Stat {
    
    public float getScore();
    
    public void setContainer(StatContainer i);
    
    public void refactor() throws NoSuchStatException;
    
    public void modify(float change);
    
    public void modifyBase(float change);
    
    public Stat copy();
    
}
