package in.co.rays.proj4.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Utility class to provide data validation rules across the application layer. 
 * Contains static methods to check numbers, formats, names, passwords, formats, 
 * and specific date types using Regular Expressions and type parsing handlers.
 * 
 * @author vinay
 * @version 1.0
 */
public class DataValidator {

	/**
	 * Checks if the passed string value is null or has an empty trimmed length.
	 * 
	 * @param val The string to check
	 * @return boolean true if null or empty, false otherwise
	 */
	public static boolean isNull(String val) {
		if (val == null || val.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if the passed string value contains data (is neither null nor empty).
	 * 
	 * @param val The string to check
	 * @return boolean true if containing data, false if null or empty
	 */
	public static boolean isNotNull(String val) {
		return !isNull(val);
	}

	/**
	 * Verifies whether the provided string can be safely parsed into a valid integer value.
	 * 
	 * @param val The string to validate
	 * @return boolean true if it is a valid integer, false otherwise
	 */
	public static boolean isInteger(String val) {

		if (isNotNull(val)) {
			try {
				Integer.parseInt(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Verifies whether the provided string can be safely parsed into a valid long integer value.
	 * 
	 * @param val The string to validate
	 * @return boolean true if it is a valid long integer, false otherwise
	 */
	public static boolean isLong(String val) {
		if (isNotNull(val)) {
			try {
				Long.parseLong(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Validates the email format criteria using a standardized regular expression.
	 * 
	 * @param val The email address string to validate
	 * @return boolean true if it matches a valid email structural syntax pattern, false otherwise
	 */
	public static boolean isEmail(String val) { // abc@gmail.comm

		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (isNotNull(val)) {
			try {
				return val.matches(emailreg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * Checks if the provided text string matches standard structural conventions for human names.
	 * Excludes leading white spaces and includes typical letters or punctuation styles.
	 * 
	 * @param val The name string to validate
	 * @return boolean true if matching structural specifications, false otherwise
	 */
	public static boolean isName(String val) {

		String namereg = "^[^-\\s][\\p{L} .'-]+$";

		if (isNotNull(val)) {
			try {
				return val.matches(namereg);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Validates whether the incoming string complies with structural Roll Number format requirements.
	 * Expects exactly two letters followed by three numeric digits.
	 * 
	 * @param val The roll number string to validate
	 * @return boolean true if it strictly fits the tracking pattern requirement, false otherwise
	 */
	public static boolean isRollNo(String val) {

		String rollreg = "[a-zA-Z]{2}[0-9]{3}";

		if (isNotNull(val)) {
			try {
				return val.matches(rollreg);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Validates password complexity against rigorous standard configuration criteria.
	 * Requires at least 1 digit, 1 lowercase letter, 1 uppercase letter, 1 special character, 
	 * no whitespaces, and a total length between 8 to 12 characters.
	 * 
	 * @param val The password string to validate
	 * @return boolean true if matching structural complexity parameters, false otherwise
	 */
	public static boolean isPassword(String val) {

		String passreg = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,12}";

		if (isNotNull(val)) {
			try {
				return val.matches(passreg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * Simple standalone check to verify whether the password character bounds fall strictly 
	 * between 8 and 12 characters.
	 * 
	 * @param val The password string to validate
	 * @return boolean true if length constraints are met, false otherwise
	 */
	public static boolean isPasswordLength(String val) {

		if (isNotNull(val) && val.length() >= 8 && val.length() <= 12) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Validates standard mobile phone structural format constraints.
	 * Checks if the numbers are exactly 10 digits and start with index values of 6, 7, 8, or 9.
	 * 
	 * @param val The phone number string to validate
	 * @return boolean true if structural criteria match, false otherwise
	 */
	public static boolean isPhoneNo(String val) {

		String phonereg = "^[6-9][0-9]{9}$";

		if (isNotNull(val)) {
			try {
				return val.matches(phonereg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * Simple verification constraint tracking that checking value matches an explicit length of 10 digits.
	 * 
	 * @param val The phone string to evaluate
	 * @return boolean true if exact count length requirement matches, false otherwise
	 */
	public static boolean isPhoneLength(String val) {

		if (isNotNull(val) && val.length() == 10) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Evaluates if a given date string can map safely to a concrete runtime {@link Date} context.
	 * Uses internal parsing formats inside {@link DataUtility#getDate(String)}.
	 * 
	 * @param val The source formatted date expression string
	 * @return boolean true if structural parse handles cleanly, false otherwise
	 */
	public static boolean isDate(String val) {

		Date d = null;
		if (isNotNull(val)) {
			d = DataUtility.getDate(val);
		}
		return d != null;
	}

	/**
	 * Resolves whether the specific given calendar date value falls on a Sunday.
	 * 
	 * @param val The target parsing date string expression
	 * @return boolean true if evaluated target represents Sunday, false otherwise
	 */
	public static boolean isSunday(String val) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(DataUtility.getDate(val));
		int i = cal.get(Calendar.DAY_OF_WEEK);

		if (i == Calendar.SUNDAY) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Execution entryway used for validating local rule conversion validation filters.
	 * 
	 * @param args execution arguments block
	 */
	public static void main(String[] args) {

		// Test isNull and isNotNull
		System.out.println("isNull Test:");
		System.out.println("Empty String: " + isNull(""));
		System.out.println("Null String: " + isNull(null));
		System.out.println("Non-null String: " + isNotNull("Hello"));

		// Test isInteger
		System.out.println("\nisInteger Test:");
		System.out.println("Valid Integer String: '123' -> " + isInteger("123"));
		System.out.println("Invalid Integer String: 'abc' -> " + isInteger("abc"));
		System.out.println("Null String: -> " + isInteger(null));

		// Test isLong
		System.out.println("\nisLong Test:");
		System.out.println("Valid Long String: '1234567890' -> " + isLong("1234567890"));
		System.out.println("Invalid Long String: 'abc' -> " + isLong("abc"));

		// Test isEmail
		System.out.println("\nisEmail Test:");
		System.out.println("Valid Email: 'test@example.com' -> " + isEmail("test@example.com"));
		System.out.println("Invalid Email: 'test@.com' -> " + isEmail("test@.com"));

		// Test isName
		System.out.println("\nisName Test:");
		System.out.println("Valid Name: 'John Doe' -> " + isName("John Doe"));
		System.out.println("Invalid Name: '123John' -> " + isName("123John"));

		// Test isRollNo
		System.out.println("\nisRollNo Test:");
		System.out.println("Valid RollNo: 'AB123' -> " + isRollNo("AB123"));
		System.out.println("Invalid RollNo: 'A1234' -> " + isRollNo("A1234"));

		// Test isPassword
		System.out.println("\nisPassword Test:");
		System.out.println("Valid Password: 'Passw0rd@123' -> " + isPassword("Passw0rd@123"));
		System.out.println("Invalid Password: 'pass123' -> " + isPassword("pass123"));

		// Test isPasswordLength
		System.out.println("\nisPasswordLength Test:");
		System.out.println("Valid Password Length: 'Passw0rd' -> " + isPasswordLength("Passw0rd"));
		System.out.println("Invalid Password Length: 'pass' -> " + isPasswordLength("pass"));

		// Test isPhoneNo
		System.out.println("\nisPhoneNo Test:");
		System.out.println("Valid PhoneNo: '9876543210' -> " + isPhoneNo("9876543210"));
		System.out.println("Invalid PhoneNo: '1234567890' -> " + isPhoneNo("1234567890"));

		// Test isPhoneLength
		System.out.println("\nisPhoneLength Test:");
		System.out.println("Valid Phone Length: '9876543210' -> " + isPhoneLength("9876543210"));
		System.out.println("Invalid Phone Length: '98765' -> " + isPhoneLength("98765"));

		// Test isDate
		System.out.println("\nisDate Test:");
		System.out.println("Valid Date: '01-01-2025' -> " + isDate("2025-01-01"));
		System.out.println("Invalid Date: '10/15/2024' -> " + isDate("10/15/2024"));

		// Test isSunday
		System.out.println("\nisSunday Test:");
		System.out.println("Date on Sunday: '09-11-2025' -> " + isSunday("09-11-2025"));
		System.out.println("Date not on Sunday: '10-11-2025' -> " + isSunday("10-11-2025"));
	}
}