package in.co.rays.proj4.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class to perform data type conversions, formatting, and formatting-safe
 * data extractions across the application layer.
 * 
 * @author Vinay
 * @version 1.0
 */
public class DataUtility {

	/**
	 * Default Application Date Format pattern.
	 */
	public static final String APP_DATE_FORMAT = "dd-MM-yyyy";

	/**
	 * Default Application Time Format pattern.
	 */
	public static final String APP_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

	/**
	 * Formatter instance for standard date conversions.
	 */
	private static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMAT);

	/**
	 * Formatter instance for timestamp/time conversions.
	 */
	private static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMAT);

	/**
	 * Trims and returns the string if it contains data, otherwise returns the original value.
	 * 
	 * @param val The raw input string
	 * @return String trimmed value, or the original null/empty value
	 */
	public static String getString(String val) {
		if (DataValidator.isNotNull(val)) {
			return val.trim();
		} else {
			return val;
		}
	}

	/**
	 * Converts an object to its String representation safely. Returns an empty string if null.
	 * 
	 * @param val The object to convert
	 * @return String representation of the object, or empty string "" if null
	 */
	public static String getStringData(Object val) {
		if (val != null) {
			return val.toString();
		} else {
			return "";
		}
	}

	/**
	 * Parses a string value into an integer safely. Returns 0 if validation fails.
	 * 
	 * @param val The string containing the integer data
	 * @return int parsed primitive integer value, or 0 if invalid
	 */
	public static int getInt(String val) {
		if (DataValidator.isInteger(val)) {
			return Integer.parseInt(val);
		} else {
			return 0;
		}
	}

	/**
	 * Parses a string value into a long safely. Returns 0 if validation fails.
	 * 
	 * @param val The string containing the long data
	 * @return long parsed primitive long value, or 0 if invalid
	 */
	public static long getLong(String val) {
		if (DataValidator.isLong(val)) {
			return Long.parseLong(val);
		} else {
			return 0;
		}
	}

	/**
	 * Parses a string value into a {@link Date} object based on {@link #APP_DATE_FORMAT}.
	 * Returns null if parsing encounters an exception.
	 * 
	 * @param val The formatted date string
	 * @return Date parsed date object, or null if parsing fails
	 */
	public static Date getDate(String val) {
		Date date = null;
		try {
			date = formatter.parse(val);
		} catch (Exception e) {

		}
		return date;
	}

	/**
	 * Formats a {@link Date} object into a string representation based on {@link #APP_DATE_FORMAT}.
	 * Returns an empty string if formatting fails or date is null.
	 * 
	 * @param date The date object to convert
	 * @return String formatted date string, or empty string ""
	 */
	public static String getDateString(Date date) {
		try {
			return formatter.format(date);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * Parses a string value into a {@link Timestamp} object based on {@link #APP_TIME_FORMAT}.
	 * Returns null if parsing fails.
	 * 
	 * @param val The formatted time/date string
	 * @return Timestamp parsed timestamp object, or null if invalid
	 */
	public static Timestamp getTimestamp(String val) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp((timeFormatter.parse(val)).getTime());
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	/**
	 * Creates a {@link Timestamp} object from raw millisecond values.
	 * Returns null if processing throws an exception.
	 * 
	 * @param l epoch time value in milliseconds
	 * @return Timestamp instantiated timestamp object, or null
	 */
	public static Timestamp getTimestamp(long l) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(l);
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	/**
	 * Generates a {@link Timestamp} reflecting the current system execution clock time.
	 * 
	 * @return Timestamp current system timestamp object
	 */
	public static Timestamp getCurrentTimestamp() {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(new Date().getTime());
		} catch (Exception e) {
		}
		return timeStamp;

	}

	/**
	 * Extracts the long millisecond value from a given {@link Timestamp} instance.
	 * Returns 0 if an exception occurs.
	 * 
	 * @param tm The target Timestamp object
	 * @return long time value in milliseconds, or 0 if target is null
	 */
	public static long getTimestamp(Timestamp tm) {
		try {
			return tm.getTime();
		} catch (Exception e) {
			return 0;
		}
	}
}