package ProductionLine;

/** ScreenSpec interface declares methods used in the Screen class. */
public interface ScreenSpec {
  public String getResolution();

  public int getRefreshRate();

  public int getResponseTime();
}
