package de.graind.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class GraindServerTestWidget extends Composite implements GraindServerTestWidgetView {

  private HorizontalPanel hpanel;
  private Controller controller;

  public GraindServerTestWidget() {
    hpanel = new HorizontalPanel();
    initWidget(hpanel);
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;

    this.controller.queryDatabaseTest(new AsyncCallback<List<Integer>>() {
      @Override
      public void onSuccess(List<Integer> result) {
        GWT.log("got a query response from the server with " + result.size() + " elements.");
        for (Integer integer : result) {
          GWT.log("server query response: " + integer);
        }
      }

      @Override
      public void onFailure(Throwable caught) {
        GWT.log("querying the graind server failed: " + caught.getMessage());
      }
    });
  }
}
