package de.graind.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.graind.client.model.PicasaAlbum;
import de.graind.client.model.PicasaImage;

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
    this.controller.getAlbums(new AsyncCallback<List<PicasaAlbum>>() {
      @Override
      public void onSuccess(List<PicasaAlbum> result) {
        GWT.log("got " + result.size() + " albums from the server.");
        for (PicasaAlbum picasaAlbum : result) {
          GWT.log("Album: " + picasaAlbum.getTitle() + " with ID " + picasaAlbum.getId());

          // TODO: this is just an example to load the images of the first
          // album.
          loadImages(result.get(0).getId());
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
        for (PicasaImage picasaImage : result) {
          GWT.log("Image: " + picasaImage.getUrl());
        }
      }
    });
  }

}
