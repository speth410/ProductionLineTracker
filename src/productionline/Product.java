package productionline;

/**
 * Product class holds product objects containing the products type, manufacturer, and name. The
 * class also implements get/set methods for these attributes contained in the Item interface.
 *
 * @author Nicholas Speth
 */
public abstract class Product implements Item {
  private int id;
  private ItemType type;
  private String manufacturer;
  private String name;

  /**
   * Constructor for a product object.
   *
   * @param name Name of the product.
   * @param manufacturer The manufacturer of the product.
   * @param type The type of device. ie. Audio, Visual, Audio_Mobile, Visual_Mobile.
   */
  public Product(int id, String name, String manufacturer, ItemType type) {
    this.id = id;
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * Constructor for a Product object.
   *
   * @param name Holds the name of the product.
   * @param manufacturer Holds the manufacturer of the product.
   * @param type Holds the type of the product.
   */
  public Product(String name, String manufacturer, ItemType type) {
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

  public ItemType getType() {
    return type;
  }

  @Override
  public String toString() {
    return "Name: " + name + "\nManufacturer: " + manufacturer + "\nType: " + type;
  }
}
