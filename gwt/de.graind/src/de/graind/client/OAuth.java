package de.graind.client;

import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.api.gwt.oauth2.client.Callback;

public class OAuth {

  // oauth
  private static final Auth AUTH = Auth.get();
  private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
  private static final String GOOGLE_CLIENT_ID = "609756030397.apps.googleusercontent.com";
  private static final String PLUS_ME_SCOPE = "https://www.googleapis.com/auth/plus.me";

  public static void login(Callback<String, Throwable> callback) {
    final AuthRequest req = new AuthRequest(GOOGLE_AUTH_URL, GOOGLE_CLIENT_ID).withScopes(PLUS_ME_SCOPE);

    // Calling login() will display a popup to the user the first time it is
    // called. Once the user has granted access to the application,
    // subsequent calls to login() will not display the popup, and will
    // immediately result in the callback being given the token to use.
    AUTH.login(req, callback);
  }
}
