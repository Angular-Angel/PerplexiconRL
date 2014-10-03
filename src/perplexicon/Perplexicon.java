package perplexicon;

import roguelikeengine.display.Rotation;
import roguelikeengine.display.RoguelikeInterface;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.controller.DijkstraMap;
import roguelikeengine.controller.EnemyAI;
import roguelikeengine.item.SimpleItem;
import roguelikeengine.item.Item;
import roguelikeengine.item.AttackScript;
import roguelikeengine.item.ItemOnGround;
import roguelikeengine.largeobjects.BodyDefinition;
import roguelikeengine.largeobjects.Attack;
import roguelikeengine.area.LocalArea;
import roguelikeengine.area.TerrainDefinition;
import roguelikeengine.area.AreaLocation;
import perplexicon.Clock;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import roguelikeengine.area.*;
import roguelikeengine.controller.*;
import roguelikeengine.item.*;
import roguelikeengine.largeobjects.*;
import roguelikeengine.stat.Trait;

/**
 *
 * @author greg
 */
public class Perplexicon implements Game{
    public RoguelikeInterface display;
    public Registry registry;
    public Random random;
    
    private Perplexicon() {
        display = new RoguelikeInterface("Perplexicon Demo", 193, 47, 
                Color.white, Color.black, new Font("Liberation Mono", Font.PLAIN, 12));
        //Loop to allow the player to pick multiple demos.
        random = new Random();
        registry = new Registry();
        registry.readJSONMaterials(new File("MaterialDefinitions.json"));
        registry.readJSONItemDefs(new File("ItemDefinitions.json"));
        registry.readJSONBodyDefs(new File("BodyDefinitions.json"));
        registry.readJSONTerrainDefs(new File("TerrainDefinitions.json"));
        registry.meleeAttackScript = (AttackScript) registry.readGroovyScript(new File("MeleeAttackScript.groovy"));
    }
    
    public static void main(String[] args) {
        Perplexicon perplexicon = new Perplexicon();
        perplexicon.start();
        
    }
    
    public void loadInitScript() {
        InitScript script = (InitScript) registry.readGroovyScript(new File("PerplexiconInitScript.groovy"));
        script.init(this);
    }
    
    /**
     * This function creates a local area that borders itself,
     * creates a player in it, and calls play.
     * 
     * @param r How the neighboring areas should be rotated.
     */
    public void makeArea(Rotation r) {
        //Create the LocalArea and the terrain to fill it with.
        LocalArea area = new LocalArea(5, 5, registry.terrainTypes.get("Stone Floor"), "Area1");
        TerrainDefinition t = registry.terrainTypes.get("Stone Wall");
        
        //Set the terrain on the LocalArea to the terrain created earlier.
        area.setTerrain(0, 0, t);
        area.setTerrain(area.getxDist() - 1, 0, t);
        area.setTerrain(0, area.getyDist() - 1, t);
        area.setTerrain(area.getxDist() - 1, area.getyDist() - 1, t);
        //area.setTerrain(3, 3, t);
        
        //area.setTerrain((area.getxDist()/2), (area.getyDist()/2), c);
        
        //Set the LocalArea as it's own border.
        area.addBorder(area, 0, -area.getyDist(), r, false, false);
        area.addBorder(area, -area.getxDist(), 0, r, false, false);
        area.addBorder(area, 0, area.getyDist(), r, false, false);
        area.addBorder(area, area.getxDist(), 0, r, false, false);
//        area.addBorder(area, -area.getxDist(), -area.getyDist());
//        area.addBorder(area, area.getxDist(), -area.getyDist());
//        area.addBorder(area, -area.getxDist(), area.getyDist());
//        area.addBorder(area, area.getxDist(), area.getyDist());
        
        //Create the player and put them in the LocalArea.
        Body body = new Body(new AreaLocation(area, 3, 3), registry.bodyTypes.get("Human"));
        Player player = new Player(body, this);
        area.addEntity(body);
        
        //Display the instructions.
        display.setAll(new DisplayChar(' ', Color.black));
        display.writeString(3, 5, "Use numpad to move.");
        display.writeString(3, 7, "Press q to quit.");
        display.repaint();
        display.getKey();
        
        //Play.
        Clock clock = new Clock();
        clock.addActor(player);
        clock.play();
    }
    
    /**
     * This function creates a local area with a couple of holes,
     * creates a couple of smaller LocalAreas to act as stairs,
     * creates a player in it, and calls play.
     */
    
    public void makeStaircase() {
        //Create the Local area and the terrain to fill it with.
        TerrainDefinition t1 = registry.terrainTypes.get("Stone Floor");
        LocalArea area = new LocalArea(9, 15, t1, "Area");
        TerrainDefinition t2 = registry.terrainTypes.get("Stone Wall");
        TerrainDefinition t3 = registry.terrainTypes.get("Stone Stairs");
        
        //Create the walls around the area.
        for (int i = 0; i < area.getxDist(); i++) {
            area.setTerrain(i, 0, t2);
            area.setTerrain(i, area.getyDist() - 1, t2);
        }
        
        for (int i = 0; i < area.getyDist(); i++) {
            area.setTerrain(0, i, t2);
            area.setTerrain(area.getxDist() - 1, i, t2);
        }
        
        //Set the LocalArea as it's own border.
//        area.addBorder(area, 0, -area.getyDist());
//        area.addBorder(area, -area.getxDist(), 0);
//        area.addBorder(area, 0, area.getyDist());
//        area.addBorder(area, area.getxDist(), 0);
//        area.addBorder(area, -area.getxDist(), -area.getyDist());
//        area.addBorder(area, area.getxDist(), -area.getyDist());
//        area.addBorder(area, -area.getxDist(), area.getyDist());
//        area.addBorder(area, area.getxDist(), area.getyDist());
        
        //Create holes for the staircase to occupy.
        for (int i = 0; i < 4; i++) {
            area.setTerrain(1, i , null);
            area.setTerrain(area.getxDist() - 2, area.getyDist() - (1 + i), null);
            area.setTerrain(2, i + 1, t2);
            area.setTerrain(area.getxDist() - 3, area.getyDist() - (2 + i), t2);
        }
        
        //Create LocalAreas for the stairs.
        LocalArea stair1 = new LocalArea(1, 3, t3, "Stair1");
        LocalArea stair2 = new LocalArea(1, 3, t3, "Stair2");
        
        //Set the stairs as borders.
        area.addBorder(stair1, 1, 1);
        area.addBorder(stair2, area.getxDist() - 2, area.getyDist() - 4);
        
        stair1.addBorder(stair2, 0, -3);
        stair2.addBorder(stair1, 0, 3);
        
        stair1.addBorder(area, -1, -1);
        stair2.addBorder(area, -(area.getxDist() - 2), -(area.getyDist() - 4));
        
        //Create the player and put them in the LocalArea.
        Body body = new Body("Player", new AreaLocation(area, 5, 5), 
               registry.bodyTypes.get("Human"));
        Player player = new Player(body, this);
        area.addEntity(body);
        Clock clock = new Clock();
        clock.addActor(player);
        clock.play();
    }
    
    /**
     * This function creates a LocalArea with walls long the top and bottom, 
     * sets it next to itself, and calls play.
     */
    
    public void makeLinearArea() {
        //Create the Local area and the terrain to fill it with.
        LocalArea area = new LocalArea(8, 9, 
                registry.terrainTypes.get("Stone Floor"), "Area1");
        TerrainDefinition t = registry.terrainTypes.get("Stone Wall");
        
        //Set the terrain on the LocalArea to the terrain created earlier.
        for (int i = 0; i < area.getxDist(); i++) {
            area.setTerrain(i, 0, t);
            area.setTerrain(i, area.getyDist() - 1, t);
        }
        
        //Set the area as it's own border.
        //area.addBorder(area, 0, -area.getyDist());
        area.addBorder(area, -area.getxDist(), 0);
        //area.addBorder(area, 0, area.getyDist());
        area.addBorder(area, area.getxDist(), 0);
//        area.addBorder(area, -area.getxDist(), -area.getyDist());
//        area.addBorder(area, area.getxDist(), -area.getyDist());
//        area.addBorder(area, -area.getxDist(), area.getyDist());
//        area.addBorder(area, area.getxDist(), area.getyDist());
        area.addEntity(new ItemOnGround(new AreaLocation(area, 4, 4), 
                new SimpleItem(registry.materials.get("Gold"), 
                registry.items.get("Bar"))));
        area.addEntity(new ItemOnGround(new AreaLocation(area, 4, 4), 
                new SimpleItem(registry.materials.get("Steel"), 
                registry.items.get("Bar"))));
        area.addEntity(new ItemOnGround(new AreaLocation(area, 4, 4), 
                new SimpleItem(registry.materials.get("Steel"), 
                registry.items.get("Longsword"))));
        area.addEntity(new ItemOnGround(new AreaLocation(area, 4, 4),
                new SimpleItem(registry.materials.get("Iron"), 
                registry.items.get("Longsword"))));
        //Create the player and put them in the LocalArea.
        BodyDefinition bodyDef = registry.bodyTypes.get("Human");
        Body body = new Body("Player", new AreaLocation(area, 5, 5), 
                bodyDef);   
        
        Player player = new Player(body, this);
        area.addEntity(body);
        //area.addOccupant(body2);
        body.setMap(new DijkstraMap(area));
        
        BodyDefinition enemyDef = registry.bodyTypes.get("Enemy");
        Body enemy = new Body("Enemy1", new AreaLocation(area, 2, 2), 
                enemyDef);
        EnemyAI AI = new EnemyAI(enemy);
        area.addEntity(enemy);
        Body enemy2 = new Body("Enemy2", new AreaLocation(area, 5, 6), 
                enemyDef);
        EnemyAI AI2 = new EnemyAI(enemy2);
        area.addEntity(enemy2);
        Clock clock = new Clock();
        clock.addActor(player);
        //clock.addActor(player2);
        clock.addActor(AI);
        clock.addActor(AI2);
        enemy.setMap(new DijkstraMap(area));
        enemy2.setMap(new DijkstraMap(area));
        clock.play();
    }

    @Override
    public void BodyAttack(Body attacker, Body defender) {
        Item weapon = attacker.getWeapon();
        if (weapon == null) {
            System.out.println(defender.getName());
            return;
        }
        ArrayList<Trait> attackList = weapon.getAttacks();
        Trait attackType = attackList.get(random.nextInt(attackList.size()));
        Attack a = null;
        a = registry.meleeAttackScript.generateAttack(attacker, weapon, attackType);
        defender.beAttacked(a);
    }

    @Override
    public void start() {
        while (true) {
            display.setAll(new DisplayChar(' ', Color.black));
            display.writeString(5, 8, "Pick 1 through 4, or q to quit.");
            display.repaint();
            switch (display.getKey()) {
                case '1': makeArea(Rotation.degree0); break;
                case '2': makeLinearArea(); break;
                case '3': makeStaircase(); break;
                case '4': loadInitScript(); break;
                case 'q': System.exit(0); return;
            }
        }
    }
}
