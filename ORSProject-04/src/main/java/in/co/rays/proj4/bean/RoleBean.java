package in.co.rays.proj4.bean;

/**
 * RoleBean class contains role information.
 * It extends BaseBean and provides implementation
 * for key and value methods.
 * 
 * @author Vinay
 */
public class RoleBean extends BaseBean {

    /** Administrator role */
    public static final int ADMIN = 1;

    /** Student role */
    public static final int STUDENT = 2;

    /** College role */
    public static final int COLLEGE = 3;

    /** Kiosk role */
    public static final int KIOSK = 4;

    /** Faculty role */
    public static final int FACULTY = 5;

    /** Stores role name */
    private String name;

    /** Stores role description */
    private String description;

    /**
     * Returns role name.
     * 
     * @return role name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets role name.
     * 
     * @param name role name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns role description.
     * 
     * @return role description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets role description.
     * 
     * @param description role description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns unique key value.
     * 
     * @return role id as string
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns display value.
     * 
     * @return role name
     */
    @Override
    public String getValue() {
        return name;
    }

}