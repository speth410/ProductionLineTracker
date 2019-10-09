package sample;

public class AudioPlayer extends Product implements MultimediaControl {
  private String supportedAudioFormats;
  private String supportedPlaylistFormats;

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
