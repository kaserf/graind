package de.graind.client.widgets;

import com.google.gwt.gdata.client.EventEntry;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MonthlyWidgetView {

  public interface Controller {

    /**
     * Set the callback, the controller should call if the List of current
     * Events has changed (e.g. after changing the month)
     * 
     * @param callback
     */
    void setEventsCallback(final AsyncCallback<EventEntry[]> callback);

    /**
     * Switch to next month
     */
    void nextMonth();

    /**
     * Switch to previous month
     */
    void previousMonth();

    /**
     * @return current Month [1,12] to be displayed
     */
    int getMonth();

    /**
     * 
     * @return current Year to be displayed
     */
    int getYear();
  }

  public void init(Controller controller);

}
