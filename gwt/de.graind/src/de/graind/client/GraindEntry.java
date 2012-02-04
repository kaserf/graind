package de.graind.client;

import com.google.gwt.accounts.client.AuthSubStatus;
import com.google.gwt.accounts.client.User;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.gdata.client.GData;
import com.google.gwt.gdata.client.GDataSystemPackage;
import com.google.gwt.user.client.ui.RootPanel;

import de.graind.shared.Config;

public class GraindEntry implements EntryPoint {

  @Override
  public void onModuleLoad() {
    if (!GData.isLoaded(GDataSystemPackage.CALENDAR)) {
      GData.loadGDataApi(Config.API_KEY, new Runnable() {
        public void run() {
          loadModule();
        }
      }, GDataSystemPackage.CALENDAR);
    } else {
      loadModule();
    }
  }

  private void loadModule() {
    // check if the page was reloaded while logging in, no need to init the page
    // further
    if (User.getStatus() == AuthSubStatus.LOGGING_IN) {
      return;
    }

    CalendarUI ui = new CalendarUI();
    ui.setVisible(true);
    RootPanel.get().add(ui);
    GWT.log("hello");
  }
}
