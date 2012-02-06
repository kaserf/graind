package de.graind.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.graind.client.model.PicasaImageBase;
import de.graind.shared.Config;

public class GraindServerTestWidget extends Composite implements GraindServerTestWidgetView {

  private HorizontalPanel hpanel;
  private Controller controller;

  public GraindServerTestWidget() {
    hpanel = new HorizontalPanel();
    initWidget(hpanel);
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;

    PicasaImageBase[] images = new PicasaImageBase[12];
    for (int i = 0; i < images.length; i++) {
      PicasaImageBase img = new PicasaImageBase();
      img.setHeight((long) 400);
      img.setWidth((long) 800);
      img.setUrl("http://www.google.de/search?q=" + i);

      images[i] = img;
    }
    this.controller.insertIntoDBTest(Config.USERNAME, images);
  }
}
