package de.graind.client.widgets.calendarPhoto;

import de.graind.client.model.PicasaImageBase;

public interface CalendarPhotoView {

  public interface Controller {

    PicasaImageBase getImage();

    void refetchImage();

    void setCurrentMonth(int currentMonth);

    int getCurrentMonth();
  }

  void init(Controller controller);

  void reload();
}
