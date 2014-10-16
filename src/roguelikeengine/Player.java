package roguelikeengine;

import roguelikeengine.display.Rotation;
import roguelikeengine.display.RoguelikeInterface;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.stat.NoSuchStatException;
import roguelikeengine.controller.Controller;
import roguelikeengine.item.SimpleItem;
import roguelikeengine.item.Item;
import roguelikeengine.item.ItemOnGround;
import roguelikeengine.area.LocalArea;
import roguelikeengine.area.Location;
import roguelikeengine.area.LocationLine;
import roguelikeengine.area.AreaLocation;
import roguelikeengine.area.NonexistentLocationException;
import roguelikeengine.controller.PlayerWantsToQuitException;
import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import perplexicon.Perplexicon;
import roguelikeengine.largeobjects.*;

/**
 * This class is for the object that manages the interface between the player 
 * and the character that they control.
 * @author greg
 */
public class Player extends Controller {
    
    private Perplexicon perplexicon;
    private Rotation rot;
    private boolean xMirrored;
    private boolean yMirrored;
    
    public Player(Body body, Perplexicon game) {
        super(body);
        
        rot = Rotation.degree0;
        xMirrored = false;
        yMirrored = false;
        perplexicon = game;
    }
    
    /**
     * writes what the character can see to display.
     * @param display The interface to write to.
     */
    public void view() {
        try {
            //this variable is used  lot, so let's copy it to a local.
            int sightRange = (int) getBody().getDef().getScore("Sight Range");
            //these too.
            int x = getBody().getLocation().getX();
            int y = getBody().getLocation().getY();
            //clear the display so that we don't have any left over bits.
            perplexicon.display.setAll(new DisplayChar(' ', Color.black));
            //send a line in each direction to see what we can see, then write to the
            //display.
            for (int i = 0; i <= sightRange*2; i++) {
                visionLine(new LocationLine(getBody().getLocation(), 
                        x + sightRange, y - sightRange + i, true, false));
                visionLine(new LocationLine(getBody().getLocation(), 
                        x - sightRange, y - sightRange + i, true, false));
                visionLine(new LocationLine(getBody().getLocation(), 
                        x - sightRange + i, y + sightRange, true, false));
                visionLine(new LocationLine(getBody().getLocation(), 
                        x - sightRange + i, y - sightRange, true, false));
            }
            //rotate and mirror according to the player, so as to not cause sudden 
            //reorientation when the player crosses a border.
            perplexicon.display.rotate(getRotation());
            if (isxMirrored()) perplexicon.display.flipHorizontal();
            if (isyMirrored()) perplexicon.display.flipVertical();
            perplexicon.display.repaint();
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This function goes through the line and write the symbol for each 
     * location to the display. Will stop if t comes to an page 
     * @param display The display to write to.
     * @param line The line to go through.
     */
    public void visionLine(LocationLine line) {
        //save the coordinates for the center of the display, 
        //cause we'll use them alot
        int x = perplexicon.display.getDisplayXDist()/2;
        int y = perplexicon.display.getDisplayYDist()/2;
        //now check over the line and add the symbol 
        //for each point to the display.
        for (int i = 0; i < line.getLength(); i++) {
            try {
                //set the symbol at the location to the display. if the location 
                //doesn't actually exist, an exception is thrown.
                perplexicon.display.setDisplay(line.getLocation(i).getSymbol(), 
                                   x + line.getX(i), y + line.getY(i));
            } catch (NonexistentLocationException ex) {
                System.out.println(ex.getMessage());
                return;
            }
        }
    }
    
    /**
     * handles the input from the display.
     * @param display The display responsible for interfacing with the player.
     * @return Whether or not the player quit.
     */
    public void handleInput() throws PlayerWantsToQuitException {
        //get the character the player entered.
        char c = perplexicon.display.getKey();
        //set the default direction, so we can easily tell whether or not it's 
        //been changed.
        int dir = -1;
        //depending on what the key entered was, set dir accordingly.
        switch (c) {
            case '4': dir = 3;
                break;
            case '6': dir = 1;
                break;
            case '8': dir = 0;
                break;
            case '2': dir = 2;
                break;
            case 'q': throw new PlayerWantsToQuitException();
            case 'g': pickUpItem();
                break;
            case 'i': viewInventory();
                break;
            case 's': saySpell();
                break;
            case '@': 
            default: break;
        }
        //adjust dir for mirroring and rotation.
        if (dir != -1) {
            if (isxMirrored()) {
                if (dir == 1) dir = 3;
                else if (dir == 3) dir = 1;
            }
            if (isyMirrored()) {
                if (dir == 0) dir = 2;
                else if (dir == 2) dir = 0;
            }
            dir -= Rotation.cast(getRotation());
            while (dir <0) dir += 4;
            dir %= 4;
            //find the location to move the player to.
            AreaLocation l = new AreaLocation(getBody().getLocation());
            switch (dir) {
                case 0: l.move(0, -1); break;
                case 1: l.move(1, 0); break;
                case 2: l.move(0, 1); break;
                case 3: l.move(-1, 0); break;
            }
            //move the player, and adjust things if they cross a border.
            if (l.bodyAt() != null) {
                perplexicon.BodyAttack(getBody(), l.bodyAt());
            } else if (getBody().moveTo(l) && l.getTerrain() == null) {
                LocalArea.BorderArea b = l.getArea().getBorderArea(l.getX(), l.getY());
                addRotation(l.getArea().getBorderArea(l.getX(), l.getY()).getRotation());
                addxMirrored(l.getArea().getBorderArea(l.getX(), l.getY()).isXMirrored());
                addyMirrored(l.getArea().getBorderArea(l.getX(), l.getY()).isYMirrored());
            }
        }
        
    }
    
    public void pickUpItem() {
        Location l = getBody().getLocation();
        ArrayList<ItemOnGround> items = l.getArea().itemsAt(l.getX(), l.getY());
        boolean[] pickup = new boolean[items.size()];
        boolean done = false;
        RoguelikeInterface.Window win = perplexicon.display.newWindow(20, 10);
        while (!done) {
            for (int i = 0; i < items.size(); i++) {
                win.setDisplay(new DisplayChar((char)(97 + i), 
                               Color.white), 1, 1 + i);
                if (pickup[i])
                    win.setDisplay(new DisplayChar('+', Color.white), 3, 1 + i);
                else
                    win.setDisplay(new DisplayChar('-', Color.white), 3, 1 + i);
                win.drawString(5, 1 + i, items.get(i).getItem().getName(), 
                            items.get(i).getItem().getSymbol().getColor());
            }
            perplexicon.display.repaint();
            char c = perplexicon.display.getKey();
            int item = (int)c - 97;
            if (item >= 0 && item < pickup.length && item <= 26)
                pickup[item] = !pickup[item];
            else {
                switch (c) {
                    case (char)27:
                    case '\n':
                    case ' ':
                        done = true;
                        break;
                }
            }
        }
        for (int i = 0; i < pickup.length; i++){
                if (pickup[i]) {
                    getBody().addItem(items.get(i).getItem());
                    l.getArea().removeEntity(items.get(i));
                }
            }
        
    }
    
    public void viewInventory() {
        RoguelikeInterface.Window win = perplexicon.display.newWindow(40, 20);
        ArrayList<Item> inventory = getBody().getInventory();
        for (int i = 0; i < inventory.size(); i++) {
            win.setDisplay(new DisplayChar((char)(97 + i), 
                           Color.white), 1, 1 + i);
            win.drawString(3, 1 + i, inventory.get(i).getName(), 
                        inventory.get(i).getSymbol().getColor());
        }
        perplexicon.display.repaint();
        char c = perplexicon.display.getKey();
        int item = (int)c - 97;
        if (item >= 0 && item < inventory.size() && item <= 26)
            viewItem(inventory.get(item));
        
    }
    
    public void wield(Item i) {
        getBody().setWeapon(i);
    }
    
    public void viewItem(Item i) {
        RoguelikeInterface.Window win = perplexicon.display.newWindow(20, 10);
        win.setDisplay(i.getSymbol(), 1, 1);
        win.drawString(3, 1, i.getName(), 
                        i.getSymbol().getColor());
        perplexicon.display.repaint();
        char c = perplexicon.display.getKey();
        switch (c) {
            case 'd':getBody().removeItem(i);
                getBody().getLocation().getArea().addEntity(new ItemOnGround(getBody().getLocation(), i));
                break;
            case 'w': wield(i);
                break;
            case 'u':i.use(perplexicon.display, getBody());
                break;
        }
    }
    
    public void viewStatus() {
        RoguelikeInterface.Window win = perplexicon.display.newWindow(20, 8);
    }
    
    public void saySpell() {
        String string[] = perplexicon.display.getSentence("Say something!").split(" ", 2);
        if (perplexicon.registry.materials.containsKey(string[0]) && 
            perplexicon.registry.items.containsKey(string[1]))
        getBody().addItem(new SimpleItem(perplexicon.registry.materials.get(string[0]), 
                perplexicon.registry.items.get(string[1])));
    }

    /**
     * @return the rot
     */
    public Rotation getRotation() {
        return rot;
    }

    /**
     * @param rot the rot to set
     */
    public void setRotation(Rotation rot) {
        this.rot = rot;
    }

    /**
     * @return the xMirrored
     */
    public boolean isxMirrored() {
        return xMirrored;
    }

    /**
     * @param xMirrored the xMirrored to set
     */
    public void setxMirrored(boolean xMirrored) {
        this.xMirrored = xMirrored;
    }

    /**
     * @return the yMirrored
     */
    public boolean isyMirrored() {
        return yMirrored;
    }

    /**
     * @param yMirrored the yMirrored to set
     */
    public void setyMirrored(boolean yMirrored) {
        this.yMirrored = yMirrored;
    }
    
    /**
     * rotate the player view by r
     * @param r 
     */
    public void addRotation(Rotation r) {
        setRotation(Rotation.add(getRotation(), r));
    
    }
    
    /**
     * change the x mirroring of the player view.
     * @param b 
     */
    public void addxMirrored(boolean b) {
        if (b) {
            if (isxMirrored()) setxMirrored(false);
            else setxMirrored(true);
        }
        
    }
    
    /**
     * change the y mirroring of the player view.
     * @param b 
     */
    public void addyMirrored(boolean b) {
        if (b) {
            if (isyMirrored()) setyMirrored(false);
            else setyMirrored(true);
        }
        
    }
     
    /**
     * inherited from controller. used to determine whether this controller 
     * is a player or not.
     * @return true;
     */
    public boolean isPlayer() {
        return true;
    }
    
    @Override
    public boolean act() throws PlayerWantsToQuitException{
        if (!getBody().isAlive()) 
            return false;
        getBody().step();
        addMoves();
        for (int i = 0; getBody().getMoves() > 0 && i < 100; i++) {
            view();
                //handle the input, and if the player wants to quit, quit.
            handleInput();
        }
        return true;
    }

    @Override
    public void addMoves() {
        getBody().addMoves();
    }
}
