package in.co.rays.proj4.util;

import java.util.ResourceBundle;

/**
 * Utility class to read and manage application properties and localized messages 
 * from the system configuration resource bundles. It supports dynamic placeholder 
 * parameter replacement for parameterized error messages and system strings.
 * 
 * @author vinay
 * @version 1.0
 */
public class PropertyReader {

	/**
	 * Resource bundle instance linked directly to the application system configuration file.
	 */
	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");

	/**
	 * Retrieves the string value associated with the specified configuration key.
	 * If the key is not found or an exception occurs, the key itself is returned as a fallback.
	 * 
	 * @param key the property key to look up in the bundle
	 * @return the configured string value mapping to the key, or the key string itself on failure
	 */
	public static String getValue(String key) {

		String val = null;

		try {
			val = rb.getString(key); // {0} is required
		} catch (Exception e) {
			val = key;
		}
		return val;
	}

	/**
	 * Retrieves the string value associated with the specified key and replaces the standard 
	 * <code>{0}</code> placeholder token with a single runtime parameter string.
	 * 
	 * @param key   the property key to look up in the bundle
	 * @param param the replacement string injected into the <code>{0}</code> placeholder position
	 * @return the formatted message string containing the injected parameter
	 */
	public static String getValue(String key, String param) {
		String msg = getValue(key); // {0} is required
		msg = msg.replace("{0}", param);
		return msg;
	}

	/**
	 * Retrieves the string value associated with the specified key and sequentially replaces 
	 * multi-index placeholder patterns like <code>{0}</code>, <code>{1}</code>, etc., using 
	 * an array of parameter strings.
	 * 
	 * @param key    the property key to look up in the bundle
	 * @param params an array of replacement strings mapping index-for-index to matching placeholders
	 * @return the formatted message string containing all injected parameters
	 */
	public static String getValue(String key, String[] params) {
		String msg = getValue(key); // {0} and {1} are required.
		for (int i = 0; i < params.length; i++) {
			msg = msg.replace("{" + i + "}", params[i]);
		}
		return msg;
	}

	/**
	 * Main execution driver entry point to run local diagnostic evaluation traces 
	 * against bundle loading routines.
	 * 
	 * @param args standard console command line parameters block
	 */
	public static void main(String[] args) {

		System.out.println("Single key example:");
		System.out.println(PropertyReader.getValue("error.require"));

		System.out.println("\nSingle parameter replacement example:");
		System.out.println(PropertyReader.getValue("error.require", "loginId"));

		System.out.println("\nMultiple parameter replacement example:");
		String[] params = { "Roll No", "Student Name" };
		System.out.println(PropertyReader.getValue("error.multipleFields", params));
	}
}