package de.graind.client;

import com.google.gwt.accounts.client.AuthSubStatus;
import com.google.gwt.accounts.client.User;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class Calendar implements EntryPoint {

  private static final String API_KEY = "ABQIAAAAnkZoFwqQN8CkyJcz9_HK-xRPN_MKWMkv79GZUfaw5ZO6Y6XxJxSLFBqJP4hF05945dgmGiur4_tqpg";
  private String scope = "http://www.google.com/calendar/feeds";

  @Override
  public void onModuleLoad() {

    if (GData.isLoaded(GDataSystemPackage.CALENDAR)) {
      Window.alert("Calendar package is loaded");
      loadModule();
    } else {
      GData.loadGDataApi(API_KEY, new Runnable() {
        public void run() {
          loadModule();
        }
      }, GDataSystemPackage.CALENDAR);
    }
  }

  private void addLogoutButton() {
    final Button logoutButton = new Button("Logout");
    logoutButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        User.logout(new Runnable() {
          @Override
          public void run() {
            RootPanel.get().remove(logoutButton);
          }
        }, scope);
      }
    });

    if (User.getStatus(scope) != AuthSubStatus.LOGGED_OUT) {
      RootPanel.get().add(logoutButton);
    }
  }

  private void getCalendars() {
    // Create a CalenderService and authenticate
    CalendarService service = CalendarService.newInstance("Graind");

    String calendarsFeedUri = "http://www.google.com/calendar/feeds/default/allcalendars/full";
    service.getAllCalendarsFeed(calendarsFeedUri, new CalendarFeedCallback() {
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

  public void loadModule() {
    AuthSubStatus status = User.getStatus(scope);

    GWT.log("Userstatus: " + status.name());
    if (status == AuthSubStatus.LOGGED_OUT) {
      User.login(scope);
    } else {
      addLogoutButton();
      getCalendars();

      MonthlyWidget monthly = new MonthlyWidget();
      monthly.init(new MonthlyWidgetView.Controller() {

        @Override
        public int getMonth() {

          return 12;
        }

        @Override
        public void loadMonths(LoadDataCallback<Integer> callback) {
          callback.receiveData(12);
        }
      });
      RootPanel.get().add(monthly);
    }
  }
}
