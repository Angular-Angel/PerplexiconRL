
import roguelikeengine.item.*;
import java.util.Random;
import roguelikeengine.largeobjects.*;
import roguelikeengine.stat.*;


class HumanBiologyScript implements BiologyScript {
    
    public boolean isAlive(Body b) {
        if (b.containsPart("Heart") && b.containsPart("Brain"))
        return true;
        println b.getName() + " Died!"
        return false;
    }
    
    public void step(Body b) {
    }
    
    public void beAttacked(Body b, Attack a) {
        b.takeAttack(a);
    }
}
