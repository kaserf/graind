package de.graind.client;

import java.util.List;

import com.google.gwt.accounts.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.gdata.client.GData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import de.graind.client.model.PicasaAlbum;
import de.graind.client.model.PicasaImage;
import de.graind.client.service.PicasaProxyService;
import de.graind.client.service.PicasaProxyServiceAsync;
import de.graind.shared.Config;

public class PicasaTestWidgetController implements PicasaTestWidgetView.Controller {

  private PicasaTestWidgetView view;

  private String token;

  private PicasaProxyServiceAsync service;

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
    this.token = User.checkLogin(Config.getScope());

    this.service = (PicasaProxyServiceAsync) GWT.create(PicasaProxyService.class);
    ServiceDefTarget serviceDef = (ServiceDefTarget) service;
    serviceDef.setServiceEntryPoint(GWT.getModuleBaseURL() + "picasaProxyService");

    this.view.init(this);
  }

  @Override
  public void getAlbums(AsyncCallback<List<PicasaAlbum>> callback) {
    service.getAlbums(token, callback);
  }

  @Override
  public void getImages(final String albumId, AsyncCallback<List<PicasaImage>> callback) {
    service.getImages(albumId, token, callback);
  }

  @Override
  public void getRecentImages(AsyncCallback<List<PicasaImage>> callback) {
    service.getRecentImages(token, callback);
  }
}
