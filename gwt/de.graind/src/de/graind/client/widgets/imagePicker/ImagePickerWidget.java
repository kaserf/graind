package de.graind.client.widgets.imagePicker;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.graind.client.widgets.picasaImage.PicasaImageWidget;

public class ImagePickerWidget extends Composite implements ImagePickerView {

  private static final int TABLE_WIDTH = 3;
  private static PicturePickerUiBinder uiBinder = GWT.create(PicturePickerUiBinder.class);
  private Controller controller;
  @UiField
  Style style;
  @UiField
  VerticalPanel selectorMenu;
  @UiField
  Label albumNameLabel;
  @UiField
  FlexTable pictureTable;

  private Label[] monthButtons = new Label[12];
  private Label saveButton;
  private Label cancelButton;

  public ImagePickerWidget() {
    initWidget(uiBinder.createAndBindUi(this));
    setUpSelectorMenu();
    setUpSaveButton();
    setUpCancelButton();
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;

    this.albumNameLabel.setText(controller.getAlbumName());
  }

  private void setUpSelectorMenu() {
    selectorMenu.clear();
    final Label head = new Label("Connect months to pics!");
    head.setStyleName(style.label());
    selectorMenu.add(head);
    final Label explain = new Label("First select a month,\nthen a pic.");
    explain.setStyleName(style.explain());
    selectorMenu.add(explain);

    for (int month = 0; month < monthButtons.length; month++) {
      monthButtons[month] = new Label(monthLabels[month]);
      monthButtons[month].setStyleName(style.button());
      monthButtons[month].addClickHandler(new MonthClickHandler(month));

      MonthHoverHandler hoverHandler = new MonthHoverHandler(month);
      monthButtons[month].addMouseOverHandler(hoverHandler);
      monthButtons[month].addMouseOutHandler(hoverHandler);

      selectorMenu.add(monthButtons[month]);
    }

  }

  private void setUpSaveButton() {
    this.saveButton = new Label("Save");
    final SaveHoverHandler hoverHandler = new SaveHoverHandler();
    saveButton.addMouseOverHandler(hoverHandler);
    saveButton.addMouseOutHandler(hoverHandler);

    saveButton.addClickHandler(new SaveClickHandler());

    saveButton.setStyleName(style.saveButtonInactive());

    selectorMenu.add(saveButton);
  }

  private void setUpCancelButton() {
    this.cancelButton = new Label("Cancel");
    final CancelHoverHandler hoverHandler = new CancelHoverHandler();
    cancelButton.addMouseOverHandler(hoverHandler);
    cancelButton.addMouseOutHandler(hoverHandler);

    cancelButton.addClickHandler(new CancelClickHandler());

    cancelButton.setStyleName(style.cancelButtonActive());

    selectorMenu.add(cancelButton);
  }

  @Override
  public void calendarSaved(boolean successful) {
    // TODO weg weg weg
  }

  private void updateMonthButtonStyles() {
    for (int month = 0; month < monthButtons.length; month++) {
      if (controller.isSelected(month)) {
        if (controller.hasSavedPicture(month)) {
          monthButtons[month].setStyleName(style.buttonWithPictureSelected());
        } else {
          monthButtons[month].setStyleName(style.buttonSelected());
        }
      } else {
        if (controller.hasSavedPicture(month)) {
          monthButtons[month].setStyleName(style.buttonWithPicture());
        } else {
          monthButtons[month].setStyleName(style.button());
        }
      }
    }

  }

  @Override
  public void setImages(List<PicasaImageWidget> images) {
    pictureTable.clear();
    int index = 0;
    for (PicasaImageWidget widget : images) {
      pictureTable.setWidget(index / TABLE_WIDTH, index % TABLE_WIDTH, widget);
      index++;
    }
  }

  @Override
  public void onMonthStatusUpdate() {
    updateMonthButtonStyles();
  }

  @Override
  public void onIsReadyToSave(boolean ready) {
    if (ready) {
      saveButton.setStyleName(style.saveButtonActive());
    } else {
      saveButton.setStyleName(style.saveButtonInactive());
    }
  }

  interface PicturePickerUiBinder extends UiBinder<Widget, ImagePickerWidget> {
  }

  interface Style extends CssResource {
    String main();

    String medium();

    String normal();

    String west();

    String center();

    String label();

    String explain();

    String button();

    String buttonHovered();

    String buttonSelected();

    String buttonHoveredSelected();

    String buttonWithPicture();

    String buttonWithPictureSelected();

    String buttonWithPictureHovered();

    String buttonWithPictureHoveredSelected();

    String saveButtonInactive();

    String saveButtonActive();

    String saveButtonActiveHovered();

    String cancelButtonInactive();

    String cancelButtonActive();

    String cancelButtonActiveHovered();

  }

  private class MonthClickHandler implements ClickHandler {

    int month;

    public MonthClickHandler(int month) {
      this.month = month;
    }

    @Override
    public void onClick(ClickEvent event) {
      controller.setSelectedMonth(month);
      updateMonthButtonStyles();
    }

  }

  private class MonthHoverHandler implements MouseOverHandler, MouseOutHandler {

    private int month;

    public MonthHoverHandler(int month) {
      this.month = month;
    }

    @Override
    public void onMouseOut(MouseOutEvent event) {
      if (controller.isSelected(month)) {
        if (controller.hasSavedPicture(month)) {
          monthButtons[month].setStyleName(style.buttonWithPictureSelected());
        } else {
          monthButtons[month].setStyleName(style.buttonSelected());
        }
      } else {
        if (controller.hasSavedPicture(month)) {
          monthButtons[month].setStyleName(style.buttonWithPicture());
        } else {
          monthButtons[month].setStyleName(style.button());
        }
      }
    }

    @Override
    public void onMouseOver(MouseOverEvent event) {
      if (controller.isSelected(month)) {
        if (controller.hasSavedPicture(month)) {
          monthButtons[month].setStyleName(style.buttonWithPictureHoveredSelected());
        } else {
          monthButtons[month].setStyleName(style.buttonHoveredSelected());
        }
      } else {
        if (controller.hasSavedPicture(month)) {
          monthButtons[month].setStyleName(style.buttonWithPictureHovered());
        } else {
          monthButtons[month].setStyleName(style.buttonHovered());
        }
      }
    }

  }

  private class SaveHoverHandler implements MouseOverHandler, MouseOutHandler {
    @Override
    public void onMouseOut(MouseOutEvent event) {
      if (controller.isReadyToSave()) {
        saveButton.setStyleName(style.saveButtonActive());
      }
    }

    @Override
    public void onMouseOver(MouseOverEvent event) {
      if (controller.isReadyToSave()) {
        saveButton.setStyleName(style.saveButtonActiveHovered());
      }
    }
  }

  private class SaveClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      if (controller.isReadyToSave()) {
        controller.saveCurrentSelection();
      }
    }
  }

  private class CancelHoverHandler implements MouseOverHandler, MouseOutHandler {
    @Override
    public void onMouseOut(MouseOutEvent event) {
      cancelButton.setStyleName(style.cancelButtonActive());
    }

    @Override
    public void onMouseOver(MouseOverEvent event) {
      cancelButton.setStyleName(style.cancelButtonActiveHovered());
    }
  }

  private class CancelClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      controller.cancelSelection();
    }
  }
}
