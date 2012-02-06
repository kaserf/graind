package de.graind.client;

import de.graind.client.model.PicasaImageBase;

public interface GraindServerTestWidgetView {

  public interface Controller {
    void insertIntoDBTest(String username, PicasaImageBase[] images);
  }

  void init(Controller controller);
}
