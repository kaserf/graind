package de.graind.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import de.graind.client.model.PicasaAlbum;

public interface PicasaProxyService extends RemoteService {

  List<PicasaAlbum> getAlbums(String token);
}
