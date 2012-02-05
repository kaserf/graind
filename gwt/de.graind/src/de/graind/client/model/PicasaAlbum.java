package de.graind.client.model;

import java.io.Serializable;

public class PicasaAlbum implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private String contentUrl;
  private String creatorUsername;
  private String creatorNickname;
  private String title;
  private Integer photoCount;

  private Thumbnail thumbnail;

  public String getId() {
    return id;
  }

  public void setId(String albumId) {
    this.id = albumId;
  }

  public String getContentUrl() {
    return contentUrl;
  }

  public void setContentUrl(String contentUrl) {
    this.contentUrl = contentUrl;
  }

  public String getCreatorUsername() {
    return creatorUsername;
  }

  public void setCreatorUsername(String creatorUsername) {
    this.creatorUsername = creatorUsername;
  }

  public String getCreatorNickname() {
    return creatorNickname;
  }

  public void setCreatorNickname(String creatorNickname) {
    this.creatorNickname = creatorNickname;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getPhotoCount() {
    return photoCount;
  }

  public void setPhotoCount(Integer photoCount) {
    this.photoCount = photoCount;
  }

  public Thumbnail getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(Thumbnail thumbnail) {
    this.thumbnail = thumbnail;
  }

}
