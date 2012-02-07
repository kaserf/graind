package de.graind.client.widgets.picasaImage;

import de.graind.client.model.PicasaImageBase;

public interface PicasaImageWidgetView {

  public interface Controller {
    public void imageClicked();

    /**
     * returns the URL of the image to be displayed
     * 
     * @return
     */
    public String getImageUrl();

    public PicasaImageBase getImage();

    public void setImage(PicasaImageBase image);

    public void setSelected(boolean selected);

    public void registerForClickEvent(ClickHandler handler);

    public interface ClickHandler {
      public void onClick(int idOfImage);
    }
  }

  public void init(Controller controller);

  public void setSelected(boolean selected);

  public void reloadImage();
}
