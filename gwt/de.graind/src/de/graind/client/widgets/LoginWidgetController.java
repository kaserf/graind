package de.graind.client.widgets;

import com.google.gwt.accounts.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.gdata.client.GData;
import com.google.gwt.gdata.client.GDataSystemPackage;

import de.graind.client.util.Config;

public class LoginWidgetController implements LoginWidgetView.Controller {

  private LoginWidgetView view;

  public LoginWidgetController(LoginWidgetView view) {
    this.view = view;

    // TODO: check if we need calendar AND gbase (for picasa)
    if (!GData.isLoaded(GDataSystemPackage.CALENDAR)) {
      GData.loadGDataApi(Config.API_KEY, new Runnable() {
        public void run() {
          GWT.log("Login has loaded gdata");
          init();
        }
      }, GDataSystemPackage.CALENDAR);
    } else {
      init();
    }
  }

  private void init() {
    this.view.init(this);
  }

  @Override
  public void login() {
    User.login(Config.getScope());
  }

}
