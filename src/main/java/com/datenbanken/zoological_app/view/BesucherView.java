package com.datenbanken.zoological_app.view;

import com.datenbanken.zoological_app.entity.Besucher;
import com.datenbanken.zoological_app.service.BesucherService;
import com.datenbanken.zoological_app.service.ShowService;
import com.datenbanken.zoological_app.view.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.UI;

import java.time.LocalDate;

@Route(value = "visitor", layout = MainLayout.class)
public class BesucherView extends VerticalLayout {

    private final BesucherService besucherService;
    private final ShowService showService;

    public BesucherView(BesucherService besucherService, ShowService showService) {
        this.besucherService = besucherService;
        this.showService = showService;

        // Title header
        add(new H1("Willkommen, Besucher!"));

        // Create the form components: DatePicker, ComboBox, and Button
        DatePicker besuchsdatumPicker = new DatePicker("Besuchsdatum");
        besuchsdatumPicker.setPlaceholder("Datum auswählen");

        ComboBox<Integer> ticketCountComboBox = new ComboBox<>("Anzahl der Tickets");
        ticketCountComboBox.setItems(1, 2, 3, 4, 5, 6, 7, 8); // Example range for tickets
        ticketCountComboBox.setPlaceholder("Wählen Sie die Anzahl der Tickets");

        Button addButton = new Button("Ticket buchen", event -> {
            LocalDate besuchsdatum = besuchsdatumPicker.getValue();
            Integer ticketCount = ticketCountComboBox.getValue();
            if (besuchsdatum != null && ticketCount != null) {
                try {
                    for (int i = 0; i < ticketCount; i++) {
                        // Create a new visitor for each ticket
                        Besucher besucher = new Besucher();
                        besucher.setBesuchsdatum(besuchsdatum);

                        // Save the new visitor to the database
                        besucherService.addBesucher(besucher);
                    }

                    // Show success message
                    Notification.show("Wir freuen uns auf Ihren Besuch!");

                    // Navigate to the KaufbestaetigungView with the selected date as a parameter
                    UI.getCurrent().navigate("kaufbestaetigung/" + besuchsdatum.toString());

                } catch (Exception e) {
                    // Show error message in case of failure
                    Notification.show("Fehler: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
                }
            } else {
                // Show message if required fields are not filled
                Notification.show("Bitte ein Besuchsdatum und die Anzahl der Tickets auswählen.");
            }
        });

        // Center the components in the layout
        VerticalLayout formLayout = new VerticalLayout(besuchsdatumPicker, ticketCountComboBox, addButton);
        formLayout.setAlignItems(Alignment.CENTER);

        add(formLayout);
    }
}
