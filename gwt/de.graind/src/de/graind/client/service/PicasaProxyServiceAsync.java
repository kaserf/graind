package de.graind.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.graind.client.model.PicasaAlbum;
import de.graind.client.model.PicasaImage;

public interface PicasaProxyServiceAsync {

  void getAlbums(String token, AsyncCallback<List<PicasaAlbum>> callback);

  void getImages(String albumId, String token, AsyncCallback<List<PicasaImage>> callback);
}
