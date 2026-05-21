package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * College Business Model Tier functionality component. Coordinates systemic low-level 
 * transactional database interactions using standard JDBC architectures to administer complete 
 * CRUD operations, lookup queries, pagination frameworks, and search criteria processing 
 * for active {@link CollegeBean} collections.
 * 
 * @author Vinay
 * @version 1.0
 */
public class CollegeModel {

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
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_COLLEGE");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	/**
	 * Commits a new College instance profile into the persistent storage engine layers. Verifies 
	 * corporate record uniqueness criteria prior to executing underlying row insertions.
	 * 
	 * @param bean CollegeBean containing the unique data properties to register
	 * @return long primitive tracking the generated storage primary key index reference
	 * @throws ApplicationException if general processing errors impact business logic steps
	 * @throws DuplicateRecordException if a matching entity name string parameter exists in storage
	 */
	public long add(CollegeBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		int pk = 0;

		CollegeBean duplicateCollege = findByName(bean.getName());
		if (duplicateCollege != null) {
			throw new DuplicateRecordException("College Name already exists");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_COLLEGE VALUES(?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getState());
			pstmt.setString(5, bean.getCity());
			pstmt.setString(6, bean.getPhoneNo());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	/**
	 * Updates properties and operational metadata configuration elements for an existing 
	 * persistent College entity row inside the database engine using its designated primary key.
	 * 
	 * @param bean CollegeBean containing the updated modification variables and data values
	 * @throws ApplicationException if general processing errors impact business logic steps
	 * @throws DuplicateRecordException if updating properties violates unique field constraints
	 */
	public void update(CollegeBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;

		CollegeBean duplicateCollege = findByName(bean.getName());
		if (duplicateCollege != null && duplicateCollege.getId() != bean.getId()) {
			throw new DuplicateRecordException("College Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_COLLEGE SET NAME=?,ADDRESS=?,STATE=?,CITY=?,PHONE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getAddress());
			pstmt.setString(3, bean.getState());
			pstmt.setString(4, bean.getCity());
			pstmt.setString(5, bean.getPhoneNo());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.setLong(10, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in Update College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Deletes an explicit, existing College row from the database using its tracking 
	 * primary key property reference contained within the structural data bean.
	 * 
	 * @param bean CollegeBean containing the unique identifier index intended for removal
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public void delete(CollegeBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_COLLEGE WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Locates and builds a distinct populated {@link CollegeBean} container instance matching 
	 * the targeted unique numerical Primary Key index value lookup constraint.
	 * 
	 * @param pk primitive long representing the targeted identifier constraint parameter
	 * @return CollegeBean instance containing retrieved data, or null if no record matched criteria
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public CollegeBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		CollegeBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ST_COLLEGE WHERE ID=?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting College by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Locates and builds a populated {@link CollegeBean} container instance matching the 
	 * specific textual unique system title or business name criteria pattern.
	 * 
	 * @param name String value representing the targeted college title search parameter
	 * @return CollegeBean instance containing retrieved data, or null if no matching row exists
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public CollegeBean findByName(String name) throws ApplicationException {
		Connection conn = null;
		CollegeBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ST_COLLEGE WHERE NAME=?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting College by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Fetches the complete comprehensive matrix ledger of all registered College entity profiles 
	 * configured inside persistent data tables.
	 * 
	 * @return List containing all retrieved College record rows
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public List<CollegeBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	/**
	 * Executes highly targeted database search queries based on conditional parameters provided 
	 * within the filter bean. Supports data subset pagination bounds handling via page indexes and limits.
	 * 
	 * @param bean CollegeBean containing dynamic property constraints for targeted filtering
	 * @param pageNo primitive int indicating the zero-based offset page matrix tracking reference
	 * @param pageSize primitive int setting maximum record capacity constraints allowed per view index
	 * @return List containing filtered matching College record entities matching parameters
	 * @throws ApplicationException if general processing errors impact business logic steps
	 */
	public List<CollegeBean> search(CollegeBean bean, int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;
		ArrayList<CollegeBean> list = new ArrayList<CollegeBean>();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().trim().length() > 0) {
				sql.append(" AND NAME LIKE '" + bean.getName() + "%'");
			}
			if (bean.getState() != null && bean.getState().trim().length() > 0) {
				sql.append(" AND STATE LIKE '" + bean.getState() + "%'");
			}
			if (bean.getCity() != null && bean.getCity().trim().length() > 0) {
				sql.append(" AND CITY LIKE '" + bean.getCity() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + ", " + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CollegeBean currentBean = new CollegeBean();
				currentBean.setId(rs.getLong(1));
				currentBean.setName(rs.getString(2));
				currentBean.setAddress(rs.getString(3));
				currentBean.setState(rs.getString(4));
				currentBean.setCity(rs.getString(5));
				currentBean.setPhoneNo(rs.getString(6));
				currentBean.setCreatedBy(rs.getString(7));
				currentBean.setModifiedBy(rs.getString(8));
				currentBean.setCreatedDatetime(rs.getTimestamp(9));
				currentBean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(currentBean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}