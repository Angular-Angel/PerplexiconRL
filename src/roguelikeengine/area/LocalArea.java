/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.area;

import java.util.ArrayList;
import roguelikeengine.display.Rotation;
import roguelikeengine.item.Item;
import roguelikeengine.item.ItemOnGround;
import roguelikeengine.largeobjects.Body;
import roguelikeengine.largeobjects.Entity;


/**
 *
 * @author greg
 */

public class LocalArea {
    private TerrainDefinition[][] terrain;
    private int xDist;
    private int yDist;
    private ArrayList<BorderArea> borders;
    private ArrayList<Entity> entities;
    private String debugName;
    
    /**
     * 
     * @param x the x coordinate of the terrain to set.
     * @param y the y coordinate of the terrain to set.
     * @param terrain the terrain to set.
     */
    public void setTerrain(int x, int y, TerrainDefinition terrain) {
        this.terrain[x][y] = terrain;
    }
    
    /**
     * 
     * @param x the x coordinate of the terrain to return.
     * @param y the y coordinate of the terrain to return.
     * @return the terrain at x and y.
     */
    public TerrainDefinition getTerrain(int x, int y) {
        if (x >= 0 && x < getxDist() && y >= 0 && y < getyDist())
        return terrain[x][y];
        else return null;
    }
    
    /**
     * Default Constructor
     */
    
    public LocalArea() {
        this(20, 20, new TerrainDefinition(), "Defaut Name");
    }
    
    /**
     * Constructor
     * @param xDist How many columns in this area.
     * @param yDist How many rows in this area.
     * @param t The default terrain.
     */
    
    public LocalArea(int xDist, int yDist, TerrainDefinition t, String name) {
        this.terrain = new TerrainDefinition[xDist][yDist];
        this.xDist = xDist;
        this.yDist = yDist;
        for (int y = 0; y < yDist; y++) {
            for (int x = 0; x < xDist; x++) {
                this.terrain[x][y] = t;
            }
        }
        borders = new ArrayList<BorderArea>();
        entities = new ArrayList<Entity>();
        debugName = name;
    }

    /**
     * @return the xDist
     */
    public int getxDist() {
        return xDist;
    }

    /**
     * @param xDist the xDist to set
     */
    public void setxDist(int xDist) {
        this.xDist = xDist;
    }

    /**
     * @return the number of rows
     */
    public int getyDist() {
        return yDist;
    }

    /**
     * @param yDist the number of rows
     */
    public void setyDist(int yDist) {
        this.yDist = yDist;
    }
    
    /**
     * Adds the body to the list of bodies in the localArea.
     * @param body The body t add.
     */
    
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    
    /**
     * Removes the body from the list of bodies in the localArea.
     * @param body the body to remove.
     */
    
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
    
    public void removeItem(Item i) {
        for (Entity e : entities) {
            if (e instanceof ItemOnGround && ((ItemOnGround) e).getItem() == i)
                removeEntity(e);
        }
    }
    
    /**
     * if there is a body at x/y, return that body.
     * @param x the x coordinate to check.
     * @param y the y coordinate to check.
     * @return the body if there is one, otherwise null.
     */
    
    public Body bodyAt(int x, int y) {
        for (Entity e : entities) {
            try {
            if ((e instanceof Body) && e.occupies(new AreaLocation(this, x, y)))
                return (Body) e;
            } catch (IllegalArgumentException ex) {
                return null;
            }
        }
        return null;
    }
    
    public ArrayList<ItemOnGround> itemsAt(int x, int y) {
        ArrayList<ItemOnGround> ret = new ArrayList<ItemOnGround>();
        Location l = new AreaLocation(this, x, y);
        for (Entity e : entities) {
            if ((e instanceof ItemOnGround) && e.occupies(l))
                ret.add((ItemOnGround) e);
        }
        return ret;
    }
    
    /**
     * Adds a new BorderArea. uses the provided arguments for the constructor.
     * @param area The area to be in the border.
     * @param x The x offset for the area
     * @param y The y offset for the area.
     */
    
    public void addBorder(LocalArea area, int x, int y) {
            addBorder(area, x, y, Rotation.degree0, false, false);
    }
    
    /**
     * Adds a new BorderArea. uses the provided arguments for the constructor.
     * @param area The area to be in the border.
     * @param x The x offset for the area
     * @param y The y offset for the area.
     * @param r The rotation of the bordering area.
     * @param xm Whether or not the bordering area is mirrored on the x axis.
     * @param ym Whether or not the bordering area is mirrored on the y axis.
     */
    
    public void addBorder(LocalArea area, int x, int y, Rotation r, boolean xm, boolean  ym) {
            borders.add(new BorderArea(area, x, y, r, xm, ym));
    }
    
    /**
     * returns the BorderArea associated with the x/y coordinate, if one exists.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return The BorderArea at x/y, or null if there is none.
     */
    
    public BorderArea getBorderArea(int x, int y) {
        if (getTerrain(x, y) != null) return null;
        int getx = 0, gety = 0;
        BorderArea ret;
        for (BorderArea b : borders) {
            if (b.contains(x, y))
            return b;
        }
        return null;
    }
    
    /**
     * Returns the location at x/y. If that location is on a bordering LocalArea, 
     * returns the location on that area.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return The Location at x/y, or null if there is none.
     */
    
    public Location getLocation(int x, int y) {
        return new AreaLocation(this, x, y);
    }
        
    boolean  refactor(AreaLocation l) {
        if (getTerrain(l.getX(), l.getY()) != null) {return false;}
        LocalArea area = null;
        int getx = 0, gety = 0, x = l.getX(), y = l.getY();
        for (BorderArea b : borders) {
            if (b.contains(x, y)) {
                switch (b.getRotation()) {
                    case degree0:
                        if (b.isXMirrored())
                        getx = b.getArea().getxDist() - 1 - (x - b.getX());
                        else
                        getx = x - b.getX();
                        if (b.isYMirrored())
                        gety = b.getArea().getyDist() - 1 - (y - b.getY());
                        else
                        gety = y - b.getY();
                        area = b.getArea();
                        break;
                    case degree90:
                        if (b.isXMirrored())
                        getx = b.getArea().getyDist() - 1 - (y - b.getY());
                        else
                        getx = y - b.getY();
                        if (b.isYMirrored())
                        gety = x - b.getX();
                        else
                        gety = b.getArea().getxDist() - 1 - (x - b.getX());
                        area = b.getArea();
                        break;
                    case degree180:
                        if (b.isXMirrored())
                        getx = x - b.getX();
                        else
                        getx = b.getArea().getxDist() - 1 - (x - b.getX());
                        if (b.isYMirrored())
                        gety = y - b.getY();
                        else
                        gety = b.getArea().getyDist() - 1 - (y - b.getY());
                        area = b.getArea();
                        break;
                    case degree270:
                        if (b.isXMirrored())
                        getx = y - b.getY();
                        else
                        getx = b.getArea().getyDist() - 1 - (y - b.getY());
                        if (b.isYMirrored())
                        gety = b.getArea().getxDist() - 1 - (x - b.getX());
                        else
                        gety = x - b.getX();
                        area = b.getArea();
                        break;

                }
            }
        }
        if (area != null) {
            l.setLocation(area, getx, gety);
            if (l.getTerrain() == null) throw new InternalError(l.getString());
            return false;
        } else {
            return true;
        }
    }

    /**
     * @return the debugName
     */
    public String getDebugName() {
        return debugName;
    }
    
    /**
     * Contains the information for an area that borders this area. 
     */
    
    public class BorderArea {
        private LocalArea area;
        private int x, y;
        private Rotation rotation;
        private boolean xMirrored;
        private boolean yMirrored;
        
        /**
         * Default Constructor.
         */
        
        private BorderArea() {}
        
        /**
         * Simplest constructor, for a single are that lies directly on top of 
         * this one with no transformations or offsets.
         * @param area The area that borders this one.
         */
        
        public BorderArea(LocalArea area) {
            this(area, 0, 0);
        }
        
        /**
         * Constructor for a border area that is offset.
         * @param area The area that borders this one.
         * @param x The x offset.
         * @param y The Y offset.
         */
        
        public BorderArea(LocalArea area, int x, int y) {
            this(area, x, y, Rotation.degree0, false, false);
        }
        
        /**
         * Constructor for an area that is offset and transformed.
         * @param area The area that borders this one.
         * @param x The x offset.
         * @param y The Y offset.
         * @param rotation The rotation of the area.
         * @param yMirrored Whether the BorderArea is reversed across the x axis
         * @param xMirrored Whether the BorderArea is reversed across the y axis
         */
        
        public BorderArea(LocalArea area, int x, int y, Rotation rotation, 
                          boolean yMirrored, boolean xMirrored) {
            this.area = area;
            this.x = x;
            this.y = y;
            setRotation(rotation);
            setXMirrored(xMirrored);
            setYMirrored(yMirrored);
            
        }
        
        /**
         * Checks to see in the given coordinates are contained within this 
         * BorderArea.
         * @param x The x coordinate to check.
         * @param y The y coordinate to check.
         * @return 
         */
        
        public boolean contains(int x, int y) {
            int getx = 0, gety = 0;
            switch (getRotation()) {
                case degree0:
                    if (isXMirrored())
                    getx = getArea().getxDist() - 1 - (x - getX());
                    else
                    getx = x - getX();
                    if (isYMirrored())
                    gety = getArea().getyDist() - 1 - (y - getY());
                    else
                    gety = y - getY();
                    break;
                case degree90:
                    if (isXMirrored())
                    getx = getArea().getyDist() - 1 - (y - getY());
                    else
                    getx = y - getY();
                    if (isYMirrored())
                    gety = x - getX();
                    else
                    gety = getArea().getxDist() - 1 - (x - getX());
                    break;
                case degree180:
                    if (isXMirrored())
                    getx = x - getX();
                    else
                    getx = getArea().getxDist() - 1 - (x - getX());
                    if (isYMirrored())
                    gety = y - getY();
                    else
                    gety = getArea().getyDist() - 1 - (y - getY());
                    break;
                case degree270:
                    if (isXMirrored())
                    getx = y - getY();
                    else
                    getx = getArea().getyDist() - 1 - (y - getY());
                    if (isYMirrored())
                    gety = getArea().getxDist() - 1 - (x - getX());
                    else
                    gety = x - getX();
                    break;
            }
            
            return (getArea().getTerrain(getx, gety) != null);
        }
    
        /**
         * @return the area
         */
        public LocalArea getArea() {
            return area;
        }

        /**
         * @param area the area to set
         */
        public void setArea(LocalArea area) {
            this.area = area;
        }

        /**
         * @return the x
         */
        public int getX() {
            return x;
        }

        /**
         * @param x the x to set
         */
        public void setX(int x) {
            this.x = x;
        }

        /**
         * @return the y
         */
        public int getY() {
            return y;
        }

        /**
         * @param y the y to set
         */
        public void setY(int y) {
            this.y = y;
        }

        /**
         * @return the rotation
         */
        public Rotation getRotation() {
            return rotation;
        }

        /**
         * @param rotation the rotation to set
         */
        public void setRotation(Rotation rotation) {
            this.rotation = rotation;
        }

        /**
         * @return the xMirrored
         */
        public boolean isXMirrored() {
            return xMirrored;
        }

        /**
         * @param xMirrored the xMirrored to set
         */
        public void setXMirrored(boolean xMirrored) {
            this.xMirrored = xMirrored;
        }

        /**
         * @return the yMirrored
         */
        public boolean isYMirrored() {
            return yMirrored;
        }

        /**
         * @param yMirrored the yMirrored to set
         */
        public void setYMirrored(boolean yMirrored) {
            this.yMirrored = yMirrored;
        }
        
    }
}
