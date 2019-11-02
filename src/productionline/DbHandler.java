package productionline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class handles database fundtions such as starting/stopping the database and executing
 * queries.
 *
 * @author Nicholas Speth
 */
public class DbHandler {

  /**
   * Connect to the database.
   *
   * @return Returns a the Connection object so that database queries can be used elsewhere in the
   *     program.
   */
  public static Connection initializeDB() {
    // Check style flagged the following 4 lines due to having more than 2 consecutive
    // capital letters in the variable name.
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/ProductionLine";
    final String USER = "";
    final String PASS = ""; // Flagged by FindBugs as a security bug
    Connection conn = null;

    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      System.out.println("Database Initialized.");

      return conn;
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    } // end try catch
  }

  /**
   * Close a ResultSet.
   *
   * @param rs ResultSet
   */
  public static void close(ResultSet rs) {
    try {
      rs.close();
      System.out.println("ResultSet closed.");
    } catch (Exception e) {
      /* ignored */
    }
  }

  /**
   * Close a PreparedStatement.
   *
   * @param ps Prepared Statement
   */
  public static void close(PreparedStatement ps) {
    try {
      ps.close();
      System.out.println("PreparedStatement closed.");
    } catch (Exception e) {
      /* ignored */
    }
  }

  /**
   * Close a Connection.
   *
   * @param conn Connection
   */
  public static void close(Connection conn) {
    try {
      conn.close();
      System.out.println("Connection closed.");
    } catch (Exception e) {
      /* ignored */
    }
  }
}
