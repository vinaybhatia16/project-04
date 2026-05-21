package in.co.rays.proj4.exception;

/**
 * ApplicationException is a generic checked exception thrown when an internal business 
 * logic or structural application error occurs within the system processing layers.
 * 
 * @author vinay
 * @version 1.0
 */
public class ApplicationException extends Exception {

	/**
	 * Constructs a new ApplicationException with the specified detailed error message.
	 * 
	 * @param msg the detailed error message describing the specific system failure context
	 */
	public ApplicationException(String msg) {
		super(msg);
	}
}