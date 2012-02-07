package de.graind.client.widgets.imagePicker;

import java.util.List;

import de.graind.client.widgets.picasaImage.PicasaImageWidget;

public interface ImagePickerView {
  public String[] monthLabels = new String[] { "January", "February", "March", "April", "May", "June", "July",
      "August", "September", "October", "November", "December" };

  public interface Controller {
    public static int JAN = 0;
    public static int FEB = 1;
    public static int MAR = 2;
    public static int APR = 3;
    public static int MAY = 4;
    public static int JUN = 5;
    public static int JUL = 6;
    public static int AUG = 7;
    public static int SEP = 8;
    public static int OCT = 9;
    public static int NOV = 10;
    public static int DEC = 11;

    void setSelectedMonth(int month);

    void setSelectedPicture(int index);

    void deselectPicture();

    // PicasaImageBase nextImage();
    //
    // PicasaImageBase prevImage();
    //
    // PicasaImageBase getImage(int index) throws IndexOutOfBoundsException;

    void saveCurrentSelection();

    void cancelSelection();

    boolean hasSavedPicture(int month);

    boolean isSelected(int month);

    String getAlbumName();

    void imageClicked(int index);

    boolean isReadyToSave();

  }

  void init(Controller controller);

  void setImages(List<PicasaImageWidget> images);

  void onMonthStatusUpdate();

  void onIsReadyToSave(boolean ready);

}
