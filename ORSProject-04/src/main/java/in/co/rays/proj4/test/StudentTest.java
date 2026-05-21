package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.StudentModel;

public class StudentTest {

	


	public static StudentModel model= new StudentModel();
	
	
	public static void main(String[] args) throws ParseException {
		//testAdd();
		//testDelete();
		//testUpdate();
		//testFindByPK();
		//testFindByEmailId();
		testSearch();
		//testList();
		
	}


	public static void testAdd() throws ParseException {
		
		try{
			StudentBean bean=new StudentBean();
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			
			bean.setId(2L);
			bean.setFirstName("virat");
			bean.setLastName("Malviya");
			bean.setDob(sdf.parse("22/09/1997"));
			bean.setMobileNo("9407411301");
			bean.setEmail("kmal1viya30@gmail.com");
			bean.setCollegeId(1L);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			
			StudentBean addbean = model.findByPk(pk);
			if(addbean==null){
				System.out.println("Test add fail");
			}
		}catch(ApplicationException e){
			e.printStackTrace();
		}catch(DuplicateRecordException e){
			e.printStackTrace();
		}
	}
	
	public static void testDelete(){
		
		try{
			StudentBean bean = new StudentBean();
			long pk=2L;
			
			bean.setId(pk);
			model.delete(bean);
			StudentBean deletebean = model.findByPk(pk);
			if(deletebean != null){
				System.out.println("Test Delete fail");
			}
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}
	public static void testUpdate() {

        try {
      
            StudentBean bean = model.findByPk(2L);
           
            bean.setFirstName("ram");
            bean.setLastName("sharma");
            bean.setCollegeId(1L);
            model.update(bean);
            System.out.println("Test Update succ");
           
            
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (DuplicateRecordException e) {
            e.printStackTrace();
        }
    }
	public static void testFindByPK(){
		try{
			StudentBean bean = new StudentBean();
			Long pk=1L;
			bean=model.findByPk(pk);
			if(bean == null){
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmail());
			System.out.println(bean.getCollegeId());
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}
	 public static void testFindByEmailId() {
	        try {
	            StudentBean bean = new StudentBean();
	            bean = model.findByEmailId("kmalviya30@gmail.com");
	            if (bean == null) {
	                System.out.println("Test Find By EmailId fail");
	            }
	            System.out.println(bean.getId());
	            System.out.println(bean.getFirstName());
	            System.out.println(bean.getLastName());
	            System.out.println(bean.getDob());
	            System.out.println(bean.getMobileNo());
	            System.out.println(bean.getEmail());
	            System.out.println(bean.getCollegeId());
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }
	    }
	 public static void testSearch(){
		 try{
			 StudentBean bean= new StudentBean();
			 List<StudentBean> list=new ArrayList<>();
			bean.setFirstName("Kapil");
			bean.setEmail("kmalviya30@gmail.com");
			// bean.setCollegeName("RML Maheshwari");
			 //bean.setCollegeId(1L);
			 list=model.search(bean,0,0);
			 if(list.size() > 0){
				 System.out.println("Test search fail");
			 }
			 Iterator<StudentBean> it=list.iterator();
			 while(it.hasNext()){
				 bean=(StudentBean)it.next();
				 System.out.println(bean.getId());
				 System.out.println(bean.getFirstName());
				 System.out.println(bean.getLastName());
				 System.out.println(bean.getDob());
				 System.out.println(bean.getMobileNo());
				 System.out.println(bean.getEmail());
				 System.out.println(bean.getCollegeId()); 
				 
			 }
		 }catch(ApplicationException e){
			 e.printStackTrace();
		 }
	 }
	 
		 
	 
	
}

