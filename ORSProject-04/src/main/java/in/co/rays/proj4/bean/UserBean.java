package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * UserBean class is used as a Bean/DTO to store
 * user-related information such as personal details,
 * login credentials, role information, and contact details.
 * 
 * This class extends BaseBean and provides getter
 * and setter methods for all properties.
 * 
 * @author Vinay
 * @version 1.0
 *
 */
public class UserBean extends BaseBean {

	/**
	 * User first name.
	 */
	private String firstName;

	/**
	 * User last name.
	 */
	private String lastName;

	/**
	 * User login ID or email.
	 */
	private String login;

	/**
	 * User password.
	 */
	private String password;

	/**
	 * Confirmation password.
	 */
	private String confirmPassword;

	/**
	 * User date of birth.
	 */
	private Date dob;

	/**
	 * User mobile number.
	 */
	private String mobileNo;

	/**
	 * Role ID of the user.
	 */
	private long roleId;

	/**
	 * Gender of the user.
	 */
	private String gender;

	/**
	 * Returns first name.
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets first name.
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns last name.
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets last name.
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns login ID.
	 * 
	 * @return login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets login ID.
	 * 
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Returns password.
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets password.
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns confirm password.
	 * 
	 * @return confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Sets confirm password.
	 * 
	 * @param confirmPassword
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * Returns date of birth.
	 * 
	 * @return dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets date of birth.
	 * 
	 * @param dob
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Returns mobile number.
	 * 
	 * @return mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets mobile number.
	 * 
	 * @param mobileNo
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Returns role ID.
	 * 
	 * @return roleId
	 */
	public long getRoleId() {
		return roleId;
	}

	/**
	 * Sets role ID.
	 * 
	 * @param roleId
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	/**
	 * Returns gender.
	 * 
	 * @return gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets gender.
	 * 
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
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
	 * @return firstName
	 */
	@Override
	public String getValue() {
		return firstName;
	}
}