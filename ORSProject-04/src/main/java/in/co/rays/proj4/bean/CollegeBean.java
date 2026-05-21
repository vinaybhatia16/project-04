package in.co.rays.proj4.bean;

/**
 * CollegeBean represents a College entity containing
 * college-related information such as name, address,
 * state, city, and phone number.
 * <p>
 * This bean extends {@link BaseBean} to inherit
 * common audit properties and implements methods
 * required for dropdown list handling.
 * 
 * @author vinay
 */
public class CollegeBean extends BaseBean {

    /** Name of the college */
    private String name;

    /** Address of the college */
    private String address;

    /** State where the college is located */
    private String state;

    /** City where the college is located */
    private String city;

    /** Contact phone number of the college */
    private String phoneNo;

    /**
     * Returns the college name.
     * 
     * @return college name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the college name.
     * 
     * @param name college name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the college address.
     * 
     * @return college address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the college address.
     * 
     * @param address college address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the state name.
     * 
     * @return state name
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state name.
     * 
     * @param state state name
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns the city name.
     * 
     * @return city name
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city name.
     * 
     * @param city city name
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the phone number of the college.
     * 
     * @return phone number
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Sets the phone number of the college.
     * 
     * @param phoneNo college phone number
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Returns the unique key of the bean.
     * This method is generally used in dropdown lists.
     * 
     * @return bean ID as String
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the display value of the bean.
     * This method is generally used in dropdown lists.
     * 
     * @return college name
     */
    @Override
    public String getValue() {
        return name;
    }
}