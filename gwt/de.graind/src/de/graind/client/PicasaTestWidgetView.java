package de.graind.client;

public interface PicasaTestWidgetView {

  public interface Controller {
    void getAlbums();
  }

  void init(Controller controller);
}
