package com.datenbanken.zoological_app.view;


import com.datenbanken.zoological_app.view.layout.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;


@Route(value = "", layout = MainLayout.class)
public class LoginView extends VerticalLayout {

    private Button loginButton;
    private Button visitorButton;


    public LoginView() {
        // Title above the form
        H1 title = new H1("Login");

        loginButton = new Button("Manager", e -> UI.getCurrent().navigate("manager"));
        visitorButton = new Button("Visitor", e -> UI.getCurrent().navigate("visitor"));
        visitorButton.setWidth("50%");
        loginButton.setWidth("50%");

        // Form Layout (to center header, text field, and button in the remaining space)
        VerticalLayout formLayout = new VerticalLayout(title, loginButton, visitorButton);
        formLayout.setAlignItems(Alignment.CENTER);
        formLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        formLayout.setSizeFull();

        // Image
        com.vaadin.flow.component.html.Image image = new com.vaadin.flow.component.html.Image(
                "tier.png", "Animal background image");
        image.setWidth("100%");
        image.setHeight("calc(100vh)"); // Full height of the viewport
        image.getStyle().set("object-fit", "cover"); // Ensures the image scales correctly
        image.getStyle().set("margin", "0"); // Removes any default margin
        image.getStyle().set("padding", "0");

        // Main Layout (Split 30% / 70%)
        HorizontalLayout mainLayout = new HorizontalLayout(formLayout, image);
        mainLayout.setSizeFull(); // Make the layout fill the entire viewport
        mainLayout.setFlexGrow(3, formLayout); // Form layout takes 30% of space
        mainLayout.setFlexGrow(7, image); // Image takes 70% of space
        mainLayout.setSpacing(false); // Remove spacing between components
        mainLayout.setPadding(false); // Remove padding from the layout
        mainLayout.getStyle().set("overflow", "hidden"); // Remove margin from the layout

        // Page-level settings
        setSizeFull(); // Make the entire page take full height
        setPadding(false); // Remove padding from the page
        setSpacing(false); // Remove spacing from the page
        getStyle().set("margin", "0");
        getStyle().set("overflow", "hidden");// Remove any margin from the page
        add(mainLayout); // Add the main layout to the view
    }

}
