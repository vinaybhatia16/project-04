package in.co.rays.proj4.util;

/**
 * The EmailMessage class serves as a transient data holder object (POJO) to 
 * encapsulate details required for sending an email, including recipient address, 
 * subject line, message body, and formatting configuration type.
 * 
 * @author vinay
 * @version 1.0
 */
public class EmailMessage {

	/**
	 * Recipient email address destination.
	 */
	private String to;

	/**
	 * Brief subject title text summarizing message purpose.
	 */
	private String subject;

	/**
	 * Body content payload text of the message block.
	 */
	private String message;

	/**
	 * Runtime track identifier signaling current formatting output style profile definition.
	 */
	private int messageType = TEXT_MSG;

	/**
	 * Format flag constant indicating an HTML content profile.
	 */
	public static final int HTML_MSG = 1;

	/**
	 * Format flag constant indicating a standard plain text content profile.
	 */
	public static final int TEXT_MSG = 2;

	/**
	 * Default no-argument constructor.
	 */
	public EmailMessage() {
	}

	/**
	 * Parameterized constructor initialized with destination target data parameters.
	 * 
	 * @param to      the recipient's email address
	 * @param subject the summary subject title text line
	 * @param message the primary content body text block
	 */
	public EmailMessage(String to, String subject, String message) {
		this.to = to;
		this.subject = subject;
		this.message = message;
	}

	/**
	 * Sets the recipient's email address destination.
	 * 
	 * @param to the tracking target destination string to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * Retrieves the recipient's email address destination.
	 * 
	 * @return the email destination string
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Sets the message subject line title text context.
	 * 
	 * @param subject the text value to record as communication subject heading
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Retrieves the current message subject line string context.
	 * 
	 * @return the recorded subject text string
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the core message body body payload context text string.
	 * 
	 * @param message primary content string expression
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Retrieves the core body message body payload context text string.
	 * 
	 * @return primary tracked body string structure expression
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Configures format flag criteria definition styling filters (HTML vs Text).
	 * 
	 * @param messageType standard structural flag parameter code values (1 for HTML, 2 for Text)
	 */
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	/**
	 * Retrieves format flag criteria profile tracking references.
	 * 
	 * @return structural type constant code indicator integer mapping value
	 */
	public int getMessageType() {
		return messageType;
	}
}