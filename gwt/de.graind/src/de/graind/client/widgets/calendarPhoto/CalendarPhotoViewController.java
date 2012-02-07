package de.graind.client.widgets.calendarPhoto;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import de.graind.client.model.PicasaImageBase;
import de.graind.client.service.GraindService;
import de.graind.client.service.GraindServiceAsync;
import de.graind.client.util.CalendarUtil;
import de.graind.client.widgets.calendarPhoto.CalendarPhotoView.Controller;
import de.graind.shared.Config;

public class CalendarPhotoViewController implements Controller {

  private CalendarPhotoView calView;
  private GraindServiceAsync service;
  private PicasaImageBase image;

  public CalendarPhotoViewController(CalendarPhotoView view) {
    this.calView = view;

    this.service = (GraindServiceAsync) GWT.create(GraindService.class);
    ServiceDefTarget serviceDef = (ServiceDefTarget) service;
    serviceDef.setServiceEntryPoint(GWT.getModuleBaseURL() + "graindService");

    service.getImageForMonth(Config.USERNAME, CalendarUtil.getMonth(new Date()), new AsyncCallback<PicasaImageBase>() {

      @Override
      public void onSuccess(PicasaImageBase result) {
        image = result;
        calView.init(CalendarPhotoViewController.this);
      }

      @Override
      public void onFailure(Throwable caught) {
        GWT.log("Error while getting today's image for User: " + Config.USERNAME);
        GWT.log(caught.toString());
        Window.alert("oops, i really don't know what happened..");

      }
    });

  }

  @Override
  public PicasaImageBase getImage() {
    if (image == null) {
      throw new IllegalStateException("not initialized yet, try again later");
    }
    return image;
  }

}
