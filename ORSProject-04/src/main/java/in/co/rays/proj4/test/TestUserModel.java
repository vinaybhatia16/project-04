package in.co.rays.proj4.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.UserModel;

public class TestUserModel {

	public static void main(String[] args) throws ParseException, SQLException, ApplicationException {
//			 testAdd();
//			testUpdate();
		// testDelete();
		// testfindByPk();
		// testFindByName();
		// testSearch();
		//testauthenticate();
		testSearch();
	}

	public static void testAdd() throws ParseException, SQLException {
		try {
			UserBean bean = new UserBean();
			UserModel model = new UserModel();

			bean.setFirstName("Student");
			bean.setLastName("rayss");
			bean.setLogin("1admiqqjgslidgjan@gmail.com");
			;
			bean.setPassword("123");
			bean.setDob(new Timestamp(new Date().getTime()));
			bean.setMobileNo("9926278640");
			bean.setRoleId(1);
			bean.setGender("male");
			bean.setModifiedBy("same");
			bean.setCreatedBy("same");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			UserBean addedbean = model.findByPk(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws ParseException, SQLException {
		try {
			UserBean bean = new UserBean();
			UserModel model = new UserModel();

			bean.setId(2);
			bean.setFirstName("Student2");
			bean.setLastName("rayss");
			bean.setLogin("student2@ecample.com");
			;
			bean.setPassword("123");
			bean.setDob(new Timestamp(new Date().getTime()));
			bean.setMobileNo("9926278640");
			bean.setRoleId(1);
			bean.setGender("male");
			bean.setModifiedBy("same");
			bean.setCreatedBy("same");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.update(bean);

		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() throws ApplicationException {

		UserBean bean = new UserBean();
		UserModel model = new UserModel();

		bean.setId(3);
		model.delete(bean);
	}

	private static void testauthenticate() {
		try {
			UserBean bean = new UserBean();
			UserModel model = new UserModel();
			bean.setLogin("1admin@gmail.com");
			bean.setPassword("123");
			bean = model.authenticate(bean.getLogin(), bean.getPassword());
			if (bean != null) {
				System.out.println("Successfully login");
			} else {
				System.out.println("Invaliad login Id & password");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	private static void testSearch() {
		try {
			UserBean bean = new UserBean();
			UserModel model = new UserModel();
			List<UserBean> list = new ArrayList<>();
			 //bean.setFirstName("admin");
			// bean.setLastName("Bandhiye");
			//bean.setLogin("1admin@gmail.com");
			bean.setId(2);
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator<UserBean> it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());

				System.out.println(bean.getGender());

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

}
