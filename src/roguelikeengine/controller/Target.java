/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.controller;

import roguelikeengine.largeobjects.Body;

/**
 *
 * @author greg
 */
public class Target implements AIGoal{
    private Body target;
    private int distance;
    
    public Target(Body b, int d) {
        target = b;
        distance = d;
    }

    /**
     * @return the distance
     */
    public int getDistance() {
        return distance;
    }

    @Override
    public boolean takeAction(Body b) {
        
        return b.moveTo(target.getMap().lowestAdjacent(b.getLocation()));
    }
}
