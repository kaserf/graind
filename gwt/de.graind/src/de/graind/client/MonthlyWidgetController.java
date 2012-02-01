package de.graind.client;

import com.google.gwt.gdata.client.DateTime;
import com.google.gwt.gdata.client.EventEntry;
import com.google.gwt.gdata.client.GData;
import com.google.gwt.gdata.client.GDataSystemPackage;
import com.google.gwt.gdata.client.calendar.CalendarEntry;
import com.google.gwt.gdata.client.calendar.CalendarEventFeed;
import com.google.gwt.gdata.client.calendar.CalendarEventFeedCallback;
import com.google.gwt.gdata.client.calendar.CalendarEventQuery;
import com.google.gwt.gdata.client.calendar.CalendarFeed;
import com.google.gwt.gdata.client.calendar.CalendarFeedCallback;
import com.google.gwt.gdata.client.calendar.CalendarService;
import com.google.gwt.gdata.client.impl.CallErrorException;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;

import de.graind.shared.Config;

public class MonthlyWidgetController implements MonthlyWidgetView.Controller {

  public MonthlyWidgetController() {
    if (GData.isLoaded(GDataSystemPackage.CALENDAR)) {
      Window.alert("Calendar package is loaded");
      initCalendar();
    } else {
      GData.loadGDataApi(Config.API_KEY, new Runnable() {
        public void run() {
          initCalendar();
        }
      }, GDataSystemPackage.CALENDAR);
    }
  }

  private void initCalendar() {
    // Create a CalenderService and authenticate
    CalendarService service = CalendarService.newInstance(Config.applicationName);

    service.getAllCalendarsFeed(Config.calendarsFeedUri, new CalendarFeedCallback() {
      @Override
      public void onFailure(CallErrorException caught) {
        Window.alert("Oops, there was an unexpected error.");
      }

      @Override
      public void onSuccess(CalendarFeed result) {
        // Window.alert("You have " + result.getEntries().length +
        // " calendars.");
        for (CalendarEntry entry : result.getEntries()) {
          System.out.println("Calendar: " + entry.getTitle().getText());
        }
      }
    });

    // TODO: remove query, make a clear api for that
    CalendarEventQuery query = CalendarEventQuery
        .newInstance("http://www.google.com/calendar/feeds/default/private/full");

    // DateTime max = DateTime.newInstance(new Date(), true);
    DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd");
    DateTime max = DateTime.newInstance(format.parse("2012-02-29"));
    DateTime min = DateTime.newInstance(format.parse("2012-02-01"));

    query.setMaximumStartTime(max);
    query.setMinimumStartTime(min);

    System.out.println("minimum start time: " + query.getMinimumStartTime().getDate().toString());
    System.out.println("maximum start time: " + query.getMaximumStartTime().getDate().toString());
    service.getEventsFeed(query, new CalendarEventFeedCallback() {
      @Override
      public void onFailure(CallErrorException caught) {
        Window.alert("Oops, there was an unexpected error.");
      }

      @Override
      public void onSuccess(CalendarEventFeed result) {
        // Window.alert("You have " + result.getEntries().length + " events.");
        for (EventEntry entry : result.getEntries()) {
          System.out.println("Entry: " + entry.getTitle().getText() + " from: "
              + entry.getTimes()[0].getStartTime().getDate().toString());
        }
      }
    });
  }

  @Override
  public int getMonth() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void loadMonths(LoadDataCallback<Integer> callback) {
    // TODO Auto-generated method stub

  }

}
