package de.graind.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class LogoutWidget extends MenuBar implements LogoutWidgetView {
  private Controller controller;
  private MenuBar submenu;

  private Command logoutCommand = new Command() {
    @Override
    public void execute() {
      GWT.log("logout");
      controller.logout(new Runnable() {
        @Override
        public native void run() /*-{
			$wnd.location.reload();
    }-*/;
      });
    }
  };

  private Command openCalendarCommand = new Command() {
    @Override
    public void execute() {
      GWT.log("open google calendar");
    }
  };

  private Command openPicasaCommand = new Command() {
    @Override
    public void execute() {
      GWT.log("open picasa");
    }
  };

  public LogoutWidget() {
    super();
  }

  @Override
  public void init(Controller controller) {
    this.controller = controller;

    setupWidget();
  }

  public void setupWidget() {
    this.setAutoOpen(true);
    this.setAnimationEnabled(true);

    submenu = new MenuBar(true);
    submenu.addItem("Logout", logoutCommand);
    submenu.addItem("Open Calendar", openCalendarCommand);
    submenu.addItem("Open Picasa", openPicasaCommand);

    // load the username async style
    controller.getUserName(new AsyncCallback<String>() {
      @Override
      public void onSuccess(String result) {
        LogoutWidget.this.addItem(new MenuItem(result, submenu));
      }

      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub
      }
    });
  }
}
