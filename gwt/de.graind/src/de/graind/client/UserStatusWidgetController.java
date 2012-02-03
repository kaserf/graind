package de.graind.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gdata.client.GData;
import com.google.gwt.gdata.client.GDataSystemPackage;
import com.google.gwt.gdata.client.calendar.CalendarFeed;
import com.google.gwt.gdata.client.calendar.CalendarFeedCallback;
import com.google.gwt.gdata.client.calendar.CalendarService;
import com.google.gwt.gdata.client.impl.CallErrorException;

import de.graind.shared.Config;

public class UserStatusWidgetController implements UserStatusWidgetView.Controller {

  private UserStatusWidgetView view;
  private CalendarService service;

  public UserStatusWidgetController(UserStatusWidgetView view) {
    this.view = view;

    // TODO: check if we need calendar AND gbase (for picasa)
    if (!GData.isLoaded(GDataSystemPackage.CALENDAR)) {
      GData.loadGDataApi(Config.API_KEY, new Runnable() {
        public void run() {
          GWT.log("Login has loaded gdata");
          UserStatusWidgetController.this.gdataLoaded();
        }
      }, GDataSystemPackage.CALENDAR);
    } else {
      gdataLoaded();
    }
  }

  public void gdataLoaded() {
    service = CalendarService.newInstance(Config.applicationName);
    service.getAllCalendarsFeed("http://www.google.com/calendar/feeds/default/allcalendars/full",
        new CalendarFeedCallback() {

          @Override
          public void onSuccess(CalendarFeed result) {
            GWT.log("feed link: " + result.getFeedLink().getHref());
            GWT.log("feed id: " + result.getId().getValue());
            GWT.log("calendar id: " + result.getEntries()[0].getId().getValue());
            GWT.log("calendar name: " + result.getEntries()[0].getTitle().getText());
            GWT.log("calendar author email: " + result.getEntries()[0].getAuthors()[0].getEmail().getValue());
            GWT.log("calendar author name: " + result.getEntries()[0].getAuthors()[0].getName().getValue());
          }

          @Override
          public void onFailure(CallErrorException caught) {
            // TODO Auto-generated method stub

          }
        });

    this.view.init(this);
  }

  @Override
  public String getUserName() {
    return "foobar";
  }

  @Override
  public void logout() {
    // TODO Auto-generated method stub

  }

}
