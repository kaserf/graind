package de.graind.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class GraindEntry implements EntryPoint {

  @Override
  public void onModuleLoad() {
    CalendarUI ui = new CalendarUI();
    ui.setVisible(true);
    RootPanel.get().add(ui);
    GWT.log("hello");
  }
}
