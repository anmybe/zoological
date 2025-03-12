package com.datenbanken.zoological_app.view.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;

public class MainLayout extends AppLayout {

    public MainLayout() {
        // Logo
        Image logo = new Image("logo.png", "zoological logo");
        logo.setHeight("45px");

        Span appName = new Span("Zoological");
        appName.getStyle()
                .set("font-size", "30px")
                .set("font-weight", "bold")
                .set("margin-left", "10px");

        Button logoutButton = new Button("Logout", e -> {
            getUI().ifPresent(ui -> ui.navigate(""));
        });

        logoutButton.getStyle()
                .set("margin-right", "10px")
                .set("color", "white");

        Div headerContent = new Div(logo, appName, logoutButton);

        headerContent.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "space-between")
                .set("width", "100vw")
                .set("padding", "10px")
                .set("background-color", "#DDE473");

        addToNavbar(headerContent);
    }
}
