/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.controller;

import roguelikeengine.area.LocalArea;
import roguelikeengine.area.Location;
import roguelikeengine.area.AreaLocation;
import java.util.*;
import roguelikeengine.area.*;

/**
 *
 * @author Greg
 */
public class DijkstraMap {
    private HashMap<LocalArea, AreaMap> areas;
    private boolean blockedByImpassable;
    private LinkedList<AreaLocation> nextCells;
    private LinkedList<Integer> nextCellCounts;
    
    private class AreaMap {
        public LocalArea area;
        
        public int[][] cells;
        
        public AreaMap(LocalArea l) {
            area = l;
            cells = new int[l.getxDist()][l.getyDist()];
            clean();
            nextCellCounts = new LinkedList<Integer>();
            nextCells = new LinkedList<>();
        }
        
        public void clean() {
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    cells[i][j] = -1;
                }
            }
        }
        
    }
    
    public DijkstraMap(LocalArea... areaList) {
        blockedByImpassable = true;
        areas = new HashMap<LocalArea, AreaMap>();
        for (LocalArea l : areaList) {
            areas.put(l, new AreaMap(l));
        }
    }
    
    public void fill(AreaLocation start) {
        for (Map.Entry<LocalArea, AreaMap> a: areas.entrySet()) {
            a.getValue().clean();
        }
        nextCellCounts.add(0);
        nextCells.add(start);
        while (nextCellCounts.size() > 0) {
            fillCell(nextCells.getFirst(), nextCellCounts.getFirst());
            nextCellCounts.remove();
            nextCells.remove();
        }
        
    }
    
    private void fillCell(AreaLocation l, int count) {
        
        if (l == null) {return;}
        if (blockedByImpassable && !l.getTerrain().isPassable()) {return;}
        
        count++;
        AreaMap a = areas.get(l.getArea());
        if (a.cells[l.getX()][l.getY()] == -1 || 
            a.cells[l.getX()][l.getY()] > count) {
            a.cells[l.getX()][l.getY()] = count;
            //fillCell(new Location(l, -1, -1), count);
            nextCells.add(new AreaLocation(l, 0, -1));
            nextCellCounts.add(count);
            //fillCell(new Location(l, 1, -1), count);
            nextCells.add(new AreaLocation(l, -1, 0));
            nextCellCounts.add(count);
            nextCells.add(new AreaLocation(l, 1, 0));
            nextCellCounts.add(count);
            //fillCell(new Location(l, -1, 1), count);
            nextCells.add(new AreaLocation(l, 0, 1));
            nextCellCounts.add(count);
            //fillCell(new Location(l, 1, 1), count);
        }
    }
    
    public int getValue(Location l) {
        if (l == null || !areas.containsKey(l.getArea())) {return -1;}
        AreaMap a = areas.get(l.getArea());
        return a.cells[l.getX()][l.getY()];
    }
    
    public AreaLocation lowestAdjacent(AreaLocation l) {
        AreaLocation cur, ret = new AreaLocation(l);
            cur = new AreaLocation(l, 0, -1);
            if (getValue(cur) >0 && getValue(cur) < getValue(ret)) {ret = cur;}
            cur = new AreaLocation(l, 0, 1);
            if (getValue(cur) >0 && getValue(cur) < getValue(ret)) {ret = cur;}
            cur = new AreaLocation(l, -1, 0);
            if (getValue(cur) >0 && getValue(cur) < getValue(ret)) {ret = cur;}
            cur = new AreaLocation(l, 1, 0);
            if (getValue(cur) >0 && getValue(cur) < getValue(ret)) {ret = cur;}
        return ret;
    }
    
}
