package de.graind.client.widgets;

public interface DayWidgetView {

  public interface Controller {
    public String getDay();
  }

  public void init(Controller controller);

}
