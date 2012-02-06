package de.graind.client.examples;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.graind.client.model.PicasaAlbum;
import de.graind.client.model.PicasaImage;

public interface PicasaTestWidgetView {

  public interface Controller {
    void getAlbums(AsyncCallback<List<PicasaAlbum>> callback);

    void getImages(String albumId, AsyncCallback<List<PicasaImage>> callback);

    void getRecentImages(AsyncCallback<List<PicasaImage>> callback);
  }

  void init(Controller controller);
}
