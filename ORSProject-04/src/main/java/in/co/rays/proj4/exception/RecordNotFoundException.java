package in.co.rays.proj4.exception;

/**
 * RecordNotFoundException is a checked exception thrown when a data retrieval 
 * or lookup operation fails to find a matching persistent row or record 
 * for the requested criteria within the datastore.
 * 
 * @author vinay
 * @version 1.0
 */
public class RecordNotFoundException extends Exception {

	/**
	 * Constructs a new RecordNotFoundException with the specified detailed error message.
	 * 
	 * @param msg the detailed error message describing the missing data record context
	 */
	public RecordNotFoundException(String msg) {
		super(msg);
	}
}