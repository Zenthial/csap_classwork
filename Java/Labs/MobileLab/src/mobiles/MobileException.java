package mobiles;

/**
 * A class to represent an exception when parsing a mobile tree.
 *
 * @author RIT CS
 */
public class MobileException extends Exception {
    /**
     * Create a new MobileException
     * @param msg associated exception message
     */
    public MobileException(String msg) {
        super(msg);
    }
}
