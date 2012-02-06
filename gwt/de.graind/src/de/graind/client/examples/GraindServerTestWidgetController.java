package de.graind.client.examples;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import de.graind.client.model.PicasaImageBase;
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
  public void insertIntoDBTest(String username, PicasaImageBase[] images) {
    service.saveMonthlyPictureSelection(username, images, new AsyncCallback<Void>() {
      @Override
      public void onSuccess(Void result) {
        GWT.log("saved values to db");
      }

      @Override
      public void onFailure(Throwable caught) {
        GWT.log("save did not return: " + caught.getMessage());
      }
    });
  }
}
