package in.co.rays.proj4.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBC Utility class that acts as a Data Access wrapper managing database connection pooling.
 * It encapsulates a C3P0 connection pool implementation, driven by environment values 
 * extracted from an application resource bundle.
 * 
 * @author vinay
 * @version 1.0
 */
public final class JDBCDataSource {

	/**
	 * Static self-reference tracker mapping the Singleton runtime instance profile boundary.
	 */
	private static JDBCDataSource jds = null;

	/**
	 * Internal storage object handling pooling instances of C3P0 structural configurations.
	 */
	private ComboPooledDataSource cpds = null;

	/**
	 * Resource configuration bundle pulling database property attributes from system configuration files.
	 */
	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");

	/**
	 * Private default constructor ensuring Singleton structural design.
	 * Instantiates and configures the core C3P0 connection pooling profile.
	 */
	private JDBCDataSource() {
		try {
			cpds = new ComboPooledDataSource();
			cpds.setDriverClass(rb.getString("driver"));
			cpds.setJdbcUrl(rb.getString("url"));
			cpds.setUser(rb.getString("username"));
			cpds.setPassword(rb.getString("password"));
			cpds.setInitialPoolSize(Integer.parseInt(rb.getString("initialpoolsize")));
			cpds.setAcquireIncrement(Integer.parseInt(rb.getString("acquireincrement")));
			cpds.setMaxPoolSize(Integer.parseInt(rb.getString("maxpoolsize")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Synchronizes and extracts the running Singleton reference pointer configuration.
	 * 
	 * @return The verified unique instance allocation of {@link JDBCDataSource}
	 */
	public static JDBCDataSource getInstance() {
		if (jds == null) {
			jds = new JDBCDataSource();
		}
		return jds;
	}

	/**
	 * Opens and returns an available connection wrapper tracking directly out of the data pool pipeline.
	 * 
	 * @return A structural {@link Connection} database context entity instance, or null if setup fails
	 */
	public static Connection getConnection() {
		try {
			return getInstance().cpds.getConnection();
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Safely frees standard operational connection artifacts, dealing directly with explicit 
	 * null safety verification logic checks.
	 * 
	 * @param conn The database connection channel context to drop
	 * @param stmt The structural database compilation processor context to close
	 * @param rs   The raw streaming result grid database collection wrapper to clear
	 */
	public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Overloaded fallback handling quick structural cleanups across base operations 
	 * that bypass traditional ResultSet allocations.
	 * 
	 * @param conn The database connection channel context to drop
	 * @param stmt The structural database compilation processor context to close
	 */
	public static void closeConnection(Connection conn, Statement stmt) {
		closeConnection(conn, stmt, null);
	}

	/**
	 * Overloaded fallback handling quick structural cleanups across target standalone connections.
	 * 
	 * @param conn The database connection channel context to drop
	 */
	public static void closeConnection(Connection conn) {
		closeConnection(conn, null);
	}
}