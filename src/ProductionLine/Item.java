package ProductionLine;

/**
 * The Item interface declares methods to be implemented in the Product class.
 *
 * @author Nicholas Speth
 */
public interface Item {
  int getId();

  void setName(String name);

  String getName();

  void setManufacturer(String manufacturer);

  String getManufacturer();
}
