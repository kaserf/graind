package de.graind.client;

import java.util.Date;

import com.google.api.gwt.client.impl.ClientGoogleApiRequestTransport;
import com.google.api.gwt.services.calendar.shared.Calendar;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.api.gwt.services.calendar.shared.model.Events;
import com.google.api.gwt.shared.GoogleApiRequestTransport;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import de.graind.shared.Config;

public class UserStatusWidgetController implements UserStatusWidgetView.Controller {

  private UserStatusWidgetView view;
  private Calendar cal = GWT.create(Calendar.class);

  private String today() {
    String today = DateTimeFormat.getFormat("yyyy-MM-dd'T'00:00:00-00:00").format(new Date());
    return today;
  }

  public UserStatusWidgetController(UserStatusWidgetView view) {
    this.view = view;

    new ClientGoogleApiRequestTransport().setApiAccessKey(Config.API_KEY).setApplicationName(Config.applicationName)
        .create(new Receiver<GoogleApiRequestTransport>() {
          @Override
          public void onSuccess(GoogleApiRequestTransport transport) {
            SimpleEventBus eventBus = new SimpleEventBus();
            cal.initialize(eventBus, transport);

            cal.events().list("fl3x888@gmail.com").setTimeMin(today()).to(new Receiver<Events>() {
              @Override
              public void onSuccess(Events events) {
                GWT.log("=== UPCOMING EVENTS ===");
                for (Event event : events.getItems()) {
                  GWT.log(event.getSummary());
                }
              }
            }).fire();
            /*
             * cal.calendarList().list().to(new Receiver<CalendarList>() {
             * 
             * @Override public void onSuccess(CalendarList response) {
             * GWT.log("=== CALENDARS ==="); for (CalendarListEntry calendar :
             * response.getItems()) { GWT.log(calendar.getId()); } }
             * 
             * }).fire();
             */
          }

          @Override
          public void onFailure(ServerFailure error) {
            Window.alert("Failed to initialize Transport!");
          }
        });

    // this.view.init(this);
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
