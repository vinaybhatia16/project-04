package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;


public class Coursetest {
	public static CourseModel model = new CourseModel();

	public static void main(String[] args) throws Exception {
		// testadd();
		// testDelete();
		// testFindByName();
		// testfindByPk();
		// testupdate();
		testsearch();
		// testlist();

	}

	private static void testsearch() {

		try {
			CourseBean bean = new CourseBean();
			 CourseModel model = new CourseModel();
			List<CourseBean> list = new ArrayList<>();
			
			//bean.setId(2);
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator<CourseBean> it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
				
				System.out.println(bean.getId());
				System.out.println(bean.getName());
			

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	// TODO Auto-generated method stub

	

	public static void testadd() throws DuplicateRecordException {

		try {
			CourseBean bean = new CourseBean();
			bean.setId(2);
			bean.setName("BBA");
			bean.setDescription("commerce");
			bean.setDuration("4 Year");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testupdate() {
		try {
			CourseBean bean = model.findByPk(1L);
			bean.setName("Mca");

			model.update(bean);
			System.out.println("update succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		try {
			CourseBean bean = new CourseBean();
			long pk = 2L;
			bean.setId(1);
			model.delete(bean);
			System.out.println("Test Deleted");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testfindByPk() {
		try {
			CourseBean bean = new CourseBean();
			long pk = 2L;
			bean = model.findByPk(pk);
			if (bean == null) {
				System.out.println("test findbypk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
