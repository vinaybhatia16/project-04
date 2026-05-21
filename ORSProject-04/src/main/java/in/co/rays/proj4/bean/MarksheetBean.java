package in.co.rays.proj4.bean;

/**
 * MarksheetBean class contains student marksheet information.
 * It extends BaseBean and provides implementation
 * for key and value methods.
 * 
 * @author Vinay
 */
public class MarksheetBean extends BaseBean {

    /** Stores student roll number */
    private String rollNo;

    /** Stores student id */
    private long studentId;

    /** Stores student name */
    private String name;

    /** Stores physics marks */
    private Integer physics;

    /** Stores chemistry marks */
    private Integer chemistry;

    /** Stores maths marks */
    private Integer maths;

    /**
     * Returns roll number.
     * 
     * @return roll number
     */
    public String getRollNo() {
        return rollNo;
    }

    /**
     * Sets roll number.
     * 
     * @param rollNo student roll number
     */
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    /**
     * Returns student id.
     * 
     * @return student id
     */
    public long getStudentId() {
        return studentId;
    }

    /**
     * Sets student id.
     * 
     * @param studentId student id
     */
    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    /**
     * Returns student name.
     * 
     * @return student name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets student name.
     * 
     * @param name student name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns physics marks.
     * 
     * @return physics marks
     */
    public Integer getPhysics() {
        return physics;
    }

    /**
     * Sets physics marks.
     * 
     * @param physics physics marks
     */
    public void setPhysics(Integer physics) {
        this.physics = physics;
    }

    /**
     * Returns chemistry marks.
     * 
     * @return chemistry marks
     */
    public Integer getChemistry() {
        return chemistry;
    }

    /**
     * Sets chemistry marks.
     * 
     * @param chemistry chemistry marks
     */
    public void setChemistry(Integer chemistry) {
        this.chemistry = chemistry;
    }

    /**
     * Returns maths marks.
     * 
     * @return maths marks
     */
    public Integer getMaths() {
        return maths;
    }

    /**
     * Sets maths marks.
     * 
     * @param maths maths marks
     */
    public void setMaths(Integer maths) {
        this.maths = maths;
    }

    /**
     * Returns unique key value.
     * 
     * @return marksheet id as string
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns display value.
     * 
     * @return student name
     */
    @Override
    public String getValue() {
        return name;
    }
}