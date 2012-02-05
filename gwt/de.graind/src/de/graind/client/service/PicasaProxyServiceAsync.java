package de.graind.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.graind.client.model.PicasaAlbum;

public interface PicasaProxyServiceAsync {

  void getAlbums(String token, AsyncCallback<List<PicasaAlbum>> callback);
}
