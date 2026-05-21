package in.co.rays.proj4.bean;

/**
 * DropdownListBean interface is used to provide
 * key and value methods for displaying data
 * in dropdown lists.
 * 
 * Classes implementing this interface must
 * define methods to return key and display value.
 * 
 * @author Vinay
 */
public interface DropdownListBean {

    /**
     * Returns unique key value.
     * 
     * @return key value
     */
    public String getKey();

    /**
     * Returns display value.
     * 
     * @return display value
     */
    public String getValue();

} 