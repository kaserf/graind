package de.graind.client.widgets.picasaImage;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import de.graind.client.model.PicasaImage;
import de.graind.client.model.Thumbnail;

public class PicasaImageWidget extends Composite implements PicasaImageWidgetView {

  private static PictureUiBinder uiBinder = GWT.create(PictureUiBinder.class);
  private Controller controller;
  private boolean isThumbnail;
  @UiField
  Image image;
  @UiField
  Style style;

  interface PictureUiBinder extends UiBinder<Widget, PicasaImageWidget> {
  }

  interface Style extends CssResource {
    String image();

    String imageSelected();
  }

  public PicasaImageWidget() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public PicasaImageWidget(boolean isThumbnail) {
    this();
    this.isThumbnail = isThumbnail;
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;
    setImage(controller.getImage());
    if (isThumbnail) {
      image.addClickHandler(new ImageClickHandler());
    }
  }

  @Override
  public void setSelected(boolean selected) {
    if (selected) {
      image.setStyleName(style.imageSelected());
    } else {
      image.setStyleName(style.image());
    }
  }

  public void setImage(PicasaImage picasaImage) {
    if (isThumbnail) {
      List<Thumbnail> thumbs = picasaImage.getThumbnails();
      int numThumbs = thumbs.size();
      if (numThumbs == 0) {
        Window.alert("No Thumbnail for picture available: " + picasaImage.getTitle());
      } else {
        // assume ordering in thumbnails: 0 < 1 < 2 < ... Take index 2 if
        // available, smaller ones else
        image.setUrl(thumbs.get(numThumbs < 3 ? numThumbs - 1 : 2).getUrl());
      }
      image.setTitle(picasaImage.getTitle());
      image.setAltText("Album: " + picasaImage.getAlbumId() + ", Title: " + picasaImage.getTitle());
    } else {
      image.setUrl(picasaImage.getUrl());
    }
  }

  private class ImageClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      controller.imageClicked();
    }
  }

}
