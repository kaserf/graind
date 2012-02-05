package de.graind.client.widgets.picturePicker;

import java.util.List;

import de.graind.client.model.PicasaImage;
import de.graind.client.widgets.picturePicker.PicturePickerView.Controller;

public class PicturePickerController implements Controller {

  private PicturePickerView view;

  private int selectedMonth = -1;
  private int selectedImage = -1;

  private List<PicasaImage> images;

  private String albumName = "Album";

  boolean[] hasSavedPicture = new boolean[12];

  public PicturePickerController(PicturePickerView view) {
    this.view = view;
    this.view.init(this);
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
  public PicasaImage getImage(int index) throws IndexOutOfBoundsException {
    return images.get(index);
  }

  @Override
  public PicasaImage nextImage() {
    selectedImage = (selectedImage + 1) % images.size();
    return getImage(selectedImage);
  }

  @Override
  public PicasaImage prevImage() {
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

}
