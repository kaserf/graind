package de.graind.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Calendar implements EntryPoint {

  @Override
  public void onModuleLoad() {
    LoginWidget loginView = new LoginWidget();
    new LoginWidgetController(loginView);
    // TODO: this does not belong to the root panel.
    RootPanel.get().add(loginView);

    MonthlyWidget monthlyView = new MonthlyWidget();
    new MonthlyWidgetController(monthlyView);
    // TODO: this does not belong to the root panel.
    RootPanel.get().add(monthlyView);
  }
}
