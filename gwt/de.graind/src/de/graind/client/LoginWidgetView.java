package de.graind.client;


public interface LoginWidgetView {

  public interface Controller {
    boolean isLoggedIn();

    // TODO: async callback
    void login();

    void logout();
  }

  public void init(Controller controller);

}
