package ProductionLine;

/**
 * Class Screen holds Screen objects, their attributes, and implements methods declared in the
 * ScreenSpec interface.
 *
 * @author Nicholas Speth
 */
public class Screen implements ScreenSpec {
  private String resolution;
  private int refreshrate;
  private int responcetime;

  /**
   * Constructor for new Screen objects.
   *
   * @param resolution Holds the resolution of the screen.
   * @param refreshrate Holds the refresh rate of the screen.
   * @param responcetime Holds the responce time of the screen.
   */
  public Screen(String resolution, int refreshrate, int responcetime) {
    this.resolution = resolution;
    this.refreshrate = refreshrate;
    this.responcetime = responcetime;
  }

  @Override
  public String getResolution() {
    return resolution;
  }

  @Override
  public int getRefreshRate() {
    return refreshrate;
  }

  @Override
  public int getResponseTime() {
    return responcetime;
  }

  @Override
  public String toString() {
    return "Resolution: "
        + resolution
        + "\nRefresh Rate: "
        + refreshrate
        + "\nResponce Time: "
        + responcetime;
  }
}
