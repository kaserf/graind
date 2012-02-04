package de.graind.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gdata.client.EventEntry;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MonthlyWidget extends Composite implements MonthlyWidgetView {

  private static MonthlyWidgetUiBinder uiBinder = GWT.create(MonthlyWidgetUiBinder.class);
  private Controller controller;
  @UiField
  Button prevButton;
  @UiField
  Button nextButton;
  @UiField
  HorizontalPanel daysPanel;
  @UiField
  Label currentMonthLabel;

  private EventEntry[] events;

  interface MonthlyWidgetUiBinder extends UiBinder<Widget, MonthlyWidget> {
  }

  public MonthlyWidget() {
    initWidget(uiBinder.createAndBindUi(this));

  }

  public MonthlyWidget(String firstName) {
    this();
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;

  }

  private void update() {
    currentMonthLabel.setText("Feb 12");
  }

  private class EventUpdater implements AsyncCallback<EventEntry[]> {

    @Override
    public void onSuccess(EventEntry[] result) {
      GWT.log("We got " + result.length + " new entries.");
      events = result;
      update();
    }

    @Override
    public void onFailure(Throwable caught) {
      GWT.log("could not get the events: " + caught.getMessage());
    }
  }

}
