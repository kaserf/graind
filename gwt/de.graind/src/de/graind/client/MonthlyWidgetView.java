package de.graind.client;

import com.google.api.gwt.services.calendar.shared.model.Events;
import com.google.web.bindery.requestfactory.shared.Receiver;

public interface MonthlyWidgetView {

  public interface Controller {

    void fetchEventsForMonth(int year, int month, Receiver<Events> receiver);

  }

  public void init(Controller controller);

}
