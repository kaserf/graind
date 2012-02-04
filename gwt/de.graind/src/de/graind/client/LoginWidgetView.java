package de.graind.client;


public interface LoginWidgetView {

  public interface Controller {
    void login();
  }

  public void init(Controller controller);

}
