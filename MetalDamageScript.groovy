
import roguelikeengine.item.*;
import java.util.Random;
import roguelikeengine.largeobjects.*;
import roguelikeengine.stat.*;


class MetalDamageScript implements DamageScript {
    
    public static int SMASHING_ATTACK = 0;
    public static int CUTTING_ATTACK = 1;
    public static int PIERCING_ATTACK = 2;
    public static int THRUSTING_ATTACK = 3;

    public void run(Attack a, Item i) throws NoSuchStatException {
        float contactArea = Math.min(a.getScore("Contact Area"), i.getScore("Size"));
        switch ((int) a.getScore("Type")) {
            case SMASHING_ATTACK:
                float bluntDamage = a.getScore("Raw Damage")/contactArea;
                if (i.getScore("Impact Point") < i.getScore("Fracture Point")) {
                    int blunt = (int) bluntDamage/i.getScore("Impact Point")
                    switch (blunt) {
                        case 0: break;
                        case 1: 
                            ItemMod mod = new ItemMod("Dent", "Dented");
                            mod.addStat("Hit Points", new NumericStat(-1000));
                            i.addMod(mod);
                            break;
                        case 2:
                            ItemMod mod = new ItemMod("Dent", "Dented");
                            mod.addStat("Hit Points", new NumericStat(-3000 * blunt));
                            i.addMod(mod);
                            break;
                    }
                } else 
                    switch ((int) bluntDamage/i.getScore("Fracture Point")) {
                        case 0: break;
                    }
                break;
            case THRUSTING_ATTACK:
                float bluntDamage = a.getScore("Raw Damage")/contactArea;
                break;
            case CUTTING_ATTACK:
                float cuttingDamage = a.getScore("Raw Damage") * a.getScore("Sharpness");
                break;
            case PIERCING_ATTACK:
                float piercingDamage = (a.getScore("Raw Damage") * a.getScore("Sharpness"))/contactArea;
                break;
            default:
                break;

        }
           
    }

}
