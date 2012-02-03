package de.graind.client;

public interface UserStatusWidgetView {

  public interface Controller {
    String getUserName();

    void logout();

    void login();
  }

  public void init(Controller controller);

}
