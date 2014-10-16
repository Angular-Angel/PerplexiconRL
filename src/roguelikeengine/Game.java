/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roguelikeengine;

import java.util.Random;
import roguelikeengine.Registry;
import roguelikeengine.display.RoguelikeInterface;
import roguelikeengine.largeobjects.Body;

/**
 *
 * @author greg
 */
public abstract class Game {
    
    public RoguelikeInterface display;
    public Registry registry;
    public Random random;
    
    public abstract void BodyAttack(Body attacker, Body defender);
    
    public abstract void start();
    
}
