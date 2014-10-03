/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.largeobjects;

import roguelikeengine.controller.PlayerWantsToQuitException;



/**
 *
 * @author Greg
 */
public interface Actor {
    
    public boolean act() throws PlayerWantsToQuitException;
    
    public void addMoves();
    
}
