package de.graind.client;

import com.google.api.gwt.client.impl.ClientGoogleApiRequestTransport;
import com.google.api.gwt.client.impl.ClientOAuth2Login;
import com.google.api.gwt.services.calendar.shared.Calendar;
import com.google.api.gwt.services.calendar.shared.Calendar.CalendarAuthScope;
import com.google.api.gwt.services.calendar.shared.model.Events;
import com.google.api.gwt.shared.GoogleApiRequestTransport;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import de.graind.shared.Config;

public class MonthlyWidgetController implements MonthlyWidgetView.Controller {

  private MonthlyWidgetView view;
  private final Calendar calendar = GWT.create(Calendar.class);

  public MonthlyWidgetController(MonthlyWidgetView view) {
    this.view = view;

    // TODO: make oauth globally and pass token to controller
    login();
  }

  private void login() {
    new ClientOAuth2Login(Config.CLIENT_ID).withScopes(CalendarAuthScope.CALENDAR_READONLY).login(
        new Receiver<String>() {
          @Override
          public void onSuccess(String accessToken) {
            GWT.log("login successful");
            initialize(accessToken);
          }
        });
  }

  private void initialize(final String accessToken) {
    new ClientGoogleApiRequestTransport().setApiAccessKey(Config.API_KEY).setApplicationName(Config.APPLICATION_NAME)
        .setAccessToken(accessToken).create(new Receiver<GoogleApiRequestTransport>() {
          @Override
          public void onSuccess(GoogleApiRequestTransport transport) {
            SimpleEventBus eventBus = new SimpleEventBus();
            calendar.initialize(eventBus, transport);

            // we are ready, init the view
            MonthlyWidgetController.this.view.init(MonthlyWidgetController.this);
          }

          @Override
          public void onFailure(ServerFailure failure) {
            Window.alert("Failed to initialize transport");
          }
        });
  }

  @Override
  public void fetchEventsForMonth(int year, int month, Receiver<Events> receiver) {
    calendar.events().list("primary").setTimeMin(CalendarUtil.getMinimumForMonth(year, month))
        .setTimeMax(CalendarUtil.getMaximumForMonth(year, month)).to(receiver).fire();
    GWT.log("today: " + CalendarUtil.today());
  }

  /**
   * get all event entries for a given month.
   * 
   * @param year
   * @param month
   *          a month between 1 and 12
   * @param callback
   */
  // @Override
  // public void fetchEventsForMonth(int year, int month, final
  // AsyncCallback<EventEntry[]> callback) {
  // CalendarEventQuery query = CalendarEventQuery
  // .newInstance("http://www.google.com/calendar/feeds/default/private/full");
  //
  // DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd");
  // DateTime max = DateTime.newInstance(format.parse(year + "-" + month + "-"
  // + CalendarUtil.getDaysOfMonth(year, month)));
  // DateTime min = DateTime.newInstance(format.parse(year + "-" + month +
  // "-01"));
  //
  // query.setMaximumStartTime(max);
  // query.setMinimumStartTime(min);
  //
  // // TODO: add entries that start at an earlier date but are still valid
  //
  // service.getEventsFeed(query, new CalendarEventFeedCallback() {
  // @Override
  // public void onFailure(CallErrorException caught) {
  // callback.onFailure(caught);
  // }
  //
  // @Override
  // public void onSuccess(CalendarEventFeed result) {
  // callback.onSuccess(result.getEntries());
  // }
  // });
  // }

}
