package de.graind.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MonthlyWidget extends Composite implements MonthlyWidgetView {
  private int currentMonth;
  private int currentYear;
  private int[] daysPerMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
  private String[] monthLabels = { "January", "February", "March", "April", "May", "June", "July", "August",
      "September", "October", "November", "December" };

  private HorizontalPanel datePanel;
  private Label monthLabel;
  private Controller controller;

  public MonthlyWidget() {
    currentMonth = Integer.parseInt(DateTimeFormat.getFormat("M").format(new Date()));
    currentYear = Integer.parseInt(DateTimeFormat.getFormat("yyyy").format(new Date()));
    GWT.log("current month is: " + currentMonth + " the year is: " + currentYear);
    GWT.log("the current month has " + getDaysOfMonth(currentMonth, currentYear) + " days.");
  }

  private void setupWidget() {
    controller.loadMonths(new LoadDataCallback<Integer>() {

      @Override
      public void receiveData(Integer data) {
        Window.alert("" + data);
      }
    });

    VerticalPanel vpanel = new VerticalPanel();

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

        fillWithDates();
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

        fillWithDates();
      }
    });
    hpanel.add(next);

    initWidget(vpanel);

    fillWithDates();
  }

  private void fillWithDates() {
    monthLabel.setText(monthLabels[currentMonth - 1] + " " + currentYear);

    // performance optimization: reuse widgets and hide the days not visible
    // this month
    datePanel.clear();
    for (int i = 0; i < getDaysOfMonth(currentMonth, currentYear); i++) {
      Label l = new Label();
      l.setText(String.valueOf(i + 1));
      l.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          Window.alert("You clicked on " + event.getSource().toString());
        }
      });

      datePanel.add(l);
    }
  }

  /**
   * Returns the number of days a month has.
   * 
   * @param month
   * @param year
   * @return
   * @throws IllegalArgumentException
   */
  public int getDaysOfMonth(int month, int year) throws IllegalArgumentException {
    // our array starts with 0
    month--;
    if (month > 11) {
      throw new IllegalArgumentException("A month larger then 12 is not allowed");
    } else {
      if (month == 1) {
        // check leap year
        if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
          return daysPerMonth[month] + 1;
        }
      }

      return daysPerMonth[month];
    }
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;

    setupWidget();
  }
}
