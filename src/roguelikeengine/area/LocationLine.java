package roguelikeengine.area;

import java.util.ArrayList;

/**
 * This class generates and stores a line of locations.
 * @author greg
 */
public class LocationLine {
    private ArrayList<Location> locations;
    private int[] x;
    private int[] y;
    private Vector dir;
    
    /**
     * The constructor for a LocationLine. Finds every location between the 
     * start location and x,y on the same area.
     * @param start The location to start at.
     * @param x2 The X coordinate of the end.
     * @param y2 The Y coordinate of the end.
     */
    
    public LocationLine(AreaLocation start, int x2, int y2, boolean stopForOpaque, boolean stopForImpassable) {
        this(start, x2 - start.getX(), y2 - start.getY(), 
                Math.max(Math.abs(x2 - start.getX()), 
                Math.abs(y2 - start.getY())), stopForOpaque, stopForImpassable);
    }
        
    public LocationLine(AreaLocation start, int dx, int dy, int length, boolean stopForOpaque, boolean stopForImpassable) {
        
        dir = new Vector(dx, dy);
        locations = new ArrayList<Location>();
        addLocation(start);
        int i = 0;
        AreaLocation cur = new AreaLocation(start);
        
        int signX = (dx > 0 ? 1 : -1);
        int signY = (dy > 0 ? 1 : -1);
        if (dx == 0) signX =0;
        if (dy == 0) signY =0;
        int oSignX = signX;
        int oSignY = signY;
        dx = Math.abs(dx) << 1;
        dy = Math.abs(dy) << 1;
        x = new int[length + 1];
        y = new int[length + 1];
        x[0] = 0;
        y[0] = 0;
        
        int odx, ody, error, oerror;
        if (dx >= dy) { error = dy - (dx >> 1); }
        else { error = dx - (dy >> 1); }
        odx = dx; ody = dy; oerror = error;
        
        while(i != length) {
            i++;
            
            if (dx >= dy) {
                
                if ((error >= 0) && (error > 0 || signX > 0)) {
                    error -= dx;
                    cur.move(0, signY);
                }
                
                error += dy;
                cur.move(signX, 0);
            } else {
                if ((error >= 0) && (error > 0 || (signY > 0))) {
                    error -= dy;
                    cur.move(signX, 0);
                }
                
                error += dx;
                cur.move(0, signY);
            }
            if (cur.getTerrain() == null) {
                LocalArea.BorderArea b = cur.getArea().getBorderArea(cur.getX(), cur.getY());
                if (b != null) {
                    int temp;
                    switch(b.getRotation()) {
                        case degree0: break;
                        case degree90: 
                            temp = signY;
                            signY = -signX;
                            signX = temp;
                            temp = dy;
                            dy = dx;
                            dx = temp;
                            break;
                        case degree180:
                            signX *= -1;
                            signY *= -1;
                            break;
                        case degree270:
                            temp = -signY;
                            signY = signX;
                            signX = temp;
                            temp = dy;
                            dy = dx;
                            dx = temp;
                            break;
                    }
                    if (b.isXMirrored()) {
                        signX*= -1;
                    }

                    if (b.isYMirrored()) {
                        signY*= -1;
                    }
                }
            }

            addLocation(new AreaLocation(cur));
            
            if (stopForOpaque && !cur.isTransparent()) {
                length = i + 1;
            }
            
            if (odx >= ody) {
                
                if ((oerror >= 0) && (oerror > 0 || oSignX > 0)) {
                    oerror -= odx;
                    setY(getY(i - 1) + oSignY, i);
                }
                else setY(getY(i - 1), i);
                
                oerror += ody;
                setX(getX(i - 1) + oSignX, i);
            } else {
                if ((oerror >= 0) && (oerror > 0 || (oSignY > 0))) {
                    oerror -= ody;
                    setX(getX(i - 1) + oSignX, i);
                }
                else setX(getX(i - 1), i);
                
                oerror += odx;
                setY(getY(i - 1) + oSignY, i);
            }
            
            if (stopForOpaque && !cur.isTransparent()) {
                break;
            }
            
            if (stopForImpassable && !cur.isPassable()) {
                break;
            }
        } 
        
    }
    
    /**
     * @return the length of this line.
     */
    
    public int getLength() {
        return locations.size();
    }
    
    /**
     * Adds the given location onto the end of the line.
     * @param l The given Location.
     */
    
    public void addLocation(Location l) {
        locations.add(l);
    }
    
    /**
     * gets the Location at index i.
     * @param i The index to check.
     * @return The location at i.
     */
    
    public Location getLocation(int i) {
        return locations.get(i);
    }

    /**
     * @return the x
     */
    public int getX(int i) {
        if (i < 0 || i >= locations.size())
            System.out.println("Oops!");
        return x[i];
    }

    /**
     * @param x the x to set
     */
    public void setX(int x, int i) {
        this.x[i] = x;
    }

    /**
     * @return the y
     */
    public int getY(int i) {
        if (i < 0 || i >= locations.size())
            System.out.println("Oops!");
        return y[i];
    }

    /**
     * @param y the y to set
     */
    public void setY(int y, int i) {
        this.y[i] = y;
    }
    
    public Vector getDirection() {
        return dir;
    }
}
