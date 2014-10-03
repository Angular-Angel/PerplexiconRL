/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.stat;

/**
 *
 * @author Greg
 */
public class NoSuchStatException extends Exception {
    
    /**
     * Default constructor.
     */
    public NoSuchStatException() {
        super();
    }
    
    /**
     * Constructor with a string for the message.
     * @param string The message.
     */
    public NoSuchStatException(String string) {
        super(string);
    }
    
    public NoSuchStatException(String string, Throwable t) {
        super(string, t);
    }
    
    public NoSuchStatException(Throwable t) {
        super(t);
    }
}
