package de.graind.client.widgets.imagePicker.image;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import de.graind.client.model.PicasaImage;
import de.graind.client.model.Thumbnail;

public class PicasaImageWidget extends Composite implements PicasaImageWidgetView {

  private static PictureUiBinder uiBinder = GWT.create(PictureUiBinder.class);
  private Controller controller;
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

  @Override
  public void init(Controller controller) {
    this.controller = controller;
    setImage(controller.getImage());
    image.addClickHandler(new ImageClickHandler());
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
    List<Thumbnail> thumbs = picasaImage.getThumbnails();
    if (thumbs.size() > 0) {
      image.setUrl(thumbs.get(0).getUrl());
    }
    image.setTitle(picasaImage.getTitle());
    image.setAltText("Album: " + picasaImage.getAlbumId() + ", Title: " + picasaImage.getTitle());
  }

  private class ImageClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      controller.imageClicked();
    }
  }

}
