package de.graind.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class LoginWidget extends Composite implements LoginWidgetView {

  private Controller controller;
  private HorizontalPanel hpanel;
  private Button button;

  private ClickHandler loginHandler = new ClickHandler() {
    @Override
    public void onClick(ClickEvent event) {
      GWT.log("login");
      controller.login();
    }
  };

  public LoginWidget() {
    this.hpanel = new HorizontalPanel();
    initWidget(hpanel);
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;

    setupWidget();
  }

  public void setupWidget() {

    this.button = new Button("Login");
    this.button.addClickHandler(loginHandler);
    this.hpanel.add(button);

  }
}
