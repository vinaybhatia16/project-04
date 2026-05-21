package in.co.rays.proj4.exception;

/**
 * DatabaseException is a checked exception thrown when an error or unexpected 
 * failure occurs during low-level database operations or storage engine interactions.
 * 
 * @author vinay
 * @version 1.0
 */
public class DatabaseException extends Exception {

	/**
	 * Constructs a new DatabaseException with the specified detailed error message.
	 * 
	 * @param msg the detailed error message describing the specific database connectivity 
	 *            or transactional failure context
	 */
	public DatabaseException(String msg) {
		super(msg);
	}
}