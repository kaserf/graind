package de.graind.client;


public class UserStatusWidgetController implements UserStatusWidgetView.Controller {

  private UserStatusWidgetView view;

  public UserStatusWidgetController(UserStatusWidgetView view) {
    this.view = view;

    this.view.init(this);
  }

  @Override
  public String getUserName() {
    return "foobar";
  }

  @Override
  public void logout() {
    // TODO Auto-generated method stub

  }

}
