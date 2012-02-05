package de.graind.client.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PicasaImage implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private String url;
  private String title;
  private String albumId;
  private Date timestamp;
  private Long width;
  private Long height;

  private List<Thumbnail> thumbnails;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

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

  public Long getWidth() {
    return width;
  }

  public void setWidth(Long width) {
    this.width = width;
  }

  public Long getHeight() {
    return height;
  }

  public void setHeight(Long height) {
    this.height = height;
  }

  public List<Thumbnail> getThumbnails() {
    return thumbnails;
  }

  public void setThumbnails(List<Thumbnail> thumbnails) {
    this.thumbnails = thumbnails;
  }

}
