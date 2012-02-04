package de.graind.client;

import com.google.gwt.accounts.client.AuthSubStatus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class UserStatusWidget extends Composite implements UserStatusWidgetView {

  private Controller controller;
  private HorizontalPanel hpanel;
  private Label usernameLabel;
  private Button button;

  private ClickHandler logoutHandler = new ClickHandler() {
    @Override
    public void onClick(ClickEvent event) {
      GWT.log("logout");
      controller.logout(new Runnable() {
        @Override
        public native void run() /*-{
			$wnd.location.reload();
    }-*/;
      });
      // GWT.log("logged out - refresh site");
    }
  };

  private ClickHandler loginHandler = new ClickHandler() {
    @Override
    public void onClick(ClickEvent event) {
      GWT.log("login");
      controller.login();
    }
  };

  public UserStatusWidget() {
    this.hpanel = new HorizontalPanel();
    initWidget(hpanel);
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;

    setupWidget();
  }

  public void setupWidget() {

    this.button = new Button();
    this.hpanel.add(button);

    if (controller.getStatus() == AuthSubStatus.LOGGED_IN) {
      this.usernameLabel = new Label();
      this.hpanel.insert(usernameLabel, 0);

      // load the username async style
      controller.getUserName(new AsyncCallback<String>() {
        @Override
        public void onSuccess(String result) {
          usernameLabel.setText(result);
        }

        @Override
        public void onFailure(Throwable caught) {
          // TODO Auto-generated method stub
        }
      });

      this.button.setText("Logout");
      this.button.addClickHandler(logoutHandler);
    } else {
      this.button.setText("Login");
      this.button.addClickHandler(loginHandler);
    }
  }
}
