package de.graind.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import de.graind.client.widgets.LogoutWidget;
import de.graind.client.widgets.LogoutWidgetController;
import de.graind.client.widgets.calendarPhoto.CalendarPhotoViewController;
import de.graind.client.widgets.calendarPhoto.CalendarPhotoWidget;
import de.graind.client.widgets.day.DayWidget;
import de.graind.client.widgets.day.DayWidgetController;
import de.graind.client.widgets.imagePicker.ImagePickerController;
import de.graind.client.widgets.imagePicker.ImagePickerWidget;
import de.graind.client.widgets.monthly.MonthlyWidget;
import de.graind.client.widgets.monthly.MonthlyWidgetController;

public class CalendarUI extends Composite {

  private static CalendarUIUiBinder uiBinder = GWT.create(CalendarUIUiBinder.class);

  @UiField
  SimplePanel topRowLeft;
  @UiField
  LogoutWidget topRowRight;
  @UiField
  SimplePanel topRowCenter;
  @UiField
  DockLayoutPanel centerSpace;
  @UiField
  DayWidget calSpaceLeft;
  @UiField
  MonthlyWidget calSpaceMonthly;

  private boolean calendarVisible = true;
  private ImagePickerWidget imagePickerWidget = new ImagePickerWidget();

  private LogoutWidgetController logoutWidgetController;

  private ImagePickerController imagePickerController;

  private CalendarPhotoWidget calPhotoWidget;

  interface CalendarUIUiBinder extends UiBinder<Widget, CalendarUI> {
  }

  public CalendarUI() {
    initWidget(uiBinder.createAndBindUi(this));
    initController();
  }

  public CalendarUI(String firstName) {
    this();
  }

  private void initController() {
    logoutWidgetController = new LogoutWidgetController(topRowRight, this, new AsyncCallback<Void>() {

      @Override
      public void onSuccess(Void result) {
        new MonthlyWidgetController(calSpaceMonthly);
        new DayWidgetController(calSpaceLeft);

        calPhotoWidget = new CalendarPhotoWidget();
        new CalendarPhotoViewController(calPhotoWidget);
        centerSpace.add(calPhotoWidget);

        // Testing saving pics to db
        // GraindServerTestWidget graindTest = new GraindServerTestWidget();
        // new GraindServerTestWidgetController(graindTest);
        // RootPanel.get().add(graindTest);

        // Testing fetching photos and albums from picasa
        // PicasaTestWidget picasaTest = new PicasaTestWidget();
        // new PicasaTestWidgetController(picasaTest);
        // RootPanel.get().add(picasaTest);
      }

      @Override
      public void onFailure(Throwable caught) {
        GWT.log("ooops! something went wrong, load error or something");
      }
    });
  }

  /**
   * Show the settings image picker
   */
  public void showSettings() {
    if (this.imagePickerController == null) {
      imagePickerController = new ImagePickerController(imagePickerWidget, CalendarUI.this);
    }

    if (this.calendarVisible) {
      // hide calendar, show image picker
      centerSpace.remove(calPhotoWidget);
      centerSpace.add(imagePickerWidget);

      this.calendarVisible = false;
    }
  }

  /**
   * Hide the settings image picker
   */
  public void hideSettings() {
    if (!this.calendarVisible) {
      // hide picker, show calendar
      centerSpace.remove(imagePickerWidget);
      centerSpace.add(calPhotoWidget);

      // tell logout widget to enable the button
      logoutWidgetController.hideSettings();

      this.calendarVisible = true;
    }
  }
}
