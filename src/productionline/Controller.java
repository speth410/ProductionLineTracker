package productionline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * JavaFX Controller.
 *
 * @author Nicholas Speth
 */
public class Controller {
  @FXML protected TableView<Product> tvProducts;
  @FXML private TextField txtName;
  @FXML private TextField txtManufacturer;
  @FXML private ChoiceBox<ItemType> cbItemType;
  @FXML private Button btnAddProduct;
  @FXML private ListView<Product> lvChooseProduct;
  @FXML private ComboBox<String> cboQuantity;
  @FXML private Button btnRecord;
  @FXML private TextArea taLog;
  @FXML private TableColumn<String, Product> columnName;
  @FXML private TableColumn<String, Product> columnManufacturer;
  @FXML private TableColumn<String, Product> columnType;
  @FXML private Button btnSubmit;
  @FXML private Label lblUsername;
  @FXML private Label lblEmail;
  @FXML private TextField txtEmployeeName;

  private ArrayList<Product> productLine = new ArrayList<>();
  private ArrayList<ProductionRecord> productionRun = new ArrayList<>();
  private ArrayList<ProductionRecord> productionLog = new ArrayList<>();
  private ItemType itemType;
  private int itemCount;

  /** Method to test the AudioPlayer, Screen, and MoviePlayer classes. */
  private static void testMultimedia() {
    AudioPlayer newAudioProduct =
        new AudioPlayer(
            "DP-X1A", "Onkyo", "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
    Screen newScreen = new Screen("720x480", 40, 22);
    MoviePlayer newMovieProduct =
        new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen, MonitorType.LCD);
    ArrayList<MultimediaControl> productList = new ArrayList<MultimediaControl>();
    productList.add(newAudioProduct);
    productList.add(newMovieProduct);
    for (MultimediaControl p : productList) {
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();
    }
  }

  /** First method executed when controller is called. */
  @FXML
  private void initialize() {
    cboQuantity.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    cboQuantity.setEditable(true);
    cboQuantity.getSelectionModel().selectFirst();

    cbItemType.getItems().addAll(ItemType.values());
    cbItemType.getSelectionModel().selectFirst();
    testMultimedia();

    setupProductionLineTable();
    loadProductList();

    loadProductionLog();
  }

  /**
   * Builds an SQL statement using input from textboxes and passes it to Main.exSQL. The input is
   * also used to create Product objects that are used to populate the TableView.
   *
   * @param event stores the mouse event.
   */
  @FXML
  void handleAddProduct(MouseEvent event) {
    itemType = cbItemType.getValue();
    int prodId = 0;

    // Get a Connection to the database
    Connection conn = DbHandler.initializeDB();

    if (conn != null) {
      try {

        // Make sure that the user has entered a manufacturer, name, and item type
        if (itemType.getCode() != null
            && !txtManufacturer.getText().isEmpty()
            && !txtName.getText().isEmpty()) {

          String sql = "INSERT INTO PRODUCT(TYPE, MANUFACTURER, NAME) VALUES (?,?,?)";
          PreparedStatement ps = conn.prepareStatement(sql);

          ps = conn.prepareStatement(sql);
          ps.setString(1, itemType.getCode());
          ps.setString(2, txtManufacturer.getText());
          ps.setString(3, txtName.getText());
          ps.executeUpdate();

          DbHandler.close(ps);
          DbHandler.close(conn);
        } else {

          // Notify the user that something was missing
          // FindBugs flags this as "Database resource not closed on all paths" although the
          // Connection and Prepared Statement are both closed above.
          Alert alert =
              new Alert(
                  Alert.AlertType.ERROR,
                  "Please enter an item type, manufacturer, and name for the product.",
                  ButtonType.OK);

          alert.showAndWait();
        }
      } catch (SQLException e) {

        // Notify the user that there was an error retrieving data from the database.
        Alert alert =
            new Alert(
                Alert.AlertType.ERROR, "Error retrieving products from database!", ButtonType.OK);

        alert.showAndWait();
        e.printStackTrace();
      }
    }

    DbHandler.close(conn);

    loadProductList();
  }

  /**
   * Adds a ProductionRecord object based on the user selected product stored in lvChooseProduct to
   * the productionRun ArrayList.
   *
   * @param event holds a MouseEvent.
   */
  @FXML
  void handleRecordProduction(MouseEvent event) {

    // Check to make sure that the user selected a product and a quantity.
    if (lvChooseProduct.getSelectionModel().getSelectedItem() != null
        && Integer.parseInt(cboQuantity.getValue()) > 0) {

      // Create ProductionRecord object(s) using the product selected.
      // The amount created is equal to the quantity selected by the user.
      for (int i = 0; i < Integer.parseInt(cboQuantity.getValue()); i++) {
        productionRun.add(
            new ProductionRecord(lvChooseProduct.getSelectionModel().getSelectedItem(), itemCount));

        itemCount++;
      }

      addToProductionDB(productionRun);
      loadProductionLog();
      showProduction();
    } else {

      // Notify the user that they need to select a product and a quantity.
      Alert alert =
          new Alert(
              Alert.AlertType.ERROR, "Please select a product and a quantity.", ButtonType.OK);

      alert.showAndWait();
    }
  }

  /**
   * For each ProductionRecord object stored within the productionRecord ArrayList insert the
   * objects productId, serialNumber, and dateProduced into a new row in the database.
   *
   * @param productionRun ArrayList that stores ProductionRecord objects.
   */
  private void addToProductionDB(ArrayList<ProductionRecord> productionRun) {
    Connection conn = DbHandler.initializeDB();

    try {
      String sql =
          "INSERT INTO PRODUCTIONRECORD(PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED) VALUES (?,?,?)";
      PreparedStatement ps = conn.prepareStatement(sql);

      // For every ProductionRecord object stored in the productionRun ArrayList execute.
      for (ProductionRecord pr : productionRun) {

        // Set the ProductionRecord objects productId
        pr.setProductID(getProductId(pr.getProductName()));

        // Set the parameters for the PreparedStatement using the current ProductionRecord objects
        // fields.
        ps.setInt(1, pr.getProductID());
        ps.setString(2, pr.getSerialNumber());
        ps.setDate(3, new java.sql.Date(new Date().getTime()));
        ps.executeUpdate();
      }

      // Clear the productionRun ArrayList to prevent duplicates
      productionRun.clear();

      DbHandler.close(ps);
      DbHandler.close(conn);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the productId from the Product table corresponding to the product name passed in.
   *
   * @param name Holds the name of the product.
   * @return Return the Id of the product.
   */
  private int getProductId(String name) {
    Connection conn = DbHandler.initializeDB();
    int id = 0;

    try {
      String sql = "SELECT ID FROM PRODUCT WHERE NAME = ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, name);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        id = rs.getInt("ID");
      }

      DbHandler.close(rs);
      DbHandler.close(ps);

    } catch (SQLException e) {
      e.printStackTrace();
    }

    DbHandler.close(conn);

    return id;
  }

  /**
   * Populate the productionLog ArrayList with ProductionRecord objects created with the contents of
   * the PRODUCTIONRECORD table.
   */
  private void loadProductionLog() {
    Connection conn = DbHandler.initializeDB();

    if (conn != null) {
      try {
        String sql = "SELECT * FROM PRODUCTIONRECORD";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
          itemCount++;
          int prodNum = rs.getInt("PRODUCTION_NUM");
          int prodId = rs.getInt("PRODUCT_ID");
          String serialNum = rs.getString("SERIAL_NUM");
          Timestamp date = rs.getTimestamp("DATE_PRODUCED");

          // If the object does not already exist within the productionLog ArrayList then add it.
          if (!productionLog.contains(new ProductionRecord(prodNum, prodId, serialNum, date))) {
            productionLog.add(new ProductionRecord(prodNum, prodId, serialNum, date));
          }
        }

        showProduction();

        DbHandler.close(rs);
        DbHandler.close(ps);
      } catch (SQLException e) {
        e.printStackTrace();
      }

      DbHandler.close(conn);
    }
  }

  /**
   * Print the toString() of each ProductionRecord object stored within productionLog to the taLog
   * TextArea on the Production Log tab.
   */
  private void showProduction() {
    Connection conn = DbHandler.initializeDB();

    if (conn != null) {
      for (ProductionRecord pr : productionLog) {
        try {
          // Using the productID of the current ProductionRecord object get the corresponding name
          // from the PRODUCT table.
          String sql = "SELECT NAME FROM PRODUCT WHERE ID = ?";
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, pr.getProductID());

          ResultSet rs = ps.executeQuery();

          if (rs.next()) {
            pr.setProductName(rs.getString("NAME"));
          }

          // If this production doesn't already exist on the taLog then append it.
          if (!taLog.getText().contains(pr.toString())) {
            taLog.appendText(pr.toString());
          }

          DbHandler.close(rs);
          DbHandler.close(ps);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      DbHandler.close(conn);
    }
  }

  /**
   * Setup the TableColumns to store the name, manufacturer, and type fields of the Product class.
   * Set the tvProducts TableView to display the contents of productList.
   */
  private void setupProductionLineTable() {

    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    columnType.setCellValueFactory(new PropertyValueFactory<>("type"));

    ObservableList<Product> productList = getProductList();

    // Populate the TableView with the contents of productList.
    tvProducts.setItems(productList);
  }

  /**
   * Load the contents of the PRODUCT table into the productLine ArrayList and populate the
   * tvProducts TableView and the lvChooseProduct ListView.
   */
  private void loadProductList() {
    Connection conn = DbHandler.initializeDB();
    productLine.clear();

    if (conn != null) {
      try {
        String sql = "SELECT * FROM PRODUCT";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
          int id = rs.getInt("ID");
          String name = rs.getString("NAME");
          String code = rs.getString("TYPE");
          String manufacturer = rs.getString("MANUFACTURER");

          // Get the ItemType that corresponds to the code stored in the db, Create a Product object
          // and store inside productLine
          for (ItemType type : ItemType.values()) {
            if (type.getCode().equals(code)) {
              productLine.add(new Widget(id, name, manufacturer, type));
            }
          }
        }

        // Populate product/produce tabs
        tvProducts.getItems().clear();
        tvProducts.getItems().addAll(productLine);

        lvChooseProduct.getItems().clear();
        lvChooseProduct.getItems().addAll(productLine);

        DbHandler.close(rs);
        DbHandler.close(ps);
        DbHandler.close(conn);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Convert the ArrayList productLine to an ObservableList and return it.
   *
   * @return An ObservableList of all of our Product Objects.
   */
  private ObservableList<Product> getProductList() {
    return FXCollections.observableArrayList(productLine);
  }

  /**
   * When the user clicks the Submit button, create a new Employee object using the name entered
   * into the txtEmployeeName TextField. Display the username and email generated by the Employee
   * class.
   *
   * @param event hold a MouseEvent.
   */
  @FXML
  void handleSubmitName(MouseEvent event) {
    String name = txtEmployeeName.getText();

    Employee employee = new Employee(name, "password");

    lblUsername.setText(employee.getUsername());
    lblEmail.setText(employee.getEmail());
  }
}
