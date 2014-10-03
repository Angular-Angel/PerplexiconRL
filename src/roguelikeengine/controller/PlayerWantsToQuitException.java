/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.controller;

/**
 *
 * @author Greg
 */
public class PlayerWantsToQuitException extends Exception {
    
    public PlayerWantsToQuitException(String string, Throwable t) {
        super(string, t);
    }
    
    public PlayerWantsToQuitException(String string) {
        super(string);
    }
    
    public PlayerWantsToQuitException() {
        super();
    }
    
}
