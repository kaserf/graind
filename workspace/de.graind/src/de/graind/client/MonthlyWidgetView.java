package de.graind.client;

public interface MonthlyWidgetView {

  public interface Controller {

    int getMonth();

    void loadMonths(LoadDataCallback<Integer> callback);

  }

  public void init(Controller controller);

}
