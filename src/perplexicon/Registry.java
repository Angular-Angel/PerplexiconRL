/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package perplexicon;

import roguelikeengine.stat.NumericStat;
import roguelikeengine.stat.Trait;
import roguelikeengine.stat.BinaryStat;
import roguelikeengine.stat.AggregateStat;
import roguelikeengine.stat.ModifiedStat;
import roguelikeengine.stat.DerivedStat;
import roguelikeengine.stat.Stat;
import roguelikeengine.item.SimpleItem;
import roguelikeengine.item.ItemDefinition;
import roguelikeengine.item.Item;
import roguelikeengine.item.ItemScript;
import roguelikeengine.item.AttackScript;
import roguelikeengine.item.MaterialDefinition;
import roguelikeengine.item.CompositeItem;
import groovy.lang.GroovyClassLoader;
import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.groovy.control.CompilationFailedException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import roguelikeengine.area.TerrainDefinition;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.item.*;
import roguelikeengine.item.DamageScript;
import roguelikeengine.largeobjects.BiologyScript;
import roguelikeengine.largeobjects.BodyDefinition;
import roguelikeengine.stat.*;

/**
 *
 * @author Greg
 */
public class Registry {
    
    public HashMap<String, MaterialDefinition> materials;
    public HashMap<String, ItemDefinition> items;
    public HashMap<String, BodyDefinition> bodyTypes;
    public HashMap<String, TerrainDefinition> terrainTypes;
    public AttackScript meleeAttackScript;
    
    public Registry() {
        items = new HashMap<>();
        materials = new HashMap<>();
        bodyTypes = new HashMap<>();
        terrainTypes =  new HashMap<>();
    }
    
    public void readJSONMaterials(File file) {
        JSONParser parser = new JSONParser();
	try {
		JSONArray matdefs = (JSONArray) parser.parse(new FileReader(file));
                for (Object e : matdefs) {
                    JSONObject m = (JSONObject) e;
                    String name = (String) m.get("name");
                    Color c = readJSONColor((JSONArray) m.get("color"));
                    
                    HashMap<String, Stat>  stats = readJSONStats((JSONArray) m.get("stats"));
                    
                    String scriptFile = (String) m.get("script");
                    
                    MaterialDefinition mat = new MaterialDefinition(name, c, stats, 
                    (DamageScript) readGroovyScript(new File(scriptFile)));
                    materials.put(name, mat);
                    
                }
 
	} catch (Exception e) {
		Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, e);
	} 
    }
    
    private Color readJSONColor(JSONArray ja) {
        return new Color(((Long) ja.get(0)).intValue(), 
                ((Long) ja.get(1)).intValue(), 
                ((Long) ja.get(2)).intValue());
    }
    
    private DisplayChar readJSONDisplayChar(JSONObject jo) {
         JSONArray ja = (JSONArray) jo.get("color");
         return new DisplayChar(((String) jo.get("symbol")).charAt(0), 
                            readJSONColor(ja));
    }
    
    private DisplayChar readJSONDisplayChar(JSONArray ja) {
         JSONArray jcolor = (JSONArray) ja.get(1);
         return new DisplayChar(((String) ja.get(0)).charAt(0), 
                            readJSONColor(jcolor));
    }
    
    public void readJSONItemDefs(File file) {
        JSONParser parser = new JSONParser();
	try {
		JSONArray itemdefs = (JSONArray) parser.parse(new FileReader(file));
                for (Object e : itemdefs) {
                    JSONObject m = (JSONObject) e;
                    String names[] = new String[3];
                    names[0] = (String) m.get("singular");
                    names[1] = (String) m.get("plural");
                    names[2] = (String) m.get("adjective");
                    char symbol = ((String) m.get("symbol")).charAt(0);
                    MaterialDefinition defmat;
                    if (m.containsKey("defmat"))
                    defmat = materials.get((String) m.get("defmat"));
                    else defmat = null;
                    
                    HashMap<String, Stat> stats = readJSONStats((JSONArray) m.get("stats"));
                    ItemDefinition itemdef;
                    if (m.containsKey("script")) {
                        ItemScript use = (ItemScript) readGroovyScript(new File((String) m.get("script")));
                        itemdef = new ItemDefinition(symbol, names, defmat, stats, use);
                    } else {
                        itemdef = new ItemDefinition(symbol, names, defmat, stats, null);
                    }
                    
                    JSONArray attacks = (JSONArray) m.get("attacks");
                    for (Object o : attacks) {
                        JSONArray attack = (JSONArray) o;
                        String attackName = (String) attack.get(0);
                        
                        HashMap<String, Stat>  attackStats = readJSONStats((JSONArray) attack.get(1));
                        itemdef.addAttack(new Trait(attackName, attackStats));
                    }
                    
                    items.put(names[0], itemdef);
                }
 
	} catch (Exception e) {
		Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, e);
	} 
    }
    
    private HashMap<String, Stat> readJSONStats(JSONArray stats) {
        HashMap<String, Stat> ret = new HashMap<>();
        for (int i = 0; i < stats.size(); i++) {
            Stat stat = null;
            if (((JSONArray) stats.get(i)).size() == 1)
                stat = new BinaryStat();
            else {
                Object o = ((JSONArray) stats.get(i)).get(1);
                if (o instanceof Long) {
                    stat = new NumericStat(((Long) o).intValue());
                } else if (o instanceof Double) {
                    stat = new NumericStat(((Double) o).floatValue());
                } else if (o instanceof JSONArray) {
                    JSONArray oa = (JSONArray) o;
                    if (oa.get(1) instanceof String) {
                        String s1 = (String) oa.get(0);
                        String s2 = (String) oa.get(1);
                        char operand = ((String) oa.get(2)).charAt(0);
                        stat = new DerivedStat(s1, s2, operand);
                    } else if (oa.get(1) instanceof Double) {
                        String s1 = (String) oa.get(0);
                        float mod = ((Double) oa.get(1)).floatValue();
                        char operand = ((String) oa.get(2)).charAt(0);
                        stat = new ModifiedStat(s1, mod, operand);
                    } else if (oa.get(1) instanceof Long) {
                        String s1 = (String) oa.get(0);
                        float mod = ((Long) oa.get(1)).floatValue();
                        char operand = ((String) oa.get(2)).charAt(0);
                        stat = new ModifiedStat(s1, mod, operand);
                    }

                } else if (o instanceof String) {
                    String statname = (String) o;
                    switch ((String) ((JSONArray) stats.get(i)).get(2)) {
                        case "Sum": stat = new AggregateStat(statname, AggregateStat.sum); break;
                        case "Ave": stat = new AggregateStat(statname, AggregateStat.average); break;
                        case "Min": stat = new AggregateStat(statname, AggregateStat.lowest); break;
                        case "Max": stat = new AggregateStat(statname, AggregateStat.highest); break;
                        default: throw new IllegalArgumentException();
                    }
                }
            }
            ret.put((String) ((JSONArray) stats.get(i)).get(0), stat);

        }
        return ret;
    }
    
    public SimpleItem readJSONSimpleItem(JSONArray item) {
        MaterialDefinition matdef = materials.get((String) item.get(0));
        ItemDefinition itemdef = items.get((String) item.get(1));
        return new SimpleItem(matdef, itemdef);
    }
    
    public CompositeItem readJSONCompositeItem(JSONArray item) {
        String name = (String) item.get(0);
        DisplayChar display = readJSONDisplayChar((JSONArray) item.get(1));
        HashMap<String, Stat> stats = readJSONStats((JSONArray) item.get(2));
        CompositeItem ret;
        if (item.size() == 5) {
            ItemScript use = (ItemScript) readGroovyScript(new File((String) item.get(4)));
            ret = new CompositeItem(name, display, stats, use);
        } else {
            ret = new CompositeItem(name, display, stats, null);
        }
                    
        for (Object o : (JSONArray) item.get(3)) {
            ret.addPart(readJSONItem((JSONArray) o));
        }
        return ret;
    }
    
    public Item readJSONItem(JSONArray item) {
        if (item.size() == 4) return readJSONCompositeItem(item);
        else if (item.size() == 2) return readJSONSimpleItem(item);
        return null;
    }
    
    public void readJSONBodyDefs(File file) {
        JSONParser parser = new JSONParser();
	try {
		JSONArray itemdefs = (JSONArray) parser.parse(new FileReader(file));
                for (Object e : itemdefs) {
                    JSONObject m = (JSONObject) e;
                    String name = (String) m.get("name");
                    
                    DisplayChar d = readJSONDisplayChar(m);
                    
                    HashMap<String, Stat> stats = readJSONStats((JSONArray) m.get("stats"));
                    
                    JSONArray bodyparts = (JSONArray) m.get("bodyparts");
                    BodyDefinition bodydef;
                    BiologyScript script = (BiologyScript) readGroovyScript(new File((String) m.get("script")));
                    bodydef = new BodyDefinition(name, d, stats, script);
                     
                    for (Object o : bodyparts) {
                        bodydef.bodyTemplate.addPart(readJSONItem((JSONArray) o));
                    }
                    bodyTypes.put(name, bodydef);
                }
 
	} catch (Exception e) {
		Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, e);
	} 
    }
    
    public Object readGroovyScript(File file) {
        try {
            GroovyClassLoader gcl = new GroovyClassLoader();
            Object reactionScript = gcl.parseClass(file).newInstance();
            return reactionScript;
        } catch (CompilationFailedException | IOException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void readJSONTerrainDefs(File file) {
        JSONParser parser = new JSONParser();
	try {
		JSONArray itemdefs = (JSONArray) parser.parse(new FileReader(file));
                for (Object e : itemdefs) {
                    JSONObject m = (JSONObject) e;
                    String name = (String) m.get("Name");
                    DisplayChar symbol = readJSONDisplayChar(m);
                    MaterialDefinition mat;
                    mat = materials.get((String) m.get("Material"));
                    
                    HashMap<String, Stat> stats = readJSONStats((JSONArray) m.get("Stats"));
                    
                    TerrainDefinition terrain = new TerrainDefinition(symbol, mat, stats);
                    
                    terrainTypes.put(name, terrain);
                }
 
	} catch (Exception e) {
		Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, e);
	} 
    }
}
