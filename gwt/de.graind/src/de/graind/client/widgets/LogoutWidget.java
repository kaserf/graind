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

  private Command openGoogleCalendarCommand = new Command() {
    @Override
    public native void execute() /*-{
			$wnd.location.href = 'https://calendar.google.com';
    }-*/;
  };

  private Command openPicasaCommand = new Command() {
    @Override
    public native void execute() /*-{
			$wnd.location.href = 'https://picasaweb.google.com';
    }-*/;
  };

  private Command openSettingsCommand = new Command() {
    @Override
    public void execute() {
      submenu.removeItem(openSettingsItem);
      submenu.addItem(openCalendarItem);
      controller.toggleSettings();
    }
  };

  private Command openCalendarCommand = new Command() {
    @Override
    public void execute() {
      submenu.removeItem(openCalendarItem);
      submenu.addItem(openSettingsItem);
      controller.toggleSettings();
    }
  };

  private MenuItem openSettingsItem = new MenuItem("Settings", openSettingsCommand);
  private MenuItem openCalendarItem = new MenuItem("Calendar", openCalendarCommand);

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
    submenu.addItem(openSettingsItem);
    submenu.addItem("Open Google Calendar", openGoogleCalendarCommand);
    submenu.addItem("Open Google Picasa", openPicasaCommand);
    submenu.addItem("Logout", logoutCommand);

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
