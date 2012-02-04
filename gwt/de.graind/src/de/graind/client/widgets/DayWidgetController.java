package de.graind.client.widgets;

import java.util.Date;

import de.graind.client.CalendarUtil;

public class DayWidgetController implements DayWidgetView.Controller {

  private DayWidgetView view;

  public DayWidgetController(DayWidgetView view) {
    this.view = view;

    view.init(this);
  }

  @Override
  public String getDay() {
    int day = CalendarUtil.getDay(new Date());
    return (day < 10 ? "0" : "") + day;
  }

  @Override
  public String getMonthAndYear() {
    final Date today = new Date();
    return CalendarUtil.getMonthLabel(today) + " " + CalendarUtil.getYear(today);
  }

}
