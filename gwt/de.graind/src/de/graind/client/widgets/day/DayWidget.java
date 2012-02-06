package de.graind.client.widgets.day;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DayWidget extends Composite implements DayWidgetView {

  private Controller controller;

  private static DayWidgetUiBinder uiBinder = GWT.create(DayWidgetUiBinder.class);
  @UiField
  Label day;
  @UiField
  Label monthAndYear;

  interface DayWidgetUiBinder extends UiBinder<Widget, DayWidget> {
  }

  public DayWidget() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public DayWidget(String firstName) {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;
    this.day.setText(controller.getDay());
    this.monthAndYear.setText(controller.getMonthAndYear());
  }
}
