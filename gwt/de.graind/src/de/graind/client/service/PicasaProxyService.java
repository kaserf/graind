package de.graind.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import de.graind.client.model.PicasaAlbum;
import de.graind.client.model.PicasaImage;

public interface PicasaProxyService extends RemoteService {

  List<PicasaAlbum> getAlbums(String token);

  List<PicasaImage> getImages(String albumId, String token);

  List<PicasaImage> getRecentImages(String token);

}
