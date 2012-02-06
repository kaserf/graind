package de.graind.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.graind.client.model.PicasaAlbum;
import de.graind.client.model.PicasaImage;
import de.graind.client.model.PicasaImageBase;

public class PicasaTestWidget extends Composite implements PicasaTestWidgetView {

  private HorizontalPanel hpanel;
  private Controller controller;

  public PicasaTestWidget() {
    hpanel = new HorizontalPanel();
    initWidget(hpanel);
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;

    loadAlbums();
    // loadImages(albumId);
    loadRecentImages();
  }

  private void loadAlbums() {
    this.controller.getAlbums(new AsyncCallback<List<PicasaAlbum>>() {
      @Override
      public void onSuccess(List<PicasaAlbum> result) {
        GWT.log("got " + result.size() + " albums from the server.");
        for (PicasaAlbum picasaAlbum : result) {
          GWT.log("Album: " + picasaAlbum.getTitle() + " with ID " + picasaAlbum.getId());
        }
      }

      @Override
      public void onFailure(Throwable caught) {
        GWT.log("getAlbums() failed: " + caught.getMessage());
      }
    });
  }

  private void loadImages(final String albumId) {
    this.controller.getImages(albumId, new AsyncCallback<List<PicasaImage>>() {

      @Override
      public void onFailure(Throwable caught) {
        GWT.log("getImages() failed: " + caught.getMessage());
      }

      @Override
      public void onSuccess(List<PicasaImage> result) {
        GWT.log("got " + result.size() + " images from the " + albumId + " album.");
        for (PicasaImageBase picasaImage : result) {
          GWT.log("Image: " + picasaImage.getUrl());
        }
      }
    });
  }

  private void loadRecentImages() {
    this.controller.getRecentImages(new AsyncCallback<List<PicasaImage>>() {

      @Override
      public void onFailure(Throwable caught) {
        GWT.log("getRecentImages() failed: " + caught.getMessage());
      }

      @Override
      public void onSuccess(List<PicasaImage> result) {
        GWT.log("got " + result.size() + " most recent images.");
        for (PicasaImage picasaImage : result) {
          GWT.log("Image: " + picasaImage.getUrl());
          GWT.log("Thumbnail url: " + picasaImage.getThumbnails().get(0).getUrl());
        }
      }
    });
  }

}
