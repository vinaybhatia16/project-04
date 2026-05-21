package in.co.rays.proj4.bean;

/**
 * CourseBean class contains Course information.
 * It extends BaseBean and provides implementation
 * for key and value methods.
 * 
 * @author Vinay
 */
public class CourseBean extends BaseBean {

    /**
     * Stores course name.
     */
    private String name;

    /**
     * Stores course duration.
     */
    private String duration;

    /**
     * Stores course description.
     */
    private String description;

    /**
     * Returns course name.
     * 
     * @return course name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets course name.
     * 
     * @param name course name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns course duration.
     * 
     * @return course duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets course duration.
     * 
     * @param duration course duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Returns course description.
     * 
     * @return course description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets course description.
     * 
     * @param description course description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns unique key value.
     * 
     * @return course id as string
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns display value.
     * 
     * @return course name
     */
    @Override
    public String getValue() {
        return name;
    }
}