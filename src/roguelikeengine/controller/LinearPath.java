/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.controller;

import roguelikeengine.area.Vector;
import roguelikeengine.area.AreaLocation;
import roguelikeengine.area.*;
import roguelikeengine.largeobjects.Body;

/**
 *
 * @author greg
 */
public class LinearPath {
    private Vector dir;
    private int error;
    private AreaLocation cur;
    int signX, signY;
    
    public LinearPath(Vector dir, AreaLocation cur) {
        this.dir = dir;
        signX = (dir.x > 0 ? 1 : -1);
        signY = (dir.y > 0 ? 1 : -1);
        if (dir.x == 0) signX = 0;
        if (dir.y == 0) signY = 0;
        dir.x = Math.abs(dir.x) << 1;
        dir.y = Math.abs(dir.y) << 1;
        error = 0;
        if (dir.x >= dir.y) { error = dir.y - (dir.x >> 1); }
        else { error = dir.x - (dir.y >> 1); }
        this.cur = new AreaLocation(cur);
    }
    
//    public Location getNext() {
//        Location ret = new Location(cur);
//        int error = this.error;
//        if (dir.x >= dir.y) {
//
//            if ((error >= 0) && (error > 0 || signX > 0)) {
//                error -= dir.x;
//                ret.move(0, signY);
//            }
//
//            error += dir.y;
//            ret.move(signX, 0);
//        } else {
//            if ((error >= 0) && (error > 0 || (signY > 0))) {
//                error -= dir.y;
//                ret.move(signX, 0);
//            }
//
//            error += dir.x;
//            ret.move(0, signY);
//        }
//        return ret;
//    }
//    
//    public Location step() {
////        int x0 = 0, y0 = 0, x1 = 0, y1 = 0;
////        float slope = (float)(y1-y0)/(x1-x0);
////        float whereweat = 0;
////        for (int x=x0; x<x1; x++)
////        {
////            whereweat = slope*x;
////            int y = (int)whereweat + y0; //should be int floor
////        }
////        
//        //Bresenham line
//        if (dir.x >= dir.y) {
//
//            if ((error >= 0) && (error > 0 || signX > 0)) {
//                error -= dir.x;
//                cur.move(0, signY);
//            }
//
//            error += dir.y;
//            cur.move(signX, 0);
//        } else {
//            if ((error >= 0) && (error > 0 || (signY > 0))) {
//                error -= dir.y;
//                cur.move(signX, 0);
//            }
//
//            error += dir.x;
//            cur.move(0, signY);
//        }
//        return cur;
//    }
    
    public void move(Body b) {
        AreaLocation ret = new AreaLocation(cur);
        int error = this.error;
        if (dir.x >= dir.y) {

            if ((error >= 0) && (error > 0 || signX > 0)) {
                error -= dir.x;
                ret.move(0, signY);
            }

            error += dir.y;
            ret.move(signX, 0);
        } else {
            if ((error >= 0) && (error > 0 || (signY > 0))) {
                error -= dir.y;
                ret.move(signX, 0);
            }

            error += dir.x;
            ret.move(0, signY);
        }
        if (b.moveTo(ret)) {
            cur = ret;
            this.error = error;
        }
    }
}
