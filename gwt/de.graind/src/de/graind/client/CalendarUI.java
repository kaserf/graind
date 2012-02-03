package de.graind.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class CalendarUI extends Composite {

  private static CalendarUIUiBinder uiBinder = GWT.create(CalendarUIUiBinder.class);

  @UiField
  SimplePanel topRowLeft;
  @UiField
  UserStatusWidget topRowRight;
  @UiField
  SimplePanel topRowCenter;
  @UiField
  SimplePanel picSpace;
  @UiField
  SimplePanel calSpaceLeft;
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
    new UserStatusWidgetController(topRowRight);
    new MonthlyWidgetController(calSpaceMonthly);
  }

}
