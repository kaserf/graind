package de.graind.client.model;

import java.io.Serializable;

public class PicasaImageBase implements Serializable {

  private static final long serialVersionUID = 1L;
  private String url;
  private Long width;
  private Long height;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
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

}
