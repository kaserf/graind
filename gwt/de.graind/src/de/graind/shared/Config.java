package de.graind.shared;

public class Config {
  public static final String API_KEY = "ABQIAAAAnkZoFwqQN8CkyJcz9_HK-xRPN_MKWMkv79GZUfaw5ZO6Y6XxJxSLFBqJP4hF05945dgmGiur4_tqpg";
  public static final String CALENDAR_SCOPE = "http://www.google.com/calendar/feeds";
  public static final String PICASA_SCOPE = "http://picasaweb.google.com/data";
  public static final String APPLICATION_NAME = "Graind";
  public static final String PICASA_ALBUM_URI = "https://picasaweb.google.com/data/feed/api/user/default?kind=album&access=public";

  public static String getScope() {
    // add all scopes we need here
    return CALENDAR_SCOPE + " " + PICASA_SCOPE;
  }
}
