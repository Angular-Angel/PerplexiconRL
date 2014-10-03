/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roguelikeengine.largeobjects;

/**
 *
 * @author greg
 */
public interface BiologyScript {
    
    public boolean isAlive(Body b);
    
    public void step(Body b);
    
    public void beAttacked(Body b, Attack a);

}
