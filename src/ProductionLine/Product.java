package ProductionLine;

/**
 * Product class holds product objects containing the products type, manufacturer, and name. The
 * class also implements get/set methods for these attributes contained in the Item interface.
 *
 * @author Nicholas Speth
 */
public abstract class Product implements Item {
  private int id;
  private String type;
  private String manufacturer;
  private String name;

  /**
   * Constructor for a product object.
   *
   * @param name Name of the product.
   * @param manufacturer The manufacturer of the product.
   * @param type The type of device. ie. Audio, Visual, Audio_Mobile, Visual_Mobile.
   */
  public Product(String name, String manufacturer, String type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  @Override
  public String toString() {
    return "Name: " + name + "\nManufacturer: " + manufacturer + "\nType: " + type;
  }
}

class Widget extends Product {
  public Widget(String name, String manufacturer, String type) {
    super(name, manufacturer, type);
  }
}
