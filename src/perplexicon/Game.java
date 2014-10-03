/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package perplexicon;

import roguelikeengine.largeobjects.Body;

/**
 *
 * @author greg
 */
public interface Game {
    
    public void BodyAttack(Body attacker, Body defender);
    
    public void start();
    
}
