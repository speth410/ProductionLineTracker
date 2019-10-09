package sample;

public class Screen implements ScreenSpec {
  private String resolution;
  private int refreshrate;
  private int responcetime;

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
