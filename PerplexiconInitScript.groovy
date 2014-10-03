import roguelikeengine.item.*
import roguelikeengine.largeobjects.*;
import roguelikeengine.stat.*;
import roguelikeengine.display.*;
import roguelikeengine.area.*;
import roguelikeengine.controller.*;
import roguelikeengine.*;
import roguelikeengine.display.*;
import perplexicon.*;

class PerplexiconInitScript implements InitScript {

    public void init(Perplexicon perplexicon) {
        LocalArea area = new LocalArea(10, 10, perplexicon.registry.terrainTypes.get("Stone Floor"), "Area1");
        TerrainDefinition t = perplexicon.registry.terrainTypes.get("Stone Wall");
            
        for (int i = 0; i < area.getxDist(); i++) {
            area.setTerrain(i, 0, t);
            area.setTerrain(i, area.getyDist() - 1, t);
        }
        
        for (int i = 0; i < area.getyDist(); i++) {
            area.setTerrain(0, i, t);
            area.setTerrain(area.getxDist() - 1, i, t);
        }
        
        Body body = new Body("Player", new AreaLocation(area, 5, 5), 
               perplexicon.registry.bodyTypes.get("Human"));
        Player player = new Player(body, perplexicon);
        area.addEntity(body);
        Clock clock = new Clock();
        clock.addActor(player);
        clock.play();
    }

}
