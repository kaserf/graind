package de.graind.client;

import java.util.List;

import com.google.gwt.accounts.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.gdata.client.GData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import de.graind.client.model.PicasaAlbum;
import de.graind.client.service.PicasaProxyService;
import de.graind.client.service.PicasaProxyServiceAsync;
import de.graind.shared.Config;

public class PicasaTestWidgetController implements PicasaTestWidgetView.Controller {

  private PicasaTestWidgetView view;

  public PicasaTestWidgetController(PicasaTestWidgetView view) {
    this.view = view;

    if (!GData.isLoaded()) {
      GData.loadGDataApi(Config.API_KEY, new Runnable() {
        public void run() {
          init();
        }
      });
    } else {
      init();
    }

  }

  private void init() {
    this.view.init(this);
  }

  @Override
  public void getAlbums() {

    String token = User.checkLogin(Config.getScope());

    PicasaProxyServiceAsync service = (PicasaProxyServiceAsync) GWT.create(PicasaProxyService.class);
    ServiceDefTarget serviceDef = (ServiceDefTarget) service;
    serviceDef.setServiceEntryPoint(GWT.getModuleBaseURL() + "picasaProxyService");
    service.getAlbums(token, new AsyncCallback<List<PicasaAlbum>>() {
      @Override
      public void onSuccess(List<PicasaAlbum> result) {
        GWT.log("got " + result.size() + " albums from the server.");
        for (PicasaAlbum picasaAlbum : result) {
          GWT.log("Album: " + picasaAlbum.getTitle());
        }
      }

      @Override
      public void onFailure(Throwable caught) {
        GWT.log("getAlbums() failed: " + caught.getMessage());
      }
    });
  }
}
