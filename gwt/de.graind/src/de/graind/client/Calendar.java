package de.graind.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Calendar implements EntryPoint {

  @Override
  public void onModuleLoad() {
    LoginWidget login = new LoginWidget();
    login.init(new LoginWidgetController());
    RootPanel.get().add(login);

    MonthlyWidget monthly = new MonthlyWidget();
    monthly.init(new MonthlyWidgetController());
    RootPanel.get().add(monthly);
  }
}
