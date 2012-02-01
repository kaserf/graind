package de.graind.client;

import com.google.gwt.gdata.client.GData;
import com.google.gwt.gdata.client.GDataSystemPackage;
import com.google.gwt.user.client.Window;

import de.graind.shared.Config;

public class LoginWidgetController implements LoginWidgetView.Controller {

  private LoginWidgetView view;

  public LoginWidgetController(LoginWidgetView view) {
    this.view = view;

    // TODO: check if we need calendar AND gbase (for picasa)
    if (!GData.isLoaded(GDataSystemPackage.CALENDAR)) {
      GData.loadGDataApi(Config.API_KEY, new Runnable() {
        public void run() {
          Window.alert("Login has loaded gdata");
          LoginWidgetController.this.view.init(LoginWidgetController.this);
        }
      }, GDataSystemPackage.CALENDAR);
    }
  }

  @Override
  public boolean isLoggedIn() {
    // AuthSubStatus status = User.getStatus(Config.gdataScope);

    // GWT.log("Userstatus: " + status.name());
    // if (status == AuthSubStatus.LOGGED_OUT) {
    // User.login(Config.gdataScope);
    // } else {
    // addLogoutButton();
    // }
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void login() {
    // TODO Auto-generated method stub

  }

  @Override
  public void logout() {
    // TODO Auto-generated method stub

  }

}
