package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/** JavaFX Controller.
 * @author Nicholas Speth
 */
public class Controller {
  @FXML private TextField txtName;

  @FXML private TextField txtManufacturer;

  @FXML private ChoiceBox<?> cbItemType;

  @FXML private Button btnAddProduct;

  @FXML protected static TableView<?> tvProducts;

  @FXML private ListView<?> lvChooseProduct;

  @FXML private ComboBox<String> cboQuantity;

  @FXML private Button btnRecord;

  @FXML private TextArea taLog;

  /**
   * Builds an SQL statement using input from textboxes and passes it to Main.exSQL
   * @param event stores the mouse event.
   */
  @FXML
  void handleAddProduct(MouseEvent event) {
    String sql =
        "INSERT INTO PRODUCT(TYPE, MANUFACTURER, NAME) VALUES ('"
            + "AUDIO'"
            + ", '"
            + txtManufacturer.getText()
            + "', '"
            + txtName.getText()
            + "');";

    // Pass formatted sql statement to Main.exSQL
    Main.exSql(sql);          //FindBug: nonconstant string passes to execute on an SQL statement
  }

  @FXML
  void handleRecordProduction(ActionEvent event) {

  }

  /** First method executed when controller is called. */
  @FXML
  private void initialize() {
    cboQuantity.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    cboQuantity.getSelectionModel().selectFirst();
    cboQuantity.setEditable(true);
  }
}
