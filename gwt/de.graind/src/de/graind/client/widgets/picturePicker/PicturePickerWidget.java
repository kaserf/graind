package de.graind.client.widgets.picturePicker;

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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PicturePickerWidget extends Composite implements PicturePickerView {

  private static PicturePickerUiBinder uiBinder = GWT.create(PicturePickerUiBinder.class);
  private Controller controller;
  @UiField
  Style style;
  @UiField
  VerticalPanel selectorMenu;
  @UiField
  Label albumNameLabel;

  private Label[] monthButtons = new Label[12];

  interface PicturePickerUiBinder extends UiBinder<Widget, PicturePickerWidget> {
  }

  interface Style extends CssResource {
    String main();

    String medium();

    String normal();

    String west();

    String center();

    String label();

    String button();

    String buttonHovered();

    String buttonSelected();

    String buttonHoveredSelected();

    String buttonWithPicture();

    String buttonWithPictureSelected();

    String buttonWithPictureHovered();

    String buttonWithPictureHoveredSelected();

  }

  public PicturePickerWidget() {
    initWidget(uiBinder.createAndBindUi(this));
    setUpSelectorMenu();
  }

  public PicturePickerWidget(String firstName) {
    this();
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;

    this.albumNameLabel.setText(controller.getAlbumName());
  }

  private void setUpSelectorMenu() {
    selectorMenu.clear();
    Label head = new Label("Connect months to pics!");
    head.setStyleName(style.label());
    selectorMenu.add(head);

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

  @Override
  public void pictureSaved(int month, boolean successful) {
    if (successful) {
      monthButtons[month].setStyleName(style.buttonWithPicture());
    } else {
      monthButtons[month].setStyleName(style.button());
    }
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

}
