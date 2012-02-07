package de.graind.client.widgets;

import com.google.gwt.accounts.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.gdata.client.GData;
import com.google.gwt.gdata.client.GDataSystemPackage;
import com.google.gwt.gdata.client.calendar.CalendarFeed;
import com.google.gwt.gdata.client.calendar.CalendarFeedCallback;
import com.google.gwt.gdata.client.calendar.CalendarService;
import com.google.gwt.gdata.client.impl.CallErrorException;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.graind.client.CalendarUI;
import de.graind.shared.Config;

public class LogoutWidgetController implements LogoutWidgetView.Controller {

  private LogoutWidgetView view;
  private CalendarService service;
  private AsyncCallback<Void> next;
  private CalendarUI parentController;

  public LogoutWidgetController(LogoutWidgetView view, CalendarUI calendarUI, AsyncCallback<Void> next) {
    this.view = view;
    this.next = next;
    this.parentController = calendarUI;

    // TODO: check if we need calendar AND gbase (for picasa)
    if (!GData.isLoaded(GDataSystemPackage.CALENDAR)) {
      GData.loadGDataApi(Config.API_KEY, new Runnable() {
        public void run() {
          GWT.log("Logout has loaded gdata");
          init();
        }
      }, GDataSystemPackage.CALENDAR);
    } else {
      init();
    }
  }

  private void init() {
    service = CalendarService.newInstance(Config.APPLICATION_NAME);

    this.fetchUsername(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable caught) {
        GWT.log("failed to fetch username.");
        next.onFailure(caught);
      }

      @Override
      public void onSuccess(Void result) {
        view.init(LogoutWidgetController.this);
        next.onSuccess(result);
      }
    });
  }

  private void fetchUsername(final AsyncCallback<Void> callback) {
    service.getOwnCalendarsFeed("http://www.google.com/calendar/feeds/default/owncalendars/full",
        new CalendarFeedCallback() {
          @Override
          public void onSuccess(CalendarFeed result) {
            Config.USERNAME = result.getEntries()[0].getAuthors()[0].getName().getValue();
            callback.onSuccess(null);
          }

          @Override
          public void onFailure(CallErrorException caught) {
            GWT.log("failed to fetch username");
            callback.onFailure(caught);
          }
        });
  }

  @Override
  public void getUserName(final AsyncCallback<String> callback) {
    if (Config.USERNAME != null) {
      callback.onSuccess(Config.USERNAME);
    } else {
      fetchUsername(new AsyncCallback<Void>() {
        @Override
        public void onFailure(Throwable caught) {
          callback.onFailure(caught);
        }

        @Override
        public void onSuccess(Void result) {
          callback.onSuccess(Config.USERNAME);
        }
      });
    }
  }

  @Override
  public void logout(Runnable callback) {
    User.logout(callback, Config.getScope());
  }

  @Override
  public void toggleSettings() {
    this.parentController.toggleSettings();
  }

}
