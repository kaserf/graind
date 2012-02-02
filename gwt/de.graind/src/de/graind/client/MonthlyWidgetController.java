package de.graind.client;

import com.google.gwt.gdata.client.DateTime;
import com.google.gwt.gdata.client.EventEntry;
import com.google.gwt.gdata.client.GData;
import com.google.gwt.gdata.client.GDataSystemPackage;
import com.google.gwt.gdata.client.calendar.CalendarEventFeed;
import com.google.gwt.gdata.client.calendar.CalendarEventFeedCallback;
import com.google.gwt.gdata.client.calendar.CalendarEventQuery;
import com.google.gwt.gdata.client.calendar.CalendarService;
import com.google.gwt.gdata.client.impl.CallErrorException;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.graind.shared.Config;

public class MonthlyWidgetController implements MonthlyWidgetView.Controller {

  private MonthlyWidgetView view;

  private CalendarService service;

  public MonthlyWidgetController(MonthlyWidgetView view) {
    this.view = view;

    if (GData.isLoaded(GDataSystemPackage.CALENDAR)) {
      Window.alert("Calendar package is loaded");
      gdataLoaded();
    } else {
      GData.loadGDataApi(Config.API_KEY, new Runnable() {
        public void run() {
          gdataLoaded();
        }
      }, GDataSystemPackage.CALENDAR);
    }
  }

  private void gdataLoaded() {
    this.service = CalendarService.newInstance(Config.applicationName);
    // initCalendar();
    MonthlyWidgetController.this.view.init(MonthlyWidgetController.this);
  }

  /**
   * get all event entries for a given month.
   * 
   * @param year
   * @param month
   *          a month between 1 and 12
   * @param callback
   */
  @Override
  public void fetchEventsForMonth(int year, int month, final AsyncCallback<EventEntry[]> callback) {
    CalendarEventQuery query = CalendarEventQuery
        .newInstance("http://www.google.com/calendar/feeds/default/private/full");

    DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd");
    DateTime max = DateTime.newInstance(format.parse(year + "-" + month + "-"
        + CalendarUtil.getDaysOfMonth(year, month)));
    DateTime min = DateTime.newInstance(format.parse(year + "-" + month + "-01"));

    query.setMaximumStartTime(max);
    query.setMinimumStartTime(min);

    // TODO: add entries that start at an earlier date but are still valid

    service.getEventsFeed(query, new CalendarEventFeedCallback() {
      @Override
      public void onFailure(CallErrorException caught) {
        callback.onFailure(caught);
      }

      @Override
      public void onSuccess(CalendarEventFeed result) {
        callback.onSuccess(result.getEntries());
      }
    });
  }

}
