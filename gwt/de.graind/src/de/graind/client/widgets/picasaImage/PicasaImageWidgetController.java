package de.graind.client.widgets.picasaImage;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.Window;

import de.graind.client.model.PicasaImage;
import de.graind.client.model.PicasaImageBase;
import de.graind.client.model.Thumbnail;
import de.graind.client.widgets.picasaImage.PicasaImageWidgetView.Controller;

public class PicasaImageWidgetController implements Controller {

  private PicasaImageWidgetView view;
  private PicasaImageBase image;
  private int id;

  private boolean isThumbnail = false;

  private List<ClickHandler> clickHandler = new LinkedList<PicasaImageWidgetView.Controller.ClickHandler>();

  /**
   * 
   * @param view
   * @param image
   * @param imageId
   * @param controller
   */
  public PicasaImageWidgetController(PicasaImageWidgetView view, PicasaImageBase image, int imageId, boolean isThumbnail) {
    this.view = view;
    this.image = image;
    this.id = imageId;
    this.isThumbnail = isThumbnail;

    view.init(this);
  }

  @Override
  public void imageClicked() {
    for (ClickHandler h : clickHandler) {
      h.onClick(id);
    }
  }

  @Override
  public void registerForClickEvent(ClickHandler handler) {
    this.clickHandler.add(handler);
  }

  @Override
  public String getImageUrl() {
    if (isThumbnail && image instanceof PicasaImage) {
      PicasaImage picasaImage = (PicasaImage) image;
      List<Thumbnail> thumbs = picasaImage.getThumbnails();
      int numThumbs = thumbs.size();
      if (numThumbs == 0) {
        Window.alert("No Thumbnail for picture available: " + picasaImage.getTitle());
        return image.getUrl();
      } else {
        // assume ordering in thumbnails: 0 < 1 < 2 < ... Take index 2 if
        // available, smaller ones else
        return (thumbs.get(numThumbs < 3 ? numThumbs - 1 : 2).getUrl());
      }
    } else {
      return image.getUrl();
    }
  }

  @Override
  public PicasaImageBase getImage() {
    return image;
  }

  @Override
  public void setSelected(boolean selected) {
    view.setSelected(selected);
  }

}
