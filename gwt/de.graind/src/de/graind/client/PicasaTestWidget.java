package de.graind.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class PicasaTestWidget extends Composite implements PicasaTestWidgetView {

  private HorizontalPanel hpanel;
  private Controller controller;

  public PicasaTestWidget() {
    hpanel = new HorizontalPanel();
    initWidget(hpanel);
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;
    GWT.log("picasa test widget init");
    this.controller.getAlbums();
  }

}
