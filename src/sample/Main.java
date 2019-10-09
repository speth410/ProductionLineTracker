package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class.
 *
 * @author Nick Speth
 * @brief 9/21/19
 */
public class Main extends Application {
  /**
   * the starting point of a JavaFx program.
   *
   * @param primaryStage the first thing the user sees
   * @throws Exception stores the exception thrown.
   */
  private static Statement stmt;

  @Override
  public void start(Stage primaryStage) throws Exception {
    initializeDB();

    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Hello World");
    primaryStage.setScene(new Scene(root, 600, 400));
    primaryStage.show();
  }

  /** Connect the program and the database. */
  protected void initializeDB() {
    // Check style flagged the following 4 lines due to having more than 2 consecutive
    // capital letters in the variable name.
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/ProductionLine";
    final String USER = "";
    final String PASS = ""; // Flagged by FindBugs as a security bug
    Connection conn = null;

    try {
      Class.forName(JDBC_DRIVER);
      conn =
          DriverManager.getConnection(
              DB_URL, USER, PASS); // FindBugs: fail to close database resource.
      stmt = conn.createStatement();
    } catch (Exception ex) {
      ex.printStackTrace();
    } // end try catch
  } // end method initializeDB

  /**
   * Executes the sql statement that is passed to it.
   *
   * @param sql stores the sql statement passed to the method.
   */
  protected static void exSql(String sql) {
    try {
      stmt.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } // end try catch
  } // end method exSQL

  public static void main(String[] args) {
    launch(args);
  } // end method main
} // end class Main
