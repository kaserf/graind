package de.graind.client.widgets.imagePicker;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.accounts.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import de.graind.client.CalendarUI;
import de.graind.client.model.PicasaImage;
import de.graind.client.model.PicasaImageBase;
import de.graind.client.service.GraindService;
import de.graind.client.service.GraindServiceAsync;
import de.graind.client.service.PicasaProxyService;
import de.graind.client.service.PicasaProxyServiceAsync;
import de.graind.client.util.Config;
import de.graind.client.widgets.imagePicker.ImagePickerView.Controller;
import de.graind.client.widgets.picasaImage.PicasaImageWidget;
import de.graind.client.widgets.picasaImage.PicasaImageWidgetController;
import de.graind.client.widgets.picasaImage.PicasaImageWidgetView.Controller.ClickHandler;

public class ImagePickerController implements Controller {

  private String token;
  private PicasaProxyServiceAsync picasaService;
  private GraindServiceAsync graindService;

  private ImagePickerView view;
  private int selectedMonth = -1;
  private int selectedImage = -1;

  private List<PicasaImageWidgetController> imageControllers = new ArrayList<PicasaImageWidgetController>(20);

  private String albumName = "Recent Pictures";

  private int[] savedImages = new int[12];
  private PicasaImageBase[] serverSettings;

  private boolean readyToSave = false;
  private CalendarUI parentController;

  public ImagePickerController(ImagePickerView view, CalendarUI parentController) {
    this.view = view;
    this.parentController = parentController;

    init();
    this.view.init(this);
  }

  private void init() {
    this.token = User.checkLogin(Config.getScope());
    this.picasaService = (PicasaProxyServiceAsync) GWT.create(PicasaProxyService.class);
    ServiceDefTarget serviceDef = (ServiceDefTarget) picasaService;
    serviceDef.setServiceEntryPoint(GWT.getModuleBaseURL() + "picasaProxyService");

    this.graindService = (GraindServiceAsync) GWT.create(GraindService.class);
    serviceDef = (ServiceDefTarget) graindService;
    serviceDef.setServiceEntryPoint(GWT.getModuleBaseURL() + "graindService");

    for (int i = 0; i < savedImages.length; i++) {
      savedImages[i] = -1;
    }

    graindService.getAllImages(Config.USERNAME, new AsyncCallback<PicasaImageBase[]>() {
      @Override
      public void onFailure(Throwable caught) {
        GWT.log("failed to load saved images.");
      }

      @Override
      public void onSuccess(PicasaImageBase[] result) {
        // we should always get an array of size 12
        serverSettings = result;

        // now we have the settings, load all images
        picasaService.getRecentImages(token, new ImageLoaderCallback(albumName));
      }
    });
  }

  @Override
  public void setSelectedMonth(int month) {
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
  public String getAlbumName() {
    return albumName;
  }

  @Override
  public void saveCurrentSelection() {
    readyToSave = false;
    view.onIsReadyToSave(false);
    PicasaImageBase[] toSave = new PicasaImageBase[12];
    for (int i = 0; i < toSave.length; i++) {
      toSave[i] = imageControllers.get(savedImages[i]).getImage();
    }
    graindService.saveMonthlyPictureSelection(Config.USERNAME, toSave, new AsyncCallback<Void>() {

      @Override
      public void onSuccess(Void result) {
        checkAllImagesSet();
        parentController.hideSettings();
      }

      @Override
      public void onFailure(Throwable caught) {
        GWT.log("Error while saving an Calendar");
        GWT.log(caught.toString());
        Window.alert("Error while saving, please try again");
        checkAllImagesSet();
        view.onMonthStatusUpdate();
      }
    });
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

      ClickHandler clickHandler = new ClickHandler() {
        @Override
        public void onClick(int idOfImage) {
          imageClicked(idOfImage);
        }
      };

      int index = 0;
      PicasaImageWidget widget;
      PicasaImageWidgetController controller;
      final List<PicasaImageWidget> imageWidgets = new ArrayList<PicasaImageWidget>(result.size());
      for (PicasaImage image : result) {
        widget = new PicasaImageWidget(true);
        imageWidgets.add(widget);

        // check with the already saved images.
        // TODO: this should be implemented more efficient
        for (int i = 0; i < serverSettings.length; i++) {
          if (serverSettings[0] != null && serverSettings[i].getUrl().equals(image.getUrl())) {
            savedImages[i] = index;
          }
        }

        controller = new PicasaImageWidgetController(widget, image, index, true);
        controller.registerForClickEvent(clickHandler);
        imageControllers.add(controller);
        index++;
      }

      view.onMonthStatusUpdate();
      checkAllImagesSet();
      view.setImages(imageWidgets);
    }
  }

  @Override
  public void cancelSelection() {
    this.parentController.hideSettings();
  }

}
