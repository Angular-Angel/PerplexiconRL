/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.controller;

import roguelikeengine.largeobjects.Body;
import roguelikeengine.area.*;

/**
 *
 * @author greg
 */
public interface AIGoal {
    public boolean takeAction(Body b);
}
