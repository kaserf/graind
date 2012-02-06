package de.graind.client.widgets;


public interface LoginWidgetView {

  public interface Controller {
    void login();
  }

  public void init(Controller controller);

}
