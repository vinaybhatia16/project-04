package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * Marksheet Business Model Tier functionality component. Coordinates systemic low-level 
 * transactional database interactions using standard JDBC architectures to administer complete 
 * CRUD operations, lookup queries, pagination frameworks, and search criteria processing 
 * for student {@link MarksheetBean} records. Includes dedicated operations to calculate cumulative 
 * academic performance breakdowns and generate competitive grade-point merit ledgers.
 * 
 * @author vinay
 * @version 1.0
 */
public class MarksheetModel {

	/**
	 * Extracts and generates the next available operational Primary Key index value 
	 * inside the target storage structure for concurrency management.
	 * 
	 * @return Integer representing the calculated incremental unique primary key sequence index
	 * @throws DatabaseException if low-level transactional storage or connection exceptions occur
	 */
	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_marksheet");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();  
		} catch (Exception e) {
			throw new DatabaseException("Exception in Marksheet getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	/**
	 * Commits a new Marksheet instance profile into the persistent storage engine layers. Pre-fetches 
	 * dependent relational student name profiles and verifies tracking roll number uniqueness constraints 
	 * prior to executing raw record insertions under local transaction boundaries.
	 * 
	 * @param bean MarksheetBean containing the unique data properties to register
	 * @return long primitive tracking the generated storage primary key index reference
	 * @throws ApplicationException if general processing errors impact business logic steps
	 * @throws DuplicateRecordException if a matching academic roll number exists in storage
	 */
	public long add(MarksheetBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		StudentModel studentModel = new StudentModel();
		StudentBean studentbean = studentModel.findByPk(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

		MarksheetBean duplicateMarksheet = findByRollNo(bean.getRollNo());

		if (duplicateMarksheet != null) {
			throw new DuplicateRecordException("Roll Number already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_marksheet values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getRollNo());
			pstmt.setLong(3, bean.getStudentId());
			pstmt.setString(4, bean.getName());
			pstmt.setInt(5, bean.getPhysics());
			pstmt.setInt(6, bean.getChemistry());
			pstmt.setInt(7, bean.getMaths());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	/**
	 * Updates properties and operational metadata configuration elements for an existing 
	 * persistent Marksheet entity row inside the database engine. Assures record consistency 
	 * and monitors relational constraint metrics.
	 * 
	 * @param bean MarksheetBean containing the updated modification variables and data values
	 * @throws ApplicationException if general processing errors impact business logic steps
	 * @throws DuplicateRecordException if updating properties conflicts with active unique roll numbers
	 */
	public void update(MarksheetBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		MarksheetBean beanExist = findByRollNo(bean.getRollNo());

		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Roll No is already exist");
		}

		StudentModel studentModel = new StudentModel();
		StudentBean studentbean = studentModel.findByPk(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_marksheet set roll_no = ?, student_id = ?, name = ?, physics = ?, chemistry = ?, maths = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			pstmt.setString(1, bean.getRollNo());
			pstmt.setLong(2, bean.getStudentId());
			pstmt.setString(3, bean.getName());
			pstmt.setInt(4, bean.getPhysics());
			pstmt.setInt(5, bean.getChemistry());
			pstmt.setInt(6, bean.getMaths());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.setLong(11, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Marksheet ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Deletes an explicit, existing Marksheet row from the database using its tracking 
	 * primary key property reference contained within the structural data bean.
	 * 
	 * @param bean MarksheetBean containing the unique identifier index intended for removal
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public void delete(MarksheetBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("delete from st_marksheet where id = ?");
			pstmt.setLong(1, bean.getId());
			System.out.println("Deleted Marksheet");
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in delete marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Locates and builds a distinct populated {@link MarksheetBean} container instance matching 
	 * the targeted unique numerical Primary Key index value lookup constraint.
	 * 
	 * @param pk primitive long representing the targeted identifier constraint parameter
	 * @return MarksheetBean instance containing retrieved data, or null if no record matched criteria
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public MarksheetBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_marksheet where id = ?");
		MarksheetBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in getting marksheet by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Locates and builds a populated {@link MarksheetBean} container instance matching the 
	 * specific textual unique tracking roll number pattern constraint.
	 * 
	 * @param rollNo String value representing the targeted index parameter to match
	 * @return MarksheetBean instance containing retrieved data, or null if no matching row exists
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public MarksheetBean findByRollNo(String rollNo) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_marksheet where roll_no = ?");
		MarksheetBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, rollNo);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in getting marksheet by roll no");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Executes highly targeted database search queries based on conditional parameters provided 
	 * within the filter bean. Supports data subset pagination bounds handling via page indexes and limits.
	 * 
	 * @param bean MarksheetBean containing dynamic property constraints for targeted filtering
	 * @param pageNo primitive int indicating the zero-based offset page matrix tracking reference
	 * @param pageSize primitive int setting maximum record capacity constraints allowed per view index
	 * @return List containing filtered matching Marksheet record entities matching parameters
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public List<MarksheetBean> search(MarksheetBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_marksheet where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getRollNo() != null && bean.getRollNo().length() > 0) {
				sql.append(" and roll_no like '" + bean.getRollNo() + "%'");
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			if (bean.getPhysics() != null && bean.getPhysics() > 0) {
				sql.append(" and physics = " + bean.getPhysics());
			}
			if (bean.getChemistry() != null && bean.getChemistry() > 0) {
				sql.append(" and chemistry = " + bean.getChemistry());
			}
			if (bean.getMaths() != null && bean.getMaths() > 0) {
				sql.append(" and maths = '" + bean.getMaths());
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Update rollback exception " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	/**
	 * Compiles and returns the top performing student marksheet ledger filtered by absolute 
	 * passing criteria across core academic topics, ordered in descending order by aggregate marks.
	 *
	 * @param pageNo primitive int indicating the zero-based offset page matrix tracking reference
	 * @param pageSize primitive int setting maximum record capacity constraints allowed per view index
	 * @return List of MarksheetBean student records sorted sequentially by peak combined totals
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public List<MarksheetBean> getMeritList(int pageNo, int pageSize) throws ApplicationException {

		ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();
		StringBuffer sql = new StringBuffer(
				"select id, roll_no, name, physics, chemistry, maths, (physics + chemistry + maths) as total from st_marksheet where physics > 33 and chemistry > 33 and maths > 33 order by total desc");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MarksheetBean bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in getting merit list of Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}