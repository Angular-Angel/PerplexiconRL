/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.area;

import java.awt.*;
import java.util.HashMap;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.item.MaterialDefinition;
import roguelikeengine.stat.Stat;
import roguelikeengine.stat.StatContainer;

/**
 * This class represents a single tile of terrain.
 * @author greg
 */
public class TerrainDefinition extends StatContainer {
    private DisplayChar displayChar;
    private MaterialDefinition material;
    
    /**
     * The default constructor.
     */
    public TerrainDefinition() {
        this('%', Color.white, null, new HashMap<String, Stat>());
    }
    
    /**
     * The complicated constructor.
     * @param symbol The character to display on the screen.
     * @param color The color.
     * @param transparent Can this terrain be seen through?
     * @param passable Can this terrain be moved through?
     * @param mat the material this Terrain is made out of.
     */
    public TerrainDefinition(char symbol, Color color, MaterialDefinition mat, HashMap<String, Stat> stats) {
        this(new DisplayChar(symbol, color), mat, stats);
    }
    
    public TerrainDefinition(DisplayChar symbol, MaterialDefinition mat, HashMap<String, Stat> stats) {
        super(stats);
        displayChar = symbol;
        material = mat;
    }

    /**
     * @return the symbol
     */
    public char getChar() {
        return getDisplayChar().getSymbol();
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return getDisplayChar().getColor();
    }

    /**
     * @return the displayChar
     */
    public DisplayChar getDisplayChar() {
        return displayChar;
    }
    
    /**
     * @return the transparent
     */
    public boolean isTransparent() {
        return hasStat("Transparent");
    }

    /**
     * @return the passable
     */
    public boolean isPassable() {
        return hasStat("Passable");
    }

    /**
     * @return the material
     */
    public MaterialDefinition getMaterial() {
        return material;
    }
}
