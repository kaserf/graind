package de.graind.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.graind.client.model.PicasaImageBase;

public interface GraindServiceAsync {

  void saveMonthlyPictureSelection(String username, PicasaImageBase[] images, AsyncCallback<Void> callback);

  void deleteMonthlyPictureSelection(String username, AsyncCallback<Void> callback);

  void getImageForMonth(String username, int month, AsyncCallback<PicasaImageBase> callback);

  void getAllImages(String username, AsyncCallback<PicasaImageBase[]> callback);

}
