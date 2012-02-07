package de.graind.client.widgets.calendarPhoto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.graind.client.widgets.picasaImage.PicasaImageWidget;
import de.graind.client.widgets.picasaImage.PicasaImageWidgetController;

public class CalendarPhotoWidget extends Composite implements CalendarPhotoView {

  private static CalendarPhotoWidgetUiBinder uiBinder = GWT.create(CalendarPhotoWidgetUiBinder.class);
  private Controller controller;

  @UiField
  PicasaImageWidget image;
  private PicasaImageWidgetController imageController;
  @UiField
  VerticalPanel eventList;

  interface CalendarPhotoWidgetUiBinder extends UiBinder<Widget, CalendarPhotoWidget> {
  }

  public CalendarPhotoWidget() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;
    imageController = new PicasaImageWidgetController(image, controller.getImage(), 0, false);
  }
}
