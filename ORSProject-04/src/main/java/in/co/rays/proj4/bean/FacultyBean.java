package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * FacultyBean class contains faculty information.
 * It extends BaseBean and provides implementation
 * for key and value methods.
 * 
 * @author Vinay
 */
public class FacultyBean extends BaseBean {

    /** Stores faculty first name */
    private String firstName;

    /** Stores faculty last name */
    private String lastName;

    /** Stores faculty date of birth */
    private Date dob;

    /** Stores faculty gender */
    private String gender;

    /** Stores faculty mobile number */
    private String mobileNo;

    /** Stores faculty email */
    private String email;

    /** Stores college id */
    private long collegeId;

    /** Stores college name */
    private String collegeName;

    /** Stores course id */
    private long courseId;

    /** Stores course name */
    private String courseName;

    /** Stores subject id */
    private long subjectId;

    /** Stores subject name */
    private String subjectName;

    /**
     * Returns faculty first name.
     * 
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets faculty first name.
     * 
     * @param firstName faculty first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns faculty last name.
     * 
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets faculty last name.
     * 
     * @param lastName faculty last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns date of birth.
     * 
     * @return date of birth
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets date of birth.
     * 
     * @param dob date of birth
     */
    public void setDob(Date dob) {
        this.dob = dob;
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
     * @param gender faculty gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns mobile number.
     * 
     * @return mobile number
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Sets mobile number.
     * 
     * @param mobileNo faculty mobile number
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * Returns email.
     * 
     * @return email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     * 
     * @param email faculty email
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

    public void setCollegeId(long collegeId) {
        this.collegeId = collegeId;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Returns unique key value.
     * 
     * @return faculty id as string
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns display value.
     * 
     * @return faculty full name
     */
    @Override
    public String getValue() {
        return firstName + " " + lastName;
    }
}