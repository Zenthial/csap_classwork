package tolls;

/**
 * An exception used to indicate errors by the tolls.TollRecord class.  This is a an
 * unchecked exception that will cause the program to terminate if not caught.
 *
 * @author RIT CS
 */
public class TollRecordException extends RuntimeException {
    /**
     * Create a new tolls.TollRecordException that contains the specified
     * message.
     *
     * @param message the message recorded by the exception.
     */
    public TollRecordException(String message) {
        super(message);
    }
}