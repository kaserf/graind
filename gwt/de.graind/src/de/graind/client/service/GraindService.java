package de.graind.client.service;

import com.google.gwt.user.client.rpc.RemoteService;

import de.graind.client.model.PicasaImageBase;

public interface GraindService extends RemoteService {

  /**
   * Save the pictures per month.
   * 
   * @param images
   *          An array of 12 PicasaImages, one per month
   */
  void saveMonthlyPictureSelection(String username, PicasaImageBase[] images);

  /**
   * Remove all saved monthly pictures for the given username.
   * 
   * @param username
   */
  void deleteMonthlyPictureSelection(String username);
}
