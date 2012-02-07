package de.graind.client.widgets.calendarPhoto;

import de.graind.client.model.PicasaImageBase;

public interface CalendarPhotoView {

  public interface Controller {

    PicasaImageBase getImage();
  }

  void init(Controller controller);
}
