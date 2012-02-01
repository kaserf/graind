package de.graind.client;

public class LoginWidgetController implements LoginWidgetView.Controller {

  public LoginWidgetController() {
    // TODO: load gdata if not present yet
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
