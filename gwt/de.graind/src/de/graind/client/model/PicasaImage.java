package de.graind.client.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PicasaImage extends PicasaImageBase implements Serializable {
  private static final long serialVersionUID = 1L;

  private String title;
  private String albumId;
  private Date timestamp;
  private List<Thumbnail> thumbnails;

  private String id;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAlbumId() {
    return albumId;
  }

  public void setAlbumId(String albumId) {
    this.albumId = albumId;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public List<Thumbnail> getThumbnails() {
    return thumbnails;
  }

  public void setThumbnails(List<Thumbnail> thumbnails) {
    this.thumbnails = thumbnails;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
