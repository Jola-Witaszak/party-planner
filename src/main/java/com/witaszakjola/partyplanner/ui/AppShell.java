package com.witaszakjola.partyplanner.ui;

import com.vaadin.flow.component.page.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(value = "lumo", variant = Lumo.DARK)
@Meta(name = "Author", content = "Donald Duck")
@PWA(name = "Party Fun Spontan", shortName = "Party-app")
//@Inline("my-custom-javascript.js")
@Viewport("width=device-width, initial-scale=1")
@BodySize(height = "100vh", width = "100vw")
@PageTitle("my-title")
@Push(value = PushMode.AUTOMATIC, transport = Transport.WEBSOCKET_XHR)
public class AppShell implements AppShellConfigurator {

    public void configurePage(AppShellSettings settings) {

    }
}
