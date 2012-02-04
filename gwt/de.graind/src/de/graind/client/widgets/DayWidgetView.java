package de.graind.client.widgets;

public interface DayWidgetView {

  public interface Controller {
    public String getDay();

    public String getMonthAndYear();
  }

  public void init(Controller controller);

}
