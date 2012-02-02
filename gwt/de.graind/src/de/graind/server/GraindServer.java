package de.graind.server;

import java.util.Date;
import java.util.List;

import de.graind.server.model.interfaces.CalendarTemplate;
import de.graind.server.model.interfaces.User;

public interface GraindServer {

  /**
   * @param userId
   * @return All Templates, the user has saved as template.
   */
  public List<CalendarTemplate> getFavouritesOf(int userId);

  /**
   * 
   * @param userId
   * @return Template the users has selected to be shown per default.
   */
  public CalendarTemplate getDefaultTemplateOf(int userId);

  /**
   * @return the User-object for the currently logged in user.
   */
  public User getCurrentUserData();

  /**
   * Update the defaultRefreshRate of the user with userId
   * 
   * @param refeshRate
   *          new refeshRate in days
   * @param userId
   */
  public void updateDefaultRefreshRate(int refeshRate, int userId);

  /**
   * @param userId
   * @return the URL to the Picture which shall be shown today
   */
  public String getPicture(int userId);

  /**
   * @param day
   *          The day of the Picture
   * @return Picture which is to be shown for that specific date
   */
  public String getPictureOf(Date day);

  /**
   * This method shall be taken for browsing through months since no specific
   * date is selected therefore.
   * 
   * @param month
   *          e.g. <code>1</code> for January
   * @param year
   *          e.g. <code>2012</code>
   * @return the Picture of that month.
   */
  public String getPictureOf(int month, int year);

  /**
   * @param offset
   *          paging offset
   * @param amount
   *          paging size
   * @return A list of available templates
   */
  public List<CalendarTemplate> getAvailableTemplates(int offset, int amount);

}
