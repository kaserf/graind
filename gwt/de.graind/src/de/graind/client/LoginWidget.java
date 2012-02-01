package de.graind.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class LoginWidget extends Composite implements LoginWidgetView {

  private Controller controller;
  private HorizontalPanel hpanel;

  @Override
  public void init(Controller controller) {
    this.controller = controller;

    setupWidget();
  }

  public void setupWidget() {

    this.hpanel = new HorizontalPanel();

    // TODO: show login or logout button.
    addLogoutButton();

    initWidget(hpanel);
  }

  private void addLogoutButton() {
    final Button logoutButton = new Button("Logout");
    logoutButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        /*
         * User.logout(new Runnable() {
         * 
         * @Override public void run() { hpanel.remove(logoutButton); } },
         * Config.gdataScope);
         */
      }
    });

    // TODO: load gdata first.
    // if (User.getStatus(Config.gdataScope) != AuthSubStatus.LOGGED_OUT) {
    hpanel.add(logoutButton);
    // }
  }

}
