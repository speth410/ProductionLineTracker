package ProductionLine;

/**
 * MoviePlayer class extends the Product class to add functionality specific to movie players.
 * Implements methods declared in the MultimediaControl interface.
 *
 * @author Nicholas Speth
 */
public class MoviePlayer extends Product implements MultimediaControl {

  private Screen screen;
  private MonitorType monitorType;

  /**
   * @param name Holds the name of the product.
   * @param manufacturer Holds the manufacturer of the product.
   * @param screen Holds a Screen Object.
   * @param monitorType Holds the monitor type.
   */
  public MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, "VISUAL");
    this.screen = screen;
    this.monitorType = monitorType;
  }

  @Override
  public void play() {
    System.out.println("Playing movie");
  }

  @Override
  public void stop() {
    System.out.println("Stopping movie");
  }

  @Override
  public void previous() {
    System.out.println("Previous movie");
  }

  @Override
  public void next() {
    System.out.println("Next movie");
  }

  @Override
  public String toString() {
    return super.toString() + "\nScreen: " + screen.toString() + "\nMonitor Type: " + monitorType;
  }
}
