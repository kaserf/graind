package de.graind.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gdata.client.GData;
import com.google.gwt.gdata.client.GDataSystemPackage;
import com.google.gwt.gdata.client.calendar.CalendarFeed;
import com.google.gwt.gdata.client.calendar.CalendarFeedCallback;
import com.google.gwt.gdata.client.calendar.CalendarService;
import com.google.gwt.gdata.client.impl.CallErrorException;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.graind.shared.Config;

public class UserStatusWidgetController implements UserStatusWidgetView.Controller {

  private UserStatusWidgetView view;
  private CalendarService service;

  private String username;

  public UserStatusWidgetController(UserStatusWidgetView view) {
    this.view = view;

    // TODO: load gdata api, init widget, make controller calls async

    // TODO: check if we need calendar AND gbase (for picasa)
    if (!GData.isLoaded(GDataSystemPackage.CALENDAR)) {
      GData.loadGDataApi(Config.API_KEY, new Runnable() {
        public void run() {
          GWT.log("Login has loaded gdata");
          init();
        }
      }, GDataSystemPackage.CALENDAR);
    } else {
      init();
    }
  }

  private void init() {
    service = CalendarService.newInstance(Config.APPLICATION_NAME);
    this.view.init(this);
  }

  public void gdataLoaded() {

  }

  @Override
  public void getUserName(final AsyncCallback<String> callback) {
    if (this.username != null) {
      callback.onSuccess(this.username);
    } else {
      service.getOwnCalendarsFeed("http://www.google.com/calendar/feeds/default/owncalendars/full",
          new CalendarFeedCallback() {
            @Override
            public void onSuccess(CalendarFeed result) {
              UserStatusWidgetController.this.username = result.getEntries()[0].getAuthors()[0].getName().getValue();
              callback.onSuccess(UserStatusWidgetController.this.username);
            }

            @Override
            public void onFailure(CallErrorException caught) {
              GWT.log("failed to query calendar in userstatus");
              callback.onFailure(caught);
            }
          });
    }
  }

  @Override
  public boolean isLoggedIn() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void logout(AsyncCallback<Void> callback) {
    // TODO Auto-generated method stub

  }

  @Override
  public void login(AsyncCallback<Void> callback) {
    // TODO Auto-generated method stub

  }

}
