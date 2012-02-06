package de.graind.client.widgets.imagePicker;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.accounts.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import de.graind.client.model.PicasaImage;
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

  private List<PicasaImageWidgetController> imageControllers = new ArrayList<PicasaImageWidgetController>(20);

  private String albumName = "Recent Pictures";

  private int[] savedImages = new int[12];
  private boolean readyToSave = false;

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
    for (int i = 0; i < savedImages.length; i++) {
      savedImages[i] = -1;
    }
  }

  @Override
  public void setSelectedMonth(int month) {
    GWT.log("Month " + month + " + selected. Image: " + savedImages[month]);
    selectedMonth = month;
    if (savedImages[month] != -1) {
      imageClicked(savedImages[month]);
    } else {
      deselectPicture();
    }
  }

  @Override
  public void setSelectedPicture(int index) {
    this.selectedImage = index;
  }

  @Override
  public void deselectPicture() {
    for (PicasaImageWidgetController image : imageControllers) {
      image.setSelected(false);
    }
    this.selectedImage = -1;

  }

  @Override
  public PicasaImage getImage(int index) throws IndexOutOfBoundsException {
    return imageControllers.get(index).getImage();
  }

  @Override
  public PicasaImage nextImage() {
    selectedImage = (selectedImage + 1) % imageControllers.size();
    return getImage(selectedImage);
  }

  @Override
  public PicasaImage prevImage() {
    if (selectedImage == 0) {
      selectedImage = imageControllers.size() - 1;
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
  }

  @Override
  public boolean hasSavedPicture(int month) {
    return savedImages[month] != -1;
  }

  @Override
  public boolean isSelected(int month) {
    return selectedMonth == month;
  }

  @Override
  public boolean isReadyToSave() {
    return readyToSave;
  }

  @Override
  public void imageClicked(int index) {
    // deselect old selected image
    if (selectedImage != -1) {
      imageControllers.get(selectedImage).setSelected(false);
    }

    // select new one
    if (index != -1) {
      imageControllers.get(index).setSelected(true);
    }
    if (selectedMonth != -1) {
      savedImages[selectedMonth] = index;
      checkAllImagesSet();
    }
    setSelectedPicture(index);

    view.onMonthStatusUpdate();
  }

  private void checkAllImagesSet() {
    // check
    boolean allImagesSet = true;
    for (int i = 0; i < savedImages.length; i++) {
      if (savedImages[i] == -1) {
        allImagesSet = false;
        break;
      }
    }
    this.readyToSave = allImagesSet;
    if (readyToSave) {
      view.onIsReadyToSave(true);
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
      imageControllers.clear();
      GWT.log("received " + result.size() + " images from '" + album + "'");

      int index = 0;
      PicasaImageWidget widget;
      PicasaImageWidgetController controller;
      final List<PicasaImageWidget> imageWidgets = new ArrayList<PicasaImageWidget>(result.size());
      for (PicasaImage image : result) {
        widget = new PicasaImageWidget(true);
        imageWidgets.add(widget);
        controller = new PicasaImageWidgetController(widget, image, index, ImagePickerController.this);
        imageControllers.add(controller);
        index++;
      }

      view.setImages(imageWidgets);

    }
  }

}
