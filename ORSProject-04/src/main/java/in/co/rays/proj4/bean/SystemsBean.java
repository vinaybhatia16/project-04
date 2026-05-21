package in.co.rays.proj4.bean;

/**
 * SystemsBean class is used as a Bean/DTO to store
 * system event information such as event code,
 * event name, and event status.
 * 
 * This class extends BaseBean and provides
 * getter and setter methods for all properties.
 * 
 * @author Vinay
 * @version 1.0
 *
 */
public class SystemsBean extends BaseBean {

	/**
	 * Event code of the system event.
	 */
	private String eventCode;

	/**
	 * Name of the event.
	 */
	private String eventName;

	/**
	 * Status of the event.
	 */
	private String eventsatuts;

	/**
	 * Returns the event code.
	 * 
	 * @return eventCode
	 */
	public String getEventCode() {
		return eventCode;
	}

	/**
	 * Sets the event code.
	 * 
	 * @param eventCode
	 */
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	/**
	 * Returns the event name.
	 * 
	 * @return eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * Sets the event name.
	 * 
	 * @param eventName
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * Returns the event status.
	 * 
	 * @return eventsatuts
	 */
	public String getEventsatuts() {
		return eventsatuts;
	}

	/**
	 * Sets the event status.
	 * 
	 * @param eventsatuts
	 */
	public void setEventsatuts(String eventsatuts) {
		this.eventsatuts = eventsatuts;
	}

	/**
	 * Returns unique key value.
	 * 
	 * @return id as String
	 */
	@Override
	public String getKey() {
		return id + "";
	}

	/**
	 * Returns display value.
	 * 
	 * @return value
	 */
	@Override
	public String getValue() {
		return null;
	}
}