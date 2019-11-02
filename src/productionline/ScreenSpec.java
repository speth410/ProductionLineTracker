package productionline;

/**
 * ScreenSpec interface declares methods used in the Screen class.
 *
 * @author Nicholas Speth
 */
public interface ScreenSpec {
  String getResolution();

  int getRefreshRate();

  int getResponseTime();
}
