package com.datenbanken.zoological_app.view;

import com.datenbanken.zoological_app.entity.Besucher;
import com.datenbanken.zoological_app.entity.Show;
import com.datenbanken.zoological_app.service.BesucherService;
import com.datenbanken.zoological_app.service.ShowService;
import com.datenbanken.zoological_app.view.layout.MainLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import java.time.LocalDate;
import java.util.List;

@Route(value = "kaufbestaetigung/:besuchsdatum", layout = MainLayout.class)
public class KaufbestaetigungView extends VerticalLayout implements BeforeEnterObserver {

    private final BesucherService besucherService;
    private final ShowService showService;
    public final Grid<Show> showGrid = new Grid<>(Show.class);
    private final Grid<Besucher> besucherGrid = new Grid<>(Besucher.class);
    private LocalDate besuchsdatum;
    private Button bookShowButton;
    private Notification notification;


    public KaufbestaetigungView(BesucherService besucherService, ShowService showService) {
        this.besucherService = besucherService;
        this.showService = showService;

        // Title header
        add(new H1("Vielen Dank für Ihren Einkauf!"));

        // Initialize the button
        this.bookShowButton = new Button("Show Buchen");
    }


    // Method to handle route parameter and perform logic when entering the view
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Retrieve the 'besuchsdatum' from the route parameters
        String dateStr = event.getRouteParameters().get("besuchsdatum").orElse(null);
        if (dateStr != null) {

            this.besuchsdatum = LocalDate.parse(dateStr);

            // Display purchased tickets for the selected visit date
            List<Besucher> gekaufteTickets = besucherService.findByBesuchsdatum(besuchsdatum);

            // Remove the default columns
            besucherGrid.removeAllColumns();

            // Add the necessary columns
            besucherGrid.setItems(gekaufteTickets);
            besucherGrid.addColumn(Besucher::getBesucherId).setHeader("Besucher ID").setAutoWidth(true).setSortable(true);
            besucherGrid.addColumn(Besucher::getBesuchsdatum).setHeader("Besuchsdatum").setAutoWidth(true).setSortable(true);
            besucherGrid.addColumn(besucher -> {
                // Convert shows to a string or display as needed
                return String.join(", ", besucher.getShows().stream().map(Show::getShowName).toList());
            }).setHeader("Shows").setAutoWidth(true);


            H1 showsHeader = new H1("Verfügbare Shows zu Ihrem Besuchsdatum:");

            // Display available shows for the selected visit date
            List<Show> availableShows = showService.findShowsByDate(besuchsdatum);

            showGrid.setItems(availableShows);
            showGrid.setColumns("showId", "showName", "showart", "showDatum", "showZeit", "kapazitaet");


            // Create a layout for the button
            HorizontalLayout buttonLayout = new HorizontalLayout();
            buttonLayout.setWidthFull();

            bookShowButton.addClickListener(event1 -> {
                Show selectedShow = showGrid.asSingleSelect().getValue();
                if (selectedShow != null) {
                    Besucher currentBesucher = besucherGrid.asSingleSelect().getValue();
                    if (currentBesucher != null) {
                        try {
                            currentBesucher.getShows().add(selectedShow);
                            besucherService.updateBesucher(currentBesucher);
                            notification = Notification.show("Show erfolgreich gebucht!", 3000, Notification.Position.MIDDLE);

                            // Refresh the BesucherGrid
                            List<Besucher> updatedTickets = besucherService.findByBesuchsdatum(besuchsdatum);
                            besucherGrid.setItems(updatedTickets);
                        } catch (Exception e) {
                            notification = Notification.show("Fehler beim Buchen der Show.", 3000, Notification.Position.MIDDLE);
                        }
                    } else {
                        notification = Notification.show("Bitte wählen Sie einen Besucher aus.", 3000, Notification.Position.MIDDLE);
                    }
                } else {
                    notification = Notification.show("Bitte wählen Sie eine Show aus.", 3000, Notification.Position.MIDDLE);
                }
            });


            // Add the button to the layout and align it to the right
            buttonLayout.add(bookShowButton);
            buttonLayout.setJustifyContentMode(JustifyContentMode.END);

            // Add the layout containing the button to the view
            add(besucherGrid, showsHeader, showGrid, buttonLayout);

        } else {
            Notification.show("Fehler: Das Besuchsdatum ist ungültig.", 3000, Notification.Position.MIDDLE);
        }

        besucherGrid.addClassName("compact-grid");
        showGrid.addClassName("compact-grid");
    }


    // Getter for 'besucherGrid'
    public Grid<Besucher> getBesucherGrid() {
        return besucherGrid;
    }

    // Getter for 'besuchsdatum'
    public LocalDate getBesuchsdatum() {
        return besuchsdatum;
    }

    // Getter for 'bookShowButton'
    public Button getBookShowButton() {
        return bookShowButton;
    }

    // Getter for 'notification'
    public Notification getNotification() {
        return notification;
    }

    // Getter for 'showGrid'
    public Grid<Show> getShowGrid() {
        return showGrid;
    }

}
