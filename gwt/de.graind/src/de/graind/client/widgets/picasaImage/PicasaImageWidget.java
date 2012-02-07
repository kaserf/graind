package de.graind.client.widgets.picasaImage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

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
    image.setUrl(controller.getImageUrl());
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

  private class ImageClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      controller.imageClicked();
    }
  }

}
