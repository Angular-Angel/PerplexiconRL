package roguelikeengine.stat;

/**
 *
 * @author greg
 */
public class BinaryStat implements Stat{

    @Override
    public float getScore() {
        return 1;
    }

    @Override
    public void setContainer(StatContainer i) {}

    @Override
    public void refactor() throws NoSuchStatException {}

    @Override
    public void modify(float change) {}

    @Override
    public void modifyBase(float change) {}

    @Override
    public Stat copy() {
        return new BinaryStat();
    }

}
