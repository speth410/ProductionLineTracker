package ProductionLine;

/**
 * Extends the Product class to add functionality specific to Audio devices. Implements methods
 * declared in the MultimediaControl interface.
 *
 * @author Nicholas Speth
 */
public class AudioPlayer extends Product implements MultimediaControl {
  private String supportedAudioFormats;
  private String supportedPlaylistFormats;

  /**
   * Constructor for AudioPlayer objects. Also creates a product object using super().
   *
   * @param name Holds the name of the product.
   * @param manufacturer Holds the manufacturer of the product.
   * @param supportedAudioFormats Holds the supported audio formats for the product.
   * @param supportedPlaylistFormats Holds the supported playlist formats for the product.
   */
  public AudioPlayer(
      String name,
      String manufacturer,
      String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, "AUDIO");
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  @Override
  public void next() {
    System.out.println("Next");
  }

  @Override
  public void previous() {
    System.out.println("Previous");
  }

  @Override
  public void play() {
    System.out.println("Play");
  }

  @Override
  public void stop() {
    System.out.println("Stop");
  }

  @Override
  public String toString() {
    return super.toString()
        + "\nAudio Spec: "
        + supportedAudioFormats
        + "\nMedia Type: "
        + supportedPlaylistFormats;
  }
}
