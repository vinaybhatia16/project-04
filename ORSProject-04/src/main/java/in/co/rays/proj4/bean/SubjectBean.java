package in.co.rays.proj4.bean;

/**
 * SubjectBean class contains subject information.
 * It extends BaseBean and provides implementation
 * for key and value methods.
 * 
 * @author Vinay
 */
public class SubjectBean extends BaseBean {

    /** Stores subject name */
    private String name;

    /** Stores course id */
    private long courseId;

    /** Stores course name */
    private String courseName;

    /** Stores subject description */
    private String description;

    /**
     * Returns subject name.
     * 
     * @return subject name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets subject name.
     * 
     * @param name subject name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns course id.
     * 
     * @return course id
     */
    public long getCourseId() {
        return courseId;
    }

    /**
     * Sets course id.
     * 
     * @param courseId course id
     */
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    /**
     * Returns course name.
     * 
     * @return course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets course name.
     * 
     * @param courseName course name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Returns subject description.
     * 
     * @return subject description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets subject description.
     * 
     * @param description subject description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns unique key value.
     * 
     * @return subject id as string
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns display value.
     * 
     * @return subject name
     */
    @Override
    public String getValue() {
        return name;
    }
}