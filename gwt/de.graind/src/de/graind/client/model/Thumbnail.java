package de.graind.client.model;

import java.io.Serializable;

public class Thumbnail implements Serializable {
  private static final long serialVersionUID = 1L;

  private String url;
  private Integer height;
  private Integer width;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

}
