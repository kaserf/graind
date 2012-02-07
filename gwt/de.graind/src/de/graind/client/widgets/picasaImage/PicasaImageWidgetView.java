package de.graind.client.widgets.picasaImage;

import de.graind.client.model.PicasaImage;

public interface PicasaImageWidgetView {

  public interface Controller {
    public void imageClicked();

    public PicasaImage getImage();

    void setSelected(boolean selected);
  }

  public void init(Controller controller);

  public void setSelected(boolean selected);
}
