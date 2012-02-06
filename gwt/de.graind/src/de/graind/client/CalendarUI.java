package de.graind.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import de.graind.client.examples.GraindServerTestWidget;
import de.graind.client.examples.GraindServerTestWidgetController;
import de.graind.client.widgets.DayWidget;
import de.graind.client.widgets.DayWidgetController;
import de.graind.client.widgets.LogoutWidget;
import de.graind.client.widgets.LogoutWidgetController;
import de.graind.client.widgets.MonthlyWidget;
import de.graind.client.widgets.MonthlyWidgetController;
import de.graind.client.widgets.imagePicker.ImagePickerController;
import de.graind.client.widgets.imagePicker.ImagePickerView;

public class CalendarUI extends Composite {

  private static CalendarUIUiBinder uiBinder = GWT.create(CalendarUIUiBinder.class);

  @UiField
  SimplePanel topRowLeft;
  @UiField
  LogoutWidget topRowRight;
  @UiField
  SimplePanel topRowCenter;
  @UiField
  Composite centerSpace;
  @UiField
  DayWidget calSpaceLeft;
  @UiField
  MonthlyWidget calSpaceMonthly;

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
    new LogoutWidgetController(topRowRight, new AsyncCallback<Void>() {

      @Override
      public void onSuccess(Void result) {
        new MonthlyWidgetController(calSpaceMonthly);
        new DayWidgetController(calSpaceLeft);
        new ImagePickerController((ImagePickerView) centerSpace);

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

}
