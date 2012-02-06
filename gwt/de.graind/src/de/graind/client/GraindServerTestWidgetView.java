package de.graind.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GraindServerTestWidgetView {

  public interface Controller {
    void queryDatabaseTest(AsyncCallback<List<Integer>> callback);
  }

  void init(Controller controller);
}
