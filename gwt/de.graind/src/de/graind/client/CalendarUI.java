package de.graind.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import de.graind.client.widgets.DayWidget;
import de.graind.client.widgets.DayWidgetController;
import de.graind.client.widgets.MonthlyWidget;
import de.graind.client.widgets.MonthlyWidgetController;

public class CalendarUI extends Composite {

  private static CalendarUIUiBinder uiBinder = GWT.create(CalendarUIUiBinder.class);

  @UiField
  SimplePanel topRowLeft;
  @UiField
  LogoutWidget topRowRight;
  @UiField
  SimplePanel topRowCenter;
  @UiField
  Composite centerSpace;
  @UiField
  DayWidget calSpaceLeft;
  @UiField
  MonthlyWidget calSpaceMonthly;

  interface CalendarUIUiBinder extends UiBinder<Widget, CalendarUI> {
  }

  public CalendarUI() {
    initWidget(uiBinder.createAndBindUi(this));
    initController();
  }

  public CalendarUI(String firstName) {
    this();
  }

  private void initController() {
    new MonthlyWidgetController(calSpaceMonthly);
    new LogoutWidgetController(topRowRight);
    new DayWidgetController(calSpaceLeft);
  }

}
