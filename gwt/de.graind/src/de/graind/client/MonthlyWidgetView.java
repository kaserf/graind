package de.graind.client;

import com.google.gwt.gdata.client.EventEntry;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MonthlyWidgetView {

  public interface Controller {

    void fetchEventsForMonth(int year, int month, AsyncCallback<EventEntry[]> callback);

  }

  public void init(Controller controller);

}
