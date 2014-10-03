/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roguelikeengine.item;

import roguelikeengine.display.RoguelikeInterface;
import roguelikeengine.largeobjects.Body;

/**
 *
 * @author greg
 */
public interface ItemScript {
    
    public void run(RoguelikeInterface display, Item i, Body b);
    
}
