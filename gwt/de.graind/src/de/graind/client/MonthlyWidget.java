package de.graind.client;

import java.util.Date;

import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.api.gwt.services.calendar.shared.model.EventDateTime;
import com.google.api.gwt.services.calendar.shared.model.Events;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class MonthlyWidget extends Composite implements MonthlyWidgetView {
  private int currentMonth;
  private int currentYear;
  private Events currentEvents;

  private HorizontalPanel datePanel;
  private Label monthLabel;
  private Controller controller;
  private VerticalPanel vpanel;
  private Label[] dayLabels;

  public MonthlyWidget() {
    vpanel = new VerticalPanel();
    initWidget(vpanel);
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;

    currentMonth = CalendarUtil.getMonth(new Date());
    currentYear = CalendarUtil.getYear(new Date());

    setupWidget();
  }

  private void setupWidget() {

    monthLabel = new Label();
    vpanel.add(monthLabel);

    HorizontalPanel hpanel = new HorizontalPanel();
    vpanel.add(hpanel);

    // add button to switch to previous month
    Button prev = new Button("<");
    prev.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        GWT.log("previous month clicked");
        if (currentMonth == 1) {
          currentMonth = 12;
          currentYear--;
        } else {
          currentMonth--;
        }

        refreshWidget();
      }
    });
    hpanel.add(prev);

    datePanel = new HorizontalPanel();
    hpanel.add(datePanel);

    // add button to switch to previous month
    Button next = new Button(">");
    next.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        GWT.log("next month clicked");
        if (currentMonth == 12) {
          currentMonth = 1;
          currentYear++;
        } else {
          currentMonth++;
        }

        refreshWidget();
      }
    });
    hpanel.add(next);

    refreshWidget();
  }

  private void refreshWidget() {
    monthLabel.setText(CalendarUtil.getMonthLabel(currentMonth) + " " + currentYear);

    // performance optimization: reuse widgets and hide the days not visible
    // this month
    datePanel.clear();
    dayLabels = new Label[CalendarUtil.getDaysOfMonth(currentYear, currentMonth)];

    for (int i = 0; i < CalendarUtil.getDaysOfMonth(currentYear, currentMonth); i++) {
      Label l = new Label();
      l.setText(String.valueOf(i + 1));
      dayLabels[i] = l;

      datePanel.add(l);
    }

    controller.fetchEventsForMonth(currentYear, currentMonth, new Receiver<Events>() {
      @Override
      public void onSuccess(Events events) {
        currentEvents = events;
        GWT.log("We got " + currentEvents.getItems().size() + " new entries.");

        fillDays();
        // GWT.log("=== UPCOMING EVENTS ===");
        // for (Event event : events.getItems()) {
        // GWT.log(event.getSummary());
        // }
      }
    });
  }

  private void fillDays() {
    for (int i = 0; i < CalendarUtil.getDaysOfMonth(currentYear, currentMonth); i++) {
      dayLabels[i].addClickHandler(new DayClickHandler(MonthlyWidget.this.getEventsForDay(i), dayLabels[i]));
    }
  }

  // TODO: build a widget that shows the events
  private String getEventsForDay(int day) {
    String ret = "";

    GWT.log("2We got " + currentEvents.getItems().size() + " new entries.");
    for (Event event : currentEvents.getItems()) {
      // TODO: check recurring events
      // GWT.log("time: " + event.getOriginalStartTime().getDateTime());
      DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ssZ");
      DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");

      EventDateTime start = event.getStart();
      if ((start.getDateTime() != null) && (CalendarUtil.getDay(dateTimeFormat.parse(start.getDateTime())) == day)) {
        ret = ret + "Event: " + event.getSummary() + " (" + start.getDateTime() + ")" + "\n";
      } else if ((start.getDate() != null) && (CalendarUtil.getDay(dateFormat.parse(start.getDate())) == day)) {
        ret = ret + "Event: " + event.getSummary() + " (" + start.getDate() + ")" + "\n";
      } else {
        GWT.log("recurring event: " + event.getSummary());
      }
    }
    return ret;
  }

}

class DayClickHandler implements ClickHandler {

  private String message;
  private UIObject target;

  public DayClickHandler(String message, UIObject target) {
    this.message = message;
    this.target = target;
  }

  @Override
  public void onClick(ClickEvent event) {
    DecoratedPopupPanel popup = new DecoratedPopupPanel(true);
    popup.setWidth("200px");
    popup.add(new Label(message));
    popup.show();
    popup.showRelativeTo(target);
  }

}
