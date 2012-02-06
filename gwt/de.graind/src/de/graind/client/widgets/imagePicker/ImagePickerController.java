package de.graind.client.widgets.imagePicker;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.accounts.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import de.graind.client.model.PicasaImage;
import de.graind.client.model.PicasaImageBase;
import de.graind.client.service.PicasaProxyService;
import de.graind.client.service.PicasaProxyServiceAsync;
import de.graind.client.widgets.imagePicker.ImagePickerView.Controller;
import de.graind.client.widgets.imagePicker.image.PicasaImageWidget;
import de.graind.client.widgets.imagePicker.image.PicasaImageWidgetController;
import de.graind.shared.Config;

public class ImagePickerController implements Controller {

  private String token;
  private PicasaProxyServiceAsync service;

  private ImagePickerView view;
  private int selectedMonth = -1;
  private int selectedImage = -1;

  private List<PicasaImageWidgetController> images = new ArrayList<PicasaImageWidgetController>(20);

  private String albumName = "Recent Pictures";

  boolean[] hasSavedPicture = new boolean[12];

  public ImagePickerController(ImagePickerView view) {
    this.view = view;

    init();
    this.view.init(this);
  }

  private void init() {
    this.token = User.checkLogin(Config.getScope());
    this.service = (PicasaProxyServiceAsync) GWT.create(PicasaProxyService.class);
    ServiceDefTarget serviceDef = (ServiceDefTarget) service;
    serviceDef.setServiceEntryPoint(GWT.getModuleBaseURL() + "picasaProxyService");

    service.getRecentImages(token, new ImageLoaderCallback(albumName));
  }

  @Override
  public void setSelectedMonth(int month) {
    selectedMonth = month;
  }

  @Override
  public void setSelectedPicture(int index) {
    this.selectedImage = index;
  }

  @Override
  public void deselectPicture() {
    selectedImage = -1;
  }

  @Override
  public PicasaImageBase getImage(int index) throws IndexOutOfBoundsException {
    return images.get(index).getImage();
  }

  @Override
  public PicasaImageBase nextImage() {
    selectedImage = (selectedImage + 1) % images.size();
    return getImage(selectedImage);
  }

  @Override
  public PicasaImageBase prevImage() {
    if (selectedImage == 0) {
      selectedImage = images.size() - 1;
    } else {
      selectedImage--;
    }
    return getImage(selectedImage);
  }

  @Override
  public String getAlbumName() {
    return albumName;
  }

  @Override
  public void saveCurrentSelection() {

    // TODO call the server and save the selection!

    hasSavedPicture[selectedMonth] = true;
  }

  @Override
  public boolean hasSavedPicture(int month) {
    return hasSavedPicture[month];
  }

  @Override
  public boolean isSelected(int month) {
    return selectedMonth == month;
  }

  @Override
  public void imageClicked(int index) {
    int i = 0;
    for (PicasaImageWidgetController image : images) {
      if (i == index) {
        image.setSelected(true);
      } else {
        image.setSelected(false);
      }
      i++;
    }

  }

  private class ImageLoaderCallback implements AsyncCallback<List<PicasaImage>> {

    private String album;

    public ImageLoaderCallback(String album) {
      this.album = album;
    }

    @Override
    public void onFailure(Throwable caught) {
      // TODO Auto-generated method stub

    }

    @Override
    public void onSuccess(List<PicasaImage> result) {
      images.clear();
      GWT.log("received " + result.size() + " images from '" + album + "'");
      int index = 0;
      PicasaImageWidget widget;
      PicasaImageWidgetController controller;
      for (PicasaImage image : result) {
        widget = new PicasaImageWidget();
        controller = new PicasaImageWidgetController(widget, image, index, ImagePickerController.this);
        images.add(controller);
        index++;
      }

    }
  }

}
