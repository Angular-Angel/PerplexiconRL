/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import java.util.ArrayList;
import roguelikeengine.stat.Stat;
import java.util.HashMap;
import roguelikeengine.display.RoguelikeInterface;
import roguelikeengine.stat.StatContainer;
import roguelikeengine.stat.Trait;

/**
 *
 * @author Greg
 */
public class ItemDefinition extends StatContainer{
    
    private char symbol;
    private String[] name;
    private ArrayList<Trait> attacks;
    private MaterialDefinition defmat;
    private ItemScript useScript;
    
    public ItemDefinition(char d, String[] s) {
        this(d, s, null, new HashMap<String, Stat>(), null);
    }
    
    public ItemDefinition(char d, String[] names, MaterialDefinition mat, HashMap<String, Stat> stats, ItemScript use) {
        super(stats);
        this.symbol = d;
        this.name = names;
        defmat = mat;
        attacks = new ArrayList<>();
        useScript = use;
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
     * @return the Name
     */
    public String getName(int i) {
        if (i < 3)
        return name[i];
        else throw new IllegalArgumentException ();
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String[] name) {
        this.name = name;
    }
    
    public void addAttack(Trait attack) {
        attacks.add(attack);
    }
    
    public void addAttacks(ArrayList<Trait> attacks) {
        this.attacks.addAll(attacks);
    }
    
    public Trait getAttack(String s) {
        for (Trait t : attacks){
            if (t.getName().equals(s)) return t;
        }
        return null;
    }
    
    public ArrayList<Trait> getAttacks() {
        return (ArrayList<Trait>) attacks.clone();
    }

    /**
     * @return the nameMaterial
     */
    public MaterialDefinition defaultMaterial() {
        return defmat;
    }

    /**
     * @return the useScript
     */
    public ItemScript getUseScript() {
        return useScript;
    }
}
