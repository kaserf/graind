package de.graind.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DayWidget extends Composite implements HasText, DayWidgetView {

  private Controller controller;

  private static DayWidgetUiBinder uiBinder = GWT.create(DayWidgetUiBinder.class);
  @UiField
  Label day;

  interface DayWidgetUiBinder extends UiBinder<Widget, DayWidget> {
  }

  public DayWidget() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public DayWidget(String firstName) {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public void setText(String text) {
    day.setText(text);
  }

  public String getText() {
    return day.getText();
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;
    this.setText(controller.getDay());
  }
}
