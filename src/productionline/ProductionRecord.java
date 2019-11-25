package productionline;

import java.util.Date;

/**
 * Class to hold ProductionRecord Objects and their attributes. Takes and holds information about
 * the Product and the data it was produced.
 *
 * @author Nicholas Speth
 */
public class ProductionRecord {
  private int productionNumber;
  private int productID;
  private String serialNumber;
  private Date dateProduced;
  private String productName;

  /**
   * Constructor for a production record that takes the products id and sets all other attributes to
   * 0.
   *
   * @param productID Holds the Id of the product.
   */
  ProductionRecord(int productID) {
    productionNumber = 0;
    serialNumber = "0";
    dateProduced = new Date();
  }

  /**
   * Constructor for a ProductionRecord object that takes 4 parameters and stores them.
   *
   * @param productionNumber Holds the productionNumber of the product.
   * @param productID Holds the product id of the product.
   * @param serialNumber Holds the serial number of the product.
   * @param dateProduced Hold the date that the product was produced.
   */
  ProductionRecord(int productionNumber, int productID, String serialNumber, Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  /**
   * Constructor for a ProductionRecord that takes a Product object and assigns a unique serial
   * number.
   *
   * @param product Holds a Product object.
   */
  ProductionRecord(Product product, int itemCount) {
    dateProduced = new Date();

    productName = product.getName();

    serialNumber =
        product.getManufacturer().substring(0, 3)
            + product.getType().getCode()
            + String.format("%05d", itemCount);
  }

  public int getProductionNumber() {
    return productionNumber;
  }

  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public int getProductID() {
    return productID;
  }

  public void setProductID(int productID) {
    this.productID = productID;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  // Flagged by FindBugs "may expose internal representation".
  public Date getDateProduced() {
    return dateProduced;
  }

  // Flagged by FindBugs "may expose internal representation".
  public void setDateProduced(Date dateProduced) {
    this.dateProduced = dateProduced;
  }

  @Override
  public String toString() {

    // If the product name was set, show productName instead of productID
    /*
    if (productName != null) {
      return "Prod. Num: "
          + productionNumber
          + " Product Name: "
          + productName
          + " Serial Num: "
          + serialNumber
          + " Date: "
          + dateProduced
          + "\n";
    } else {
      return "Prod. Num: "
          + productionNumber
          + " Product ID: "
          + productID
          + " Serial Num: "
          + serialNumber
          + " Date: "
          + dateProduced
          + "\n";
    }
     */
    return "Prod. Num: "
        + productionNumber
        + " Product Name: "
        + productName
        + " Serial Num: "
        + serialNumber
        + " Date: "
        + dateProduced
        + "\n";
  }
}
