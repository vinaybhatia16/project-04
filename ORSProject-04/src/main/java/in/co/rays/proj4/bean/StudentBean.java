package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * StudentBean class contains student information.
 * It extends BaseBean and provides implementation
 * for key and value methods.
 * 
 * @author Vinay
 */
public class StudentBean extends BaseBean {

    /** Stores student first name */
    private String firstName;

    /** Stores student last name */
    private String lastName;

    /** Stores student date of birth */
    private Date dob;

    /** Stores student gender */
    private String gender;

    /** Stores student mobile number */
    private String mobileNo;

    /** Stores student email */
    private String email;

    /** Stores college id */
    private long collegeId;

    /** Stores college name */
    private String collegeName;

    /**
     * Returns student first name.
     * 
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets student first name.
     * 
     * @param firstName student first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns student last name.
     * 
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets student last name.
     * 
     * @param lastName student last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns student date of birth.
     * 
     * @return date of birth
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets student date of birth.
     * 
     * @param dob student date of birth
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Returns student gender.
     * 
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets student gender.
     * 
     * @param gender student gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns student mobile number.
     * 
     * @return mobile number
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Sets student mobile number.
     * 
     * @param mobileNo student mobile number
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * Returns student email.
     * 
     * @return email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets student email.
     * 
     * @param email student email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns college id.
     * 
     * @return college id
     */
    public long getCollegeId() {
        return collegeId;
    }

    /**
     * Sets college id.
     * 
     * @param collegeId college id
     */
    public void setCollegeId(long collegeId) {
        this.collegeId = collegeId;
    }

    /**
     * Returns college name.
     * 
     * @return college name
     */
    public String getCollegeName() {
        return collegeName;
    }

    /**
     * Sets college name.
     * 
     * @param collegeName college name
     */
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /**
     * Returns unique key value.
     * 
     * @return student id as string
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns display value.
     * 
     * @return student full name
     */
    @Override
    public String getValue() {
        return firstName + " " + lastName;
    }
}