
import roguelikeengine.item.*;
import java.util.Random;
import roguelikeengine.largeobjects.*;
import roguelikeengine.stat.*;


class FleshDamageScript implements DamageScript {
    
    public static int SMASHING_ATTACK = 0;
    public static int CUTTING_ATTACK = 1;
    public static int PIERCING_ATTACK = 2;
    public static int THRUSTING_ATTACK = 3;

    public void run(Attack a, Item i) throws NoSuchStatException {
        float contactArea = Math.min(a.getScore("Contact Area"), i.getScore("Size"));
        switch ((int) a.getScore("Type")) {
            case SMASHING_ATTACK:
            case THRUSTING_ATTACK:
                float bluntDamage = a.getScore("Raw Damage")/contactArea;
                int blunt = bluntDamage/i.getScore("Impact Point");
                switch (blunt) {
                    case 0: break;
                    case 1: 
                        ItemMod mod = new ItemMod("Bruise", "Bruised");
                        mod.addStat("Hit Points", new NumericStat(-1000));
                        i.addMod(mod);
                        break;
                    case {it >= 2}: 
                        ItemMod mod = new ItemMod("Pulping", "Pulped");
                        mod.addStat("Hit Points", new NumericStat(-3000 * blunt));
                        i.addMod(mod);
                        
                        break;
                }
                break;
            case CUTTING_ATTACK:
                float cuttingDamage = a.getScore("Raw Damage") * a.getScore("Sharpness");
                int cutting = cuttingDamage/i.getScore("Cutting Point");
                switch (cutting) {
                    case 0: break;
                    case 1: 
                        ItemMod mod = new ItemMod("Cut", "Cut");
                        mod.addStat("Hit Points", new NumericStat(-1000));
                        i.addMod(mod);
                        break;
                    case {it >= 2}: 
                        ItemMod mod = new ItemMod("Slice", "Sliced");
                        mod.addStat("Hit Points", new NumericStat(-3000 * cutting));
                        i.addMod(mod);
                        break;
                }
                break;
            case PIERCING_ATTACK:
                float piercingDamage = (a.getScore("Raw Damage") * a.getScore("Sharpness"))/contactArea;
                int piercing = piercingDamage/i.getScore("Piercing Point");
                switch (piercing) {
                    case 0: break;
                    case 1: 
                        ItemMod mod = new ItemMod("Stab Wound", "Stabbed");
                        mod.addStat("Hit Points", new NumericStat(-1000));
                        i.addMod(mod);
                        break;
                    case {it >= 2}: 
                        ItemMod mod = new ItemMod("Impalement Wound", "Impaled");
                        mod.addStat("Hit Points", new NumericStat(-3000 * piercing));
                        i.addMod(mod);
                        break;
                }
                break;
            default:
                break;

        }
           if (i.getScore("Hit Points") <= 0) {
               i.destroy();
           }
    }

}
