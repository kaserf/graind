package de.graind.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import de.graind.client.service.GraindService;
import de.graind.client.service.GraindServiceAsync;

public class GraindServerTestWidgetController implements GraindServerTestWidgetView.Controller {

  private GraindServerTestWidgetView view;

  private GraindServiceAsync service;

  public GraindServerTestWidgetController(GraindServerTestWidgetView view) {
    this.view = view;

    this.service = (GraindServiceAsync) GWT.create(GraindService.class);
    ServiceDefTarget serviceDef = (ServiceDefTarget) service;
    serviceDef.setServiceEntryPoint(GWT.getModuleBaseURL() + "graindService");

    this.view.init(this);
  }

  @Override
  public void queryDatabaseTest(AsyncCallback<List<Integer>> callback) {
    service.queryDatabaseTest(callback);
  }
}
