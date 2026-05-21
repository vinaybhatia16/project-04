package in.co.rays.proj4.exception;

/**
 * DuplicateRecordException is a checked exception thrown when an operational 
 * attempt is made to insert or update a database entity with unique constraints 
 * that already exist in the persistent datastore.
 * 
 * @author vinay
 * @version 1.0
 */
public class DuplicateRecordException extends Exception {

	/**
	 * Constructs a new DuplicateRecordException with the specified detailed error message.
	 * 
	 * @param msg the detailed error message describing the specific duplicate data conflict
	 */
	public DuplicateRecordException(String msg) {
		super(msg);
	}
}