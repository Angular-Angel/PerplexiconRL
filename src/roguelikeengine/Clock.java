/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine;

import roguelikeengine.largeobjects.Actor;
import roguelikeengine.controller.PlayerWantsToQuitException;
import java.util.ArrayList;
import roguelikeengine.largeobjects.*;

/**
 *
 * @author greg
 */
public class Clock {
    private ArrayList<Actor> actors;
    
    public Clock() {
        actors = new ArrayList<Actor>();
    }
    
    public void addActor(Actor actor) {
        actors.add(actor);
    }
    
    public void removeActor(Actor actor) {
        actors.remove(actor);
    }
    
    public void play() {
        ArrayList<Actor> deadActors = new ArrayList<>();
        try {
            while (actors.size() > 0) {
                for (Actor a : actors) {
                    boolean alive = a.act();
                    if (!alive) {
                     deadActors.add(a);
                    }
                }
                for(Actor a : deadActors) {
                    actors.remove(a);
                }
                deadActors.clear();
            }
        } catch(PlayerWantsToQuitException ex) {
        }
    }
}
