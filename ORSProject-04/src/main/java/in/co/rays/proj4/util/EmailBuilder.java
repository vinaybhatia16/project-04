package in.co.rays.proj4.util;

import java.util.HashMap;

/**
 * Utility class to build HTML-formatted email messages for various application events 
 * such as user registration, password recovery, and password updates.
 * 
 * @author vinay
 * @version 1.0
 */
public class EmailBuilder {

	/**
	 * Generates an HTML email message body for a newly registered user.
	 * Expected map keys: "login", "password".
	 * 
	 * @param map A HashMap containing user registration details.
	 * @return A string containing the complete HTML email message body.
	 */
	public static String getUserRegistrationMessage(HashMap<String, String> map) {
		StringBuilder msg = new StringBuilder();
		msg.append("<HTML><BODY>");
		msg.append("<H1>Welcome to ORS, ").append(map.get("login")).append("!</H1>");
		msg.append("<P>Your registration is successful. You can now log in and manage your account.</P>");
		msg.append("<P><B>Login Id: ").append(map.get("login")).append("<BR>Password: ").append(map.get("password"))
				.append("</B></P>");
		msg.append("<P>Change your password after logging in for security reasons.</P>");
		msg.append("<P>For support, contact +91 98273 60504 or hrd@sunrays.co.in.</P>");
		msg.append("</BODY></HTML>");
		return msg.toString();
	}

	/**
	 * Generates an HTML email message body for password recovery.
	 * Expected map keys: "firstName", "lastName", "login", "password".
	 * 
	 * @param map A HashMap containing the user's details and recovered password.
	 * @return A string containing the complete HTML email message body.
	 */
	public static String getForgetPasswordMessage(HashMap<String, String> map) {
		StringBuilder msg = new StringBuilder();
		msg.append("<HTML><BODY>");
		msg.append("<H1>Password Recovery</H1>");
		msg.append("<P>Hello, ").append(map.get("firstName")).append(" ").append(map.get("lastName")).append(".</P>");
		msg.append("<P>Your login details are:</P>");
		msg.append("<P><B>Login Id: ").append(map.get("login")).append("<BR>Password: ").append(map.get("password"))
				.append("</B></P>");
		msg.append("</BODY></HTML>");
		return msg.toString();
	}

	/**
	 * Generates an HTML email message body confirming a successful password change.
	 * Expected map keys: "firstName", "lastName", "login", "password".
	 * 
	 * @param map A HashMap containing the user's details and updated password.
	 * @return A string containing the complete HTML email message body.
	 */
	public static String getChangePasswordMessage(HashMap<String, String> map) {
		StringBuilder msg = new StringBuilder();
		msg.append("<HTML><BODY>");
		msg.append("<H1>Password Changed Successfully</H1>");
		msg.append("<P>Dear ").append(map.get("firstName")).append(" ").append(map.get("lastName"))
				.append(", your password has been updated.</P>");
		msg.append("<P>Your updated login details are:</P>");
		msg.append("<P><B>Login Id: ").append(map.get("login")).append("<BR>New Password: ").append(map.get("password"))
				.append("</B></P>");
		msg.append("</BODY></HTML>");
		return msg.toString();
	}
}