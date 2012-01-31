package de.graind.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class WelcomePage implements EntryPoint{

	@Override
	public void onModuleLoad() {
		Label label = new Label("Hello User!");
		Button button = new Button("Click me to sign in...");
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("login go!");
			}
		});
		
		RootPanel.get().add(label);
		RootPanel.get().add(button);
	}

}
