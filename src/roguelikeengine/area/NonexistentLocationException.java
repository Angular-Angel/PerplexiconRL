package roguelikeengine.area;

/**
 * This exception is thrown when something tries to access data from a location
 * that doesn't actually exist.
 * @author greg
 */
public class NonexistentLocationException extends Exception {
    
    /**
     * Default constructor.
     */
    public NonexistentLocationException() {
        super();
    }
    
    /**
     * Constructor with a string for the message.
     * @param string The message.
     */
    public NonexistentLocationException(String string) {
        super(string);
    }
    
    public NonexistentLocationException(String string, Throwable t) {
        super(string, t);
    }
    
    public NonexistentLocationException(Throwable t) {
        super(t);
    }
}
