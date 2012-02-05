package de.graind.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.ServiceException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.graind.client.model.PicasaAlbum;
import de.graind.client.service.PicasaProxyService;
import de.graind.shared.Config;

public class PicasaProxyServiceImpl extends RemoteServiceServlet implements PicasaProxyService {

  private static final long serialVersionUID = 1L;
  private PicasawebService service;

  public PicasaProxyServiceImpl() {
    this.service = new PicasawebService(Config.APPLICATION_NAME);
  }

  @Override
  public List<PicasaAlbum> getAlbums(String token) {
    List<PicasaAlbum> ret = new LinkedList<PicasaAlbum>();

    service.setAuthSubToken(token);

    try {
      URL feedUrl = new URL(Config.PICASA_ALBUM_URI);
      UserFeed userFeed = service.getFeed(feedUrl, UserFeed.class);

      for (AlbumEntry currentAlbum : userFeed.getAlbumEntries()) {
        PicasaAlbum tmp = new PicasaAlbum();
        tmp.setAlbumId(currentAlbum.getId());
        tmp.setAlbumUrl(currentAlbum.getHtmlLink().getHref());
        tmp.setCreatorNickname(currentAlbum.getNickname());
        tmp.setCreatorUsername(currentAlbum.getUsername());
        tmp.setPhotoCount(currentAlbum.getPhotosUsed());
        tmp.setTitle(currentAlbum.getTitle().getPlainText());
        ret.add(tmp);
      }
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ServiceException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return ret;
  }
}
