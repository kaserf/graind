package de.graind.client.widgets;

import java.util.Date;

import de.graind.client.CalendarUtil;

public class DayWidgetController implements DayWidgetView.Controller {

  private DayWidgetView view;

  public DayWidgetController(DayWidgetView view) {
    this.view = view;

    view.init(this);
  }

  public String getDay() {
    int day = CalendarUtil.getDay(new Date());
    return (day < 10 ? "0" : "") + day;
  }

}
