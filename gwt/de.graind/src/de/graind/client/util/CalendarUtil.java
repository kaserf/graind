package de.graind.client.util;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

public class CalendarUtil {

  private static int[] daysPerMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
  private static String[] monthLabels = { "January", "February", "March", "April", "May", "June", "July", "August",
      "September", "October", "November", "December" };

  private static String[] weekdays = new String[] { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };

  private static final DateTimeFormat dayFormat = DateTimeFormat.getFormat("dd");
  private static final DateTimeFormat monthFormat = DateTimeFormat.getFormat("M");
  private static final DateTimeFormat monthLabelFormat = DateTimeFormat.getFormat(PredefinedFormat.MONTH);
  private static final DateTimeFormat yearFormat = DateTimeFormat.getFormat("yyyy");
  private static final DateTimeFormat timeShortFormat = DateTimeFormat.getFormat(PredefinedFormat.TIME_SHORT);
  private static final DateTimeFormat dateFormatDE = DateTimeFormat.getFormat("dd.MM.yyyy");
  private static final DateTimeFormat weekdayFormat = DateTimeFormat.getFormat("EEE");

  /**
   * Returns a String representation for a given month.
   * 
   * @param month
   *          a month between 1 and 12
   * @return Name of the given month (e.g. 3 -> March)
   */
  public static String getMonthLabel(int month) throws IllegalArgumentException {
    month--;
    if (month > 11)
      throw new IllegalArgumentException("A month larger then 12 is not allowed");
    else
      return monthLabels[month];
  }

  /**
   * Returns the number of days a month has.
   * 
   * @param month
   *          a month between 1 and 12
   * @param year
   * @return
   * @throws IllegalArgumentException
   */
  public static int getDaysOfMonth(int year, int month) throws IllegalArgumentException {
    // our array starts with 0
    month--;
    if (month > 11) {
      throw new IllegalArgumentException("A month larger then 12 is not allowed");
    } else {
      if (month == 1) {
        // check leap year
        if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
          return daysPerMonth[month] + 1;
        }
      }

      return daysPerMonth[month];
    }
  }

  /**
   * Calculate the day of a date.
   * 
   * @param date
   * @return
   */
  public static int getDay(Date date) {
    return Integer.parseInt(dayFormat.format(date));
  }

  /**
   * Calculate the month of a date.
   * 
   * @param date
   * @return
   */
  public static int getMonth(Date date) {
    return Integer.parseInt(monthFormat.format(date));
  }

  /**
   * Calculate the year of a date.
   * 
   * @param date
   * @return
   */
  public static int getYear(Date date) {
    return Integer.parseInt(yearFormat.format(date));
  }

  public static String getMonthLabel(Date date) {
    return monthLabelFormat.format(date);
  }

  public static String getTime(Date date) {
    return timeShortFormat.format(date);
  }

  /**
   * Return the day of the week: [0: Mon .. 6: Sun]
   * 
   * @param day
   * @param month
   * @param year
   */
  public static int getDayOfWeek(int day, int month, int year) {
    Date date = dateFormatDE.parse(day + "." + month + "." + year);
    String weekday = weekdayFormat.format(date);
    for (int i = 0; i < weekdays.length; i++) {
      if (weekday.equals(weekdays[i])) {
        return i;
      }
    }
    return -1;
  }

}
