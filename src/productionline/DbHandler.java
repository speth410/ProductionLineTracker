package productionline;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

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

    final String jdbcDriver = "org.h2.Driver";
    final String dbUrl = "jdbc:h2:./res/ProductionLine";
    final String dbUser = "";
    Connection conn = null;

    try {
      Properties prop = new Properties();

      // Load properties from properties file.
      prop.load(new FileInputStream("res/properties"));

      String dbPassword = reverseString(prop.getProperty("password"));

      Class.forName(jdbcDriver);
      conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

      System.out.println("Database Initialized.");

      return conn;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Reverse a string using recursion.
   *
   * @param pw Holds the password found in the properties file.
   * @return
   */
  private static String reverseString(String pw) {
    // Paste the code for your reverseString method here.
    if (pw.isEmpty()) {
      return pw;
    }

    return reverseString(pw.substring(1)) + pw.charAt(0);
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
