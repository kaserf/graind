package de.graind.client;

import com.google.gwt.accounts.client.AuthSubStatus;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserStatusWidgetView {

  public interface Controller {
    void getUserName(AsyncCallback<String> callback);

    void logout(Runnable callback);

    void login();

    AuthSubStatus getStatus();
  }

  public void init(Controller controller);

}
