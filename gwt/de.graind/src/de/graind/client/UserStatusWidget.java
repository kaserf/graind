package de.graind.client;

import com.google.gwt.accounts.client.AuthSubStatus;
import com.google.gwt.accounts.client.User;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import de.graind.shared.Config;

public class UserStatusWidget extends Composite implements UserStatusWidgetView {

  private Controller controller;
  private HorizontalPanel hpanel;
  private Label usernameLabel;

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
    this.usernameLabel = new Label();
    this.hpanel.add(usernameLabel);

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

    // TODO: show login or logout button.
    // TODO: move all static setup to constructor.
    addLogoutButton();

  }

  private void addLogoutButton() {
    final Button logoutButton = new Button("Logout");
    logoutButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        User.logout(new Runnable() {
          @Override
          public void run() {
            hpanel.remove(logoutButton);
          }
        }, Config.CALENDAR_SCOPE);
      }
    });

    if (User.getStatus(Config.CALENDAR_SCOPE) != AuthSubStatus.LOGGED_OUT) {
      hpanel.add(logoutButton);
    }
  }

}
