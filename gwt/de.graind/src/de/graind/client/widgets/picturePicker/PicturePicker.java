package de.graind.client.widgets.picturePicker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PicturePicker extends Composite {

  private static PicturePickerUiBinder uiBinder = GWT.create(PicturePickerUiBinder.class);

  interface PicturePickerUiBinder extends UiBinder<Widget, PicturePicker> {
  }

  public PicturePicker() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public PicturePicker(String firstName) {
    this();
  }
}
