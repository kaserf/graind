package de.graind.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserStatusWidgetView {

  public interface Controller {
    void getUserName(AsyncCallback<String> callback);

    void logout(AsyncCallback<Void> callback);

    void login(AsyncCallback<Void> callback);

    boolean isLoggedIn();
  }

  public void init(Controller controller);

}
