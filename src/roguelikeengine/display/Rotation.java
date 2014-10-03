package roguelikeengine.display;

/**
 *
 * @author greg
 */
public enum Rotation{
    
    degree0, degree90, degree180, degree270;
    
    /**
     * Adds the two rotations.
     * @param r1 The first rotation to add.
     * @param r2 The second rotation to add.
     * @return The sum of the rotations.
     */
    public static Rotation add(Rotation r1, Rotation r2) {
        int i = (cast(r1) + cast(r2)) % 4;
        switch (i) {
            case 0: return degree0;
            case 1: return degree90;
            case 2: return degree180;
            case 3: return degree270;
        }
        return degree0;
    }
    
    /**
     * Subtracts the second rotation from the first one.
     * @param r1 The first rotation.
     * @param r2 The second rotation.
     * @return The result of the two rotations.
     */
    public static Rotation subtract(Rotation r1, Rotation r2) {
        return cast(cast(r1) - cast(r2) % 4);
    }
    
    /**
     * Casts a rotation into an int.
     * @param r The rotation.
     * @return The int to which that rotation can be cast.
     */
    public static int cast(Rotation r){
        switch(r) {
            case degree0: return 0;
            case degree90: return 1;
            case degree180: return 2;
            case degree270: return 3;
        }
        return 0;
    }
    
    /**
     * Casts an int into a rotation.
     * @param r The int.
     * @return The rotation to which that int can be cast.
     */
    public static Rotation cast(int r){
        switch(r) {
            case 0: return degree0;
            case 1: return degree90;
            case 2: return degree180;
            case 3: return degree270;
        }
        return degree0;
    }
}

