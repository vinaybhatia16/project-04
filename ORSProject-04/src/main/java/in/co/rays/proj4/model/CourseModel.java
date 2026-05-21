package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * Course Business Model Tier functionality component. Coordinates systemic low-level 
 * transactional database interactions using standard JDBC architectures to administer complete 
 * CRUD operations, lookup queries, pagination frameworks, and search criteria processing 
 * for active {@link CourseBean} collections.
 * 
 * @author vinay
 * @version 1.0
 */
public class CourseModel {

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
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_course");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	/**
	 * Commits a new Course instance profile into the persistent storage engine layers. Verifies 
	 * course record uniqueness criteria prior to executing underlying row insertions.
	 * 
	 * @param bean CourseBean containing the unique data properties to register
	 * @return long primitive tracking the generated storage primary key index reference
	 * @throws ApplicationException if general processing errors impact business logic steps
	 * @throws DuplicateRecordException if a matching entity name string parameter exists in storage
	 */
	public long add(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		int pk = 0;

		CourseBean duplicateCourse = findByName(bean.getName());

		if (duplicateCourse != null) {
			throw new DuplicateRecordException("Course Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_course values(?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDuration());
			pstmt.setString(4, bean.getDescription());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	/**
	 * Updates properties and operational metadata configuration elements for an existing 
	 * persistent Course entity row inside the database engine using its designated primary key.
	 * 
	 * @param bean CourseBean containing the updated modification variables and data values
	 * @throws ApplicationException if general processing errors impact business logic steps
	 * @throws DuplicateRecordException if updating properties violates unique field constraints
	 */
	public void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;

		CourseBean duplicateCourse = findByName(bean.getName());
		if (duplicateCourse != null && duplicateCourse.getId() != bean.getId()) {
			throw new DuplicateRecordException("Course already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_course set name = ?, duration = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDuration());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.setLong(8, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Course ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Deletes an explicit, existing Course row from the database using its tracking 
	 * primary key property reference contained within the structural data bean.
	 * 
	 * @param bean CourseBean containing the unique identifier index intended for removal
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public void delete(CourseBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_course where id = ?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Locates and builds a distinct populated {@link CourseBean} container instance matching 
	 * the targeted unique numerical Primary Key index value lookup constraint.
	 * 
	 * @param pk primitive long representing the targeted identifier constraint parameter
	 * @return CourseBean instance containing retrieved data, or null if no record matched criteria
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public CourseBean findByPk(long pk) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_course where id = ?");
		CourseBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Course by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Locates and builds a populated {@link CourseBean} container instance matching the 
	 * specific textual unique system title or business name criteria pattern.
	 * 
	 * @param name String value representing the targeted course title search parameter
	 * @return CourseBean instance containing retrieved data, or null if no matching row exists
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public CourseBean findByName(String name) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_course where name = ?");
		CourseBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));

			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Course by Course Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Fetches the complete comprehensive matrix ledger of all registered Course entity profiles 
	 * configured inside persistent data tables.
	 * 
	 * @return List containing all retrieved Course record rows
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public List<CourseBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	/**
	 * Executes highly targeted database search queries based on conditional parameters provided 
	 * within the filter bean. Supports data subset pagination bounds handling via page indexes and limits.
	 * 
	 * @param bean CourseBean containing dynamic property constraints for targeted filtering
	 * @param pageNo primitive int indicating the zero-based offset page matrix tracking reference
	 * @param pageSize primitive int setting maximum record capacity constraints allowed per view index
	 * @return List containing filtered matching Course record entities matching parameters
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public List<CourseBean> search(CourseBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_course where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			if (bean.getDuration() != null && bean.getDuration().length() > 0) {
				sql.append(" and duration like '" + bean.getDuration() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" and description like '" + bean.getDescription() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		ArrayList<CourseBean> list = new ArrayList<CourseBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in search Course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}