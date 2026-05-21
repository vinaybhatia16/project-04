package in.co.rays.proj4.util;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import in.co.rays.proj4.bean.DropdownListBean;
import in.co.rays.proj4.model.RoleModel;

/**
 * Utility class to dynamically generate structural HTML component fragments, such as drop-down 
 * selection input boxes (`<select>`), from backend data collections (Maps, Lists).
 * 
 * @author vinay
 * @version 1.0
 */
public class HTMLUtility {

	/**
	 * Generates an HTML selective dropdown component list string powered by a raw key-value HashMap dataset.
	 * 
	 * @param name        the semantic 'name' parameter value identifier applied onto the select element
	 * @param selectedVal the default value matching matching element keys that should map with an initial 'selected' markup status
	 * @param map         the source data collection holding map key data and user-friendly visual label definitions
	 * @return a string containing the complete structural raw HTML `<select>` drop-down layout block
	 */
	public static String getList(String name, String selectedVal, HashMap<String, String> map) {

		StringBuffer sb = new StringBuffer(
				"<select style=\"width: 169px;text-align-last: center;\"; class='form-control' name='" + name + "'>");

		sb.append("\n<option selected value=''>-------------Select-------------</option>");

		Set<String> keys = map.keySet();
		String val = null;

		for (String key : keys) {
			val = map.get(key);
			if (key.trim().equals(selectedVal)) {
				sb.append("\n<option selected value='" + key + "'>" + val + "</option>");
			} else {
				sb.append("\n<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("\n</select>");
		return sb.toString();
	}

	/**
	 * Generates an HTML selective dropdown component list string powered by a generic List containing 
	 * elements implementing the {@link DropdownListBean} contract.
	 * 
	 * @param name        the semantic 'name' parameter value identifier applied onto the select element
	 * @param selectedVal the default value matching matching element keys that should map with an initial 'selected' markup status
	 * @param list         the source dynamic list data array holding elements mapping key-value criteria signatures
	 * @return a string containing the complete structural raw HTML `<select>` drop-down layout block
	 */
	public static String getList(String name, String selectedVal, List list) {

		// Collections.sort(list);

		List<DropdownListBean> dd = (List<DropdownListBean>) list;

		StringBuffer sb = new StringBuffer("<select style=\"width: 169px;text-align-last: center;\"; "
				+ "class='form-control' name='" + name + "'>");

		sb.append("\n<option selected value=''>-------------Select-------------</option>");

		String key = null;
		String val = null;

		for (DropdownListBean obj : dd) {
			key = obj.getKey();
			val = obj.getValue();

			if (key.trim().equals(selectedVal)) {
				sb.append("\n<option selected value='" + key + "'>" + val + "</option>");
			} else {
				sb.append("\n<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("\n</select>");
		return sb.toString();
	}

	/**
	 * Local test driver routine confirming standard select generation processes driven via Map objects.
	 */
	public static void testGetListByMap() {

		HashMap<String, String> map = new HashMap<>();
		map.put("male", "male");
		map.put("female", "female");

		String selectedValue = null;
		String htmlSelectFromMap = HTMLUtility.getList("gender", selectedValue, map);

		System.out.println(htmlSelectFromMap);
	}

	/**
	 * Local test driver routine confirming standard select generation processes driven via List objects.
	 * 
	 * @throws Exception thrown if unexpected persistence extraction routines fail locally
	 */
	public static void testGetListByList() throws Exception {

		RoleModel model = new RoleModel();

		// UserModel model = new UserModel();

		List list = model.list();

		String selectedValue = "1";

		String htmlSelectFromList = HTMLUtility.getList("role", selectedValue, list);

		System.out.println(htmlSelectFromList);
	}

	/**
	 * Execution entryway driving runtime utility console diagnostic validation traces.
	 * 
	 * @param args execution arguments block
	 * @throws Exception general failure exception catch line
	 */
	public static void main(String[] args) throws Exception {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("male", "male");
		map.put("female", "female");
		map.put("other", "other");

		System.out.println(getList("gender", null, map));

	}
}