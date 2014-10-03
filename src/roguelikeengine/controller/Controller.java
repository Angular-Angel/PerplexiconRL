package roguelikeengine.controller;

import roguelikeengine.largeobjects.Actor;
import roguelikeengine.largeobjects.Body;

/**
 *
 * @author greg
 */
public abstract class Controller implements Actor{
    private Body body;
    
    /**
     * Constructor
     * @param body The body that this controls.
     */
    public Controller(Body body) {
        setBody(body);
        getBody().setController(this);
    }
    /**
     * @return the body
     */
    public Body getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(Body body) {
        this.body = body;
    }
    
    public abstract boolean isPlayer();
    
    @Override
    public abstract boolean act() throws PlayerWantsToQuitException;

    @Override
    public void addMoves(){
        getBody().addMoves();
    }
}
