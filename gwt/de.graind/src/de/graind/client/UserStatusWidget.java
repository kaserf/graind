package de.graind.client;

import com.google.gwt.accounts.client.AuthSubStatus;
import com.google.gwt.accounts.client.User;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.graind.shared.Config;

public class UserStatusWidget extends Composite implements UserStatusWidgetView {

  private Controller controller;
  private HorizontalPanel hpanel;

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

    Button b = new Button("Google OAuth");
    b.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        controller.login();
      }
    });

    hpanel.add(b);
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
        }, Config.gdataScope);
      }
    });

    if (User.getStatus(Config.gdataScope) != AuthSubStatus.LOGGED_OUT) {
      hpanel.add(logoutButton);
    }
  }

}
