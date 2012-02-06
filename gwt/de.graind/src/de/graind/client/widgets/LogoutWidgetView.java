package de.graind.client.widgets;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LogoutWidgetView {

  public interface Controller {
    void getUserName(AsyncCallback<String> callback);

    void logout(Runnable callback);
  }

  public void init(Controller controller);
}
