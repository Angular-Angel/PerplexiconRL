/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.controller;

import roguelikeengine.area.LocationLine;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import roguelikeengine.area.*;
import roguelikeengine.largeobjects.Body;
import roguelikeengine.stat.NoSuchStatException;

/**
 *
 * @author greg
 */
public class EnemyAI extends Controller{

    private AIGoal target;
    
    public EnemyAI(Body body) {
        super(body);
        target = null;
    }
    
    @Override
    public boolean isPlayer() {
        return false;
    }
    
    @Override
    public boolean act() {
        if (!getBody().isAlive()) 
           return false;
        getBody().step();
        addMoves();
        view();
        if (target != null) {move();}
        else {getBody().addMoves(-100);}
        return true;
    }
    
    public void view() {
        try {
            int sightRange = (int) getBody().getDef().getScore("Sight Range");
            int x = getBody().getLocation().getX();
            int y = getBody().getLocation().getY();
            ArrayList<Target> targets = new ArrayList<Target>();
            for (int i = 0; i <= sightRange*2; i++) {
                targets.addAll(visionLine(new LocationLine(getBody().getLocation(), 
                           x + sightRange, y - sightRange + i, true, false)));
                targets.addAll(visionLine(new LocationLine(getBody().getLocation(), 
                           x - sightRange, y - sightRange + i, true, false)));
                targets.addAll(visionLine(new LocationLine(getBody().getLocation(), 
                           x - sightRange + i, y + sightRange, true, false)));
                targets.addAll(visionLine(new LocationLine(getBody().getLocation(),
                           x - sightRange + i, y - sightRange, true, false)));
            }
            pickTarget(targets);
        } catch (NoSuchStatException ex) {
            Logger.getLogger(EnemyAI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Target> visionLine(LocationLine line) {
        ArrayList<Target> targets = new ArrayList<Target>();
        Body b;
        for (int i = 0; i < line.getLength(); i++) {
            b = line.getLocation(i).getArea().bodyAt(i, i);
            if (b != null && b != getBody()) {
                targets.add(new Target(b, i));
            }
        }
        return targets;
    }
    
    public void pickTarget(ArrayList<Target> targets) {
        if (targets.size() <= 0)
            return;
        int mindist = targets.get(0).getDistance();
        target = targets.get(0);
        for (int i = 1; i < targets.size(); i++) {
            if (mindist > targets.get(i).getDistance()) {
                mindist = targets.get(i).getDistance();
                target = targets.get(i);
            }
        }
    }
    
    public void move() {
        
        if (!target.takeAction(getBody())) {
            getBody().addMoves(-100);
        }
    }
    
    
}
