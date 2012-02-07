package de.graind.client.widgets.calendarPhoto;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import de.graind.client.model.PicasaImageBase;
import de.graind.client.service.GraindService;
import de.graind.client.service.GraindServiceAsync;
import de.graind.client.util.CalendarUtil;
import de.graind.client.util.Config;
import de.graind.client.widgets.calendarPhoto.CalendarPhotoView.Controller;

public class CalendarPhotoViewController implements Controller {

  private CalendarPhotoView calView;
  private GraindServiceAsync service;
  private PicasaImageBase image;

  private int currentMonth = CalendarUtil.getMonth(new Date());

  public CalendarPhotoViewController(CalendarPhotoView view) {
    this.calView = view;

    this.service = (GraindServiceAsync) GWT.create(GraindService.class);
    ServiceDefTarget serviceDef = (ServiceDefTarget) service;
    serviceDef.setServiceEntryPoint(GWT.getModuleBaseURL() + "graindService");

    service.getImageForMonth(Config.USERNAME, currentMonth, new AsyncCallback<PicasaImageBase>() {

      @Override
      public void onSuccess(PicasaImageBase result) {
        image = result;
        calView.init(CalendarPhotoViewController.this);
      }

      @Override
      public void onFailure(Throwable caught) {
        GWT.log("Error while getting today's image for User: " + Config.USERNAME);
        GWT.log(caught.toString());
      }
    });

  }

  @Override
  public PicasaImageBase getImage() {
    return image;
  }

  @Override
  public void refetchImage() {
    service.getImageForMonth(Config.USERNAME, currentMonth, new AsyncCallback<PicasaImageBase>() {

      @Override
      public void onSuccess(PicasaImageBase result) {
        image = result;
        calView.reload();
      }

      @Override
      public void onFailure(Throwable caught) {
        GWT.log("Error while getting today's image for User: " + Config.USERNAME);
        GWT.log(caught.toString());
      }
    });
  }

  @Override
  public void setCurrentMonth(int currentMonth) {
    this.currentMonth = currentMonth;
  }

  @Override
  public int getCurrentMonth() {
    return this.currentMonth;
  }

}
