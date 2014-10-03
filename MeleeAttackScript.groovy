import roguelikeengine.item.*;
import roguelikeengine.largeobjects.*;
import roguelikeengine.stat.*;

/**
 *
 * @author greg
 */
class MeleeAttackScript implements AttackScript {
    
    public Attack generateAttack(Body attacker, Item weapon, Trait attack){
        HashMap<String, Stat> attackStats;
        attackStats = new HashMap<>();
        try {
            //println weapon.getName() + ": " + attack.getName();
            float velocity = attacker.getScore("Strength");
            if (weapon.getScore("Mass") > 250 * attacker.getScore("Strength")) {
                velocity -= (weapon.getScore("Mass") - 250 * attacker.getScore("Strength"))/250;
            }
            attackStats.put("Velocity", new NumericStat((float) velocity * attack.getScore("Mechanical Advantage")));
            attackStats.put("Sharpness", weapon.viewStat("Sharpness"));
            attackStats.put("Mass", new NumericStat(weapon.getScore("Mass")));
            attackStats.putAll(attack.viewStats());
        }
        catch (NoSuchStatException e) {
            
        }
        MeleeAttack ret = new MeleeAttack(attack.getName(), weapon, attacker, attackStats);
        ret.addStat("Raw Damage", new DerivedStat("Velocity", "Effective Mass", "*".charAt(0)));
        ret.refactor();
        
        return ret;
    }
	
}

