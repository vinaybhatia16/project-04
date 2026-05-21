package in.co.rays.proj4.bean;

import java.sql.Timestamp;

/**
 * /** BaseBean is an abstract parent bean class that contains common attributes
 * shared by all application beans.
 * <p>
 * This class provides audit information such as:
 * <ul>
 * <li>Primary key ID</li>
 * <li>Created by user</li>
 * <li>Modified by user</li>
 * <li>Creation date and time</li>
 * <li>Last modification date and time</li>
 * </ul>
 * 
 * All entity beans should extend this class to inherit these common properties.
 * 
 * @author vinay
 *
 * 
 */
public abstract class BaseBean implements DropdownListBean {

	/** Unique identifier of the bean */
	protected long id;

	/** Name of the user who created the record */
	protected String createdBy;

	/** Name of the user who last modified the record */
	protected String modifiedBy;

	/** Date and time when the record was created */
	protected Timestamp createdDatetime;

	/** Date and time when the record was last modified */
	protected Timestamp modifiedDatetime;

	/**
	 * Returns the unique ID of the bean.
	 * 
	 * @return bean ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the unique ID of the bean.
	 * 
	 * @param id bean ID
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the creator name.
	 * 
	 * @return createdBy user name
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the creator name.
	 * 
	 * @param createdBy creator user name
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Returns the modifier name.
	 * 
	 * @return modifiedBy user name
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * Sets the modifier name.
	 * 
	 * @param modifiedBy modifier user name
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * Returns the creation date and time.
	 * 
	 * @return createdDatetime timestamp
	 */
	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	/**
	 * Sets the creation date and time.
	 * 
	 * @param createdDatetime creation timestamp
	 */
	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	/**
	 * Returns the last modification date and time.
	 * 
	 * @return modifiedDatetime timestamp
	 */
	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}

	/**
	 * Sets the last modification date and time.
	 * 
	 * @param modifiedDatetime modification timestamp
	 */
	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}
}
