package roguelikeengine.display;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 *
 * @author greg
 */
public class DisplayChar {
    private char symbol;
    private Color color;
    private Color bgColor;
    
    public DisplayChar(char symbol, Color color) {
        this(symbol, color, Color.black);
    }
    
    /**
     * Constructor
     * @param symbol The character 
     * @param color The Color
     */
    public DisplayChar(char symbol, Color color, Color bgColor){
        setSymbol(symbol);
        setColor(color);
        setBgColor(bgColor);
    }

    /**
     * @return the symbol
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    public void draw(Graphics g, FontMetrics metrics, int x, int y) {
        if(getBgColor() != Color.black) {
            g.setColor(getBgColor());
            g.fillRect(x * metrics.getMaxAdvance() + 2, y * metrics.getHeight() + 2,
                      (x + 1) * metrics.getMaxAdvance(), (y +1) * metrics.getHeight());
        }
        String c = "" ;
        g.setColor(getColor());
        c = "";
        c += getSymbol();
        g.drawString(c, x * metrics.getMaxAdvance() + 2, (y +1) * metrics.getHeight());
    }

    /**
     * @return the bgColor
     */
    public Color getBgColor() {
        return bgColor;
    }

    /**
     * @param bgColor the bgColor to set
     */
    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }
}
