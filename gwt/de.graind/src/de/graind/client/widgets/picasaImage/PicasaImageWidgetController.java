package de.graind.client.widgets.picasaImage;

import de.graind.client.model.PicasaImage;
import de.graind.client.widgets.imagePicker.ImagePickerView;
import de.graind.client.widgets.picasaImage.PicasaImageWidgetView.Controller;

public class PicasaImageWidgetController implements Controller {

  private PicasaImageWidgetView view;
  private PicasaImage image;
  private ImagePickerView.Controller controller;
  private int index;

  public PicasaImageWidgetController(PicasaImageWidgetView view, PicasaImage image, int indexOfImage,
      ImagePickerView.Controller controller) {
    this.view = view;
    this.image = image;
    this.controller = controller;
    this.index = indexOfImage;

    view.init(this);
  }

  @Override
  public void imageClicked() {
    controller.imageClicked(index);
  }

  @Override
  public PicasaImage getImage() {
    return image;
  }

  @Override
  public void setSelected(boolean selected) {
    view.setSelected(selected);
  }

}
