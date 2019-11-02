package productionline;

/**
 * Creates an enum to hold the different Item types and their respective code. Contains a method
 * getType that returns the code for a specific item type.
 *
 * @author Nicholas Speth
 */
public enum ItemType {
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIO_MOBILE("AM"),
  VISUAL_MOBILE("VM");

  private String code;

  /**
   * Constructor for an ItemType object.
   *
   * @param code Holds the code for the ItemType that is being created.
   */
  ItemType(String code) {
    this.code = code;
  }

  /**
   * Method that returns the code attached to a specific ItemType when called.
   *
   * @return String code stored within an ItemType object.
   */
  public String getCode() {
    return code;
  }
}
