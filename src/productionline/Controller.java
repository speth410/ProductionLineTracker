package productionline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * JavaFX Controller.
 *
 * @author Nicholas Speth
 */
public class Controller {
  @FXML private TextField txtName;

  @FXML private TextField txtManufacturer;

  @FXML private ChoiceBox<ItemType> cbItemType;

  @FXML private Button btnAddProduct;

  @FXML protected TableView<Product> tvProducts;

  @FXML private ListView<?> lvChooseProduct;

  @FXML private ComboBox<String> cboQuantity;

  @FXML private Button btnRecord;

  @FXML private TextArea taLog;

  @FXML private TableColumn<String, Product> columnName;

  @FXML private TableColumn<String, Product> columnManufacturer;

  @FXML private TableColumn<String, Product> columnType;

  private ArrayList<Product> productLine = new ArrayList<>();

  /**
   * Builds an SQL statement using input from textboxes and passes it to Main.exSQL. The input is
   * also used to create Product objects that are used to populate the TableView.
   *
   * @param event stores the mouse event.
   */
  @FXML
  void handleAddProduct(MouseEvent event) {
    ItemType itemType = cbItemType.getValue();

    // Get a Connection to the database
    Connection conn = DbHandler.initializeDB();
    PreparedStatement ps = null;

    String sql = "INSERT INTO PRODUCT(TYPE, MANUFACTURER, NAME) VALUES (?,?,?)";

    if (conn != null) {
      try {
        ps = conn.prepareStatement(sql);
        ps.setString(1, itemType.getCode());
        ps.setString(2, txtManufacturer.getText());
        ps.setString(3, txtName.getText());
        ps.executeUpdate();

      } catch (SQLException e) {
        e.printStackTrace();
      }
      DbHandler.close(ps);
      DbHandler.close(conn);
    }

    // Create a Product object from the user input and an ArrayList to hold them.
    Widget addProduct = new Widget(txtName.getText(), txtManufacturer.getText(), itemType);
    productLine.add(addProduct);
    System.out.println(productLine.toString());

    // Add the object to the TableView
    tvProducts.getItems().addAll(productLine);
  }


  @FXML
  void handleRecordProduction(ActionEvent event) {
    // Not implemented yet.
  }

  /** First method executed when controller is called. */
  @FXML
  private void initialize() {
    cboQuantity.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    cboQuantity.getSelectionModel().selectFirst();
    cboQuantity.setEditable(true);
    cbItemType.getItems().addAll(ItemType.values());
    cbItemType.getSelectionModel().selectFirst();
    testMultimedia();

    ProductionRecord pr = new ProductionRecord(0, 3, "1", new Date());
    taLog.setText(pr.toString());

    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    columnType.setCellValueFactory(new PropertyValueFactory<>("type"));

    ObservableList<Product> productList = getProductList();

    tvProducts.setItems(productList);
  }

  /**
   * Convert the ArrayList productLine to an ObservableList and return it.
   *
   * @return An ObservableList of all of our Product Objects.
   */
  private ObservableList<Product> getProductList() {
    return FXCollections.observableArrayList(productLine);
  }

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
}
