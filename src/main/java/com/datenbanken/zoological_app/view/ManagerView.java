package com.datenbanken.zoological_app.view;

import com.datenbanken.zoological_app.entity.*;
import com.datenbanken.zoological_app.service.*;
import com.datenbanken.zoological_app.view.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(value = "manager", layout = MainLayout.class)
public class ManagerView extends VerticalLayout implements BeforeEnterObserver {

    private final MitarbeiterService mitarbeiterService;
    private final GehegeService gehegeService;
    private final TierService tierService;
    private final ShowService showService;
    private final BesucherService besucherService;
    private final FuetterungseinheitService fuetterungseinheitService;
    private final FuetterungsplanService fuetterungsplanService;

    private Tabs tabs;
    private Tab mitarbeiterTab;
    private Tab gehegeTab;
    private Tab tiereTab;
    private Tab showsTab;
    private Tab besucherTab;
    private Tab fuetterungseinheitTab;
    private Tab fuetterungsplanTab;

    @Autowired
    public ManagerView(
            MitarbeiterService mitarbeiterService,
            GehegeService gehegeService,
            TierService tierService,
            ShowService showService,
            BesucherService besucherService,
            FuetterungseinheitService fuetterungseinheitService,
            FuetterungsplanService fuetterungsplanService
    ) {
        this.mitarbeiterService = mitarbeiterService;
        this.gehegeService = gehegeService;
        this.tierService = tierService;
        this.showService = showService;
        this.besucherService = besucherService;
        this.fuetterungseinheitService = fuetterungseinheitService;
        this.fuetterungsplanService = fuetterungsplanService;

        // Initialize the tabs
        tabs = new Tabs();
        mitarbeiterTab = new Tab("Mitarbeiter");
        gehegeTab = new Tab("Gehege");
        tiereTab = new Tab("Tiere");
        showsTab = new Tab("Shows");
        besucherTab = new Tab("Besucher");
        fuetterungseinheitTab = new Tab("Fütterungseinheiten");
        fuetterungsplanTab = new Tab("Fütterungspläne");

        tabs.add(mitarbeiterTab, gehegeTab, tiereTab, fuetterungseinheitTab, fuetterungsplanTab, showsTab, besucherTab);
        tabs.setSelectedTab(mitarbeiterTab); // Default to "Mitarbeiter"
        tabs.setWidthFull();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        add(new H3("Manager"));
        add(tabs);

        // Container for tab content
        VerticalLayout tabContent = new VerticalLayout();
        add(tabContent);

        // Initially load the content for the selected tab
        loadMitarbeiterContent(tabContent);

        // Tabs selection listener
        tabs.addSelectedChangeListener(e -> {
            tabContent.removeAll(); // Clear previous content
            if (tabs.getSelectedTab().equals(mitarbeiterTab)) {
                loadMitarbeiterContent(tabContent);
            } else if (tabs.getSelectedTab().equals(gehegeTab)) {
                loadGehegeContent(tabContent);
            } else if (tabs.getSelectedTab().equals(tiereTab)) {
                loadTiereContent(tabContent);
            } else if (tabs.getSelectedTab().equals(fuetterungseinheitTab)) {
                loadFuetterungseinheitContent(tabContent);
            } else if (tabs.getSelectedTab().equals(fuetterungsplanTab)) {
                loadFuetterungsplaeneContent(tabContent);
            } else if (tabs.getSelectedTab().equals(showsTab)) {
                loadShowsContent(tabContent);
            } else if (tabs.getSelectedTab().equals(besucherTab)) {
                loadBesucherContent(tabContent);
            }
        });

    }


    private void loadMitarbeiterContent(VerticalLayout tabContent) {

        // Grid Setup
        GridCrud<Mitarbeiter> mitarbeiterGrid = new GridCrud<>(Mitarbeiter.class);
        mitarbeiterGrid.getGrid().setWidthFull();
        mitarbeiterGrid.getCrudFormFactory().setVisibleProperties("vorname", "nachname", "position", "verantwortungsbereich", "arbeitsschicht", "gehege");
        mitarbeiterGrid.getCrudFormFactory().setFieldProvider("mitarbeiterId", field -> null);
        mitarbeiterGrid.getCrudFormFactory().setFieldProvider("gehege", field -> {
            // MultiSelectComboBox for Gehege
            MultiSelectComboBox<Gehege> multiSelectComboBox = new MultiSelectComboBox<>("Gehege");
            multiSelectComboBox.setItems(gehegeService.findAll()); // load Gehege-data
            multiSelectComboBox.setItemLabelGenerator(gehege ->  gehege.getFaunabereich() + ", Gehege-ID: " + gehege.getId());

            Binder<Mitarbeiter> binder = new Binder<>(Mitarbeiter.class);

            binder.forField(multiSelectComboBox)
                    .withValidator(
                            set -> set != null && !set.isEmpty(),
                            "Das Set darf nicht leer sein"
                    )
                    .bind(
                            Mitarbeiter::getGehege,
                            Mitarbeiter::setGehege
                    );

            return multiSelectComboBox;
        });


        // CRUD operations
        mitarbeiterGrid.setFindAllOperation(mitarbeiterService::findAll);
        mitarbeiterGrid.setAddOperation(mitarbeiterService::addMitarbeiter);
        mitarbeiterGrid.setUpdateOperation(mitarbeiterService::updateMitarbeiter);
        mitarbeiterGrid.setDeleteOperation(mitarbeiterService::deleteMitarbeiter);

        mitarbeiterGrid.getGrid().removeAllColumns();
        mitarbeiterGrid.getGrid().addColumn(Mitarbeiter::getId).setHeader("Mitarbeiter ID").setAutoWidth(true).setSortable(true);
        mitarbeiterGrid.getGrid().addColumn(Mitarbeiter::getVorname).setHeader("Vorname").setAutoWidth(true);
        mitarbeiterGrid.getGrid().addColumn(Mitarbeiter::getNachname).setHeader("Nachname").setAutoWidth(true);
        mitarbeiterGrid.getGrid().addColumn(Mitarbeiter::getPosition).setHeader("Position").setAutoWidth(true).setSortable(true);
        mitarbeiterGrid.getGrid().addColumn(Mitarbeiter::getVerantwortungsbereich).setHeader("Verantwortungsbereich").setAutoWidth(true).setSortable(true);
        mitarbeiterGrid.getGrid().addColumn(Mitarbeiter::getArbeitsschicht).setHeader("Arbeitsschicht").setAutoWidth(true).setSortable(true);
        mitarbeiterGrid.getGrid().addColumn(Mitarbeiter::getFuetterungseinheiten).setHeader("Fütterungseinheiten").setAutoWidth(true).setSortable(true);
        mitarbeiterGrid.getGrid().addColumn(Mitarbeiter::getGehege).setHeader("Gehege").setAutoWidth(true).setSortable(true);

        mitarbeiterGrid.getGrid().addComponentColumn(mitarbeiter -> {
            Button tagesplanButton = new Button("Tagesplan");
            tagesplanButton.addClickListener(event -> {
                erzeugeTagesplanDialog(mitarbeiter.getId());
            });
            return tagesplanButton;
        });

        tabContent.add(mitarbeiterGrid);
    }


    private void loadGehegeContent(VerticalLayout tabContent) {
        var gehegeList = gehegeService.findAll();

        // Grid Setup
        GridCrud<Gehege> gehegeGrid = new GridCrud<>(Gehege.class);

        // Set visible properties for the form
        gehegeGrid.getCrudFormFactory().setVisibleProperties(
                "faunabereich",
                "saeuberungsdatum",
                "saeuberungszeit",
                "groesse",
                "groesseneinheit"
        );

        // Add a field provider for 'saeuberungszeit'
        gehegeGrid.getCrudFormFactory().setFieldProvider("saeuberungszeit",
                field -> new TimePicker("Säuberungszeit")
        );

        // Prevent editing the 'gehegeId' field
        gehegeGrid.getCrudFormFactory().setFieldProvider("gehegeId", field -> null);

        // CRUD operations
        gehegeGrid.setFindAllOperation(gehegeService::findAll);
        gehegeGrid.setAddOperation(gehegeService::addGehege);
        gehegeGrid.setUpdateOperation(gehegeService::updateGehege);
        gehegeGrid.setDeleteOperation(gehegeService::deleteGehege);

        // Customize Grid columns
        gehegeGrid.getGrid().removeAllColumns();
        gehegeGrid.getGrid().addColumn(Gehege::getId).setHeader("Gehege ID").setAutoWidth(true).setSortable(true);
        gehegeGrid.getGrid().addColumn(Gehege::getFaunabereich).setHeader("Faunabereich").setAutoWidth(true).setSortable(true);
        gehegeGrid.getGrid().addColumn(Gehege::getTiere).setHeader("Tiere").setAutoWidth(true).setSortable(true);
        gehegeGrid.getGrid().addColumn(Gehege::getMitarbeiter).setHeader("Mitarbeiter").setAutoWidth(true).setSortable(true);
        gehegeGrid.getGrid().addColumn(Gehege::getGroesse).setHeader("Größe").setAutoWidth(true).setSortable(true);
        gehegeGrid.getGrid().addColumn(Gehege::getGroesseneinheit).setHeader("Größeneinheit").setAutoWidth(true).setSortable(true);
        gehegeGrid.getGrid().addColumn(Gehege::getSaeuberungsdatum).setHeader("Säuberungsdatum").setAutoWidth(true).setSortable(true);
        gehegeGrid.getGrid().addColumn(Gehege::getSaeuberungszeit).setHeader("Säuberungszeit").setAutoWidth(true).setSortable(true);

        // Add the CRUD grid to the layout
        tabContent.add(gehegeGrid);
    }


    private void loadTiereContent(VerticalLayout tabContent) {
        // Grid Setup
        GridCrud<Tier> tierGrid = new GridCrud<>(Tier.class);

        tierGrid.getCrudFormFactory().setVisibleProperties("name", "tierart", "herkunftsland", "geschlecht", "geburtsjahr", "aufnahmedatum", "fuetterungsplan", "gehege", "fuetterungseinheiten");
        tierGrid.getCrudFormFactory().setFieldProvider("tier_id", field -> null);
        tierGrid.getCrudFormFactory().setFieldProvider("gehege", field -> {
            ComboBox<Gehege> comboBox = new ComboBox<>("Gehege");
            comboBox.setItems(gehegeService.findAll());
            comboBox.setItemLabelGenerator(Gehege::getFaunabereich); // display name of Gehege
            return comboBox;
        });

        tierGrid.getCrudFormFactory().setFieldProvider("fuetterungsplan", field -> {
            ComboBox<Fuetterungsplan> comboBox = new ComboBox<>("Fütterungsplan");
            comboBox.setItems(fuetterungsplanService.findAll());
            comboBox.setItemLabelGenerator(fuetterungsplan -> String.valueOf(fuetterungsplan.getFuetterungsplanId())); // display name of Fütterungsplan
            return comboBox;
        });


        // CRUD operations
        tierGrid.setFindAllOperation(tierService::findAll);
        tierGrid.setAddOperation(tierService::save);
        tierGrid.setUpdateOperation(tierService::update);
        tierGrid.setDeleteOperation(tierService::delete);

        // Configure grid columns
        tierGrid.getGrid().removeAllColumns();
        tierGrid.getGrid().addColumn(Tier::getTierId).setHeader("Tier-ID").setAutoWidth(true).setSortable(true);
        tierGrid.getGrid().addColumn(Tier::getTierart).setHeader("Tierart").setAutoWidth(true).setSortable(true);
        tierGrid.getGrid().addColumn(Tier::getName).setHeader("Name").setAutoWidth(true).setSortable(true);
        tierGrid.getGrid().addColumn(Tier::getGeschlecht).setHeader("Geschlecht").setAutoWidth(true);
        tierGrid.getGrid().addColumn(Tier::getGeburtsjahr).setHeader("Geburtsdatum").setAutoWidth(true);
        tierGrid.getGrid().addColumn(Tier::getGehege).setHeader("Gehege").setAutoWidth(true).setSortable(true);
        tierGrid.getGrid().addColumn(Tier::getFuetterungsplan).setHeader("Fütterungsplan").setAutoWidth(true).setSortable(true);
        tierGrid.getGrid().addColumn(Tier::getHerkunftsland).setHeader("Herkunftsland").setAutoWidth(true);
        tierGrid.getGrid().addColumn(Tier::getAufnahmedatum).setHeader("Aufnahmedatum").setAutoWidth(true);

        tabContent.add(tierGrid);
    }


    private void loadShowsContent(VerticalLayout tabContent) {
        // GridCrud Setup
        GridCrud<Show> showGrid = new GridCrud<>(Show.class);

        // Configure visible properties in the CRUD form
        showGrid.getCrudFormFactory().setVisibleProperties(
                "showName",
                "showart",
                "showDatum",
                "showZeit",
                "kapazitaet",
                "fuetterungseinheit"
        );

        // Prevent editing of the 'showId' field
        showGrid.getCrudFormFactory().setFieldProvider("showId", field -> null);

        // Add a TimePicker for 'showZeit'
        showGrid.getCrudFormFactory().setFieldProvider("showZeit", field -> {
            TimePicker timePicker = new TimePicker("Show-Zeit");
            return timePicker;
        });

        // Add a ComboBox for 'fuetterungseinheit'
        showGrid.getCrudFormFactory().setFieldProvider("fuetterungseinheit", field -> {
            ComboBox<Fuetterungseinheit> comboBox = new ComboBox<>("Fütterungseinheit");
            comboBox.setItems(fuetterungseinheitService.findAll());
            comboBox.setItemLabelGenerator(fuetterungseinheit ->
                    fuetterungseinheit.getFuetterungseinheitId().toString()
            );
            return comboBox;
        });

        // CRUD operations
        showGrid.setFindAllOperation(showService::findAll);
        showGrid.setAddOperation(showService::addShow);
        showGrid.setUpdateOperation(showService::updateShow);
        showGrid.setDeleteOperation(showService::deleteShow);

        // Configure the grid columns
        showGrid.getGrid().removeAllColumns();
        showGrid.getGrid().addColumn(Show::getShowId).setHeader("Show-ID").setAutoWidth(true).setSortable(true);
        showGrid.getGrid().addColumn(Show::getShowName).setHeader("Show-Name").setAutoWidth(true).setSortable(true);
        showGrid.getGrid().addColumn(Show::getShowart).setHeader("Show-Art").setAutoWidth(true).setSortable(true);
        showGrid.getGrid().addColumn(Show::getShowDatum).setHeader("Show-Datum").setAutoWidth(true).setSortable(true);
        showGrid.getGrid().addColumn(Show::getShowZeit).setHeader("Show-Zeit").setAutoWidth(true).setSortable(true);
        showGrid.getGrid().addColumn(Show::getKapazitaet).setHeader("Kapazität").setAutoWidth(true).setSortable(true);
        showGrid.getGrid().addColumn(Show::getAuslastung).setHeader("Auslastung").setAutoWidth(true).setSortable(true);

        // Add the configured grid to the layout
        tabContent.add(showGrid);
    }


    private void loadBesucherContent(VerticalLayout tabContent) {
        var besucherList = besucherService.findAll();

        // Grid Setup
        Grid<Besucher> besucherGrid = new Grid<>(Besucher.class);
        besucherGrid.setItems(besucherList);

        // Remove the default columns
        besucherGrid.removeAllColumns();

        // Add the necessary columns
        besucherGrid.addColumn(Besucher::getBesucherId).setHeader("Besucher ID").setAutoWidth(true).setSortable(true);
        besucherGrid.addColumn(Besucher::getBesuchsdatum).setHeader("Besuchsdatum").setAutoWidth(true).setSortable(true);
        besucherGrid.addColumn(besucher -> {
            // Convert shows to a string or display as needed
            return String.join(", ", besucher.getShows().stream().map(Show::getShowName).toList());
        }).setHeader("Shows").setAutoWidth(true);

        // Button for popular dates
        Button popularDatesButton = new Button("Beliebtester Besuchstag", event -> {
            String popularDate = besucherService.findAllPopularDates();
            if (!popularDate.isEmpty()) {
                Notification.show("Beliebtester Besuchstag: " + popularDate, 5000, Notification.Position.MIDDLE);
            } else {
                Notification.show("Kein beliebtester Besuchstag gefunden.", 3000, Notification.Position.MIDDLE);
            }
        });

        // Add the grid and button to the layout
        tabContent.add(besucherGrid, popularDatesButton);
    }


    private void loadFuetterungseinheitContent(VerticalLayout tabContent) {

        // Grid Setup
        GridCrud<Fuetterungseinheit> fuetterungseinheitGrid = new GridCrud<>(Fuetterungseinheit.class);

        // Configure visible properties in the CRUD form
        fuetterungseinheitGrid.getCrudFormFactory().setVisibleProperties(
                "fuetterungszeit",
                "futterart",
                "futtermenge",
                "fuetterungsplan",
                "mitarbeiter"
        );

        // Prevent editing of 'fuetterungseinheitId' field
        fuetterungseinheitGrid.getCrudFormFactory().setFieldProvider("fuetterungseinheitId", field -> null);

        // Add a TimePicker for 'fuetterungszeit'
        fuetterungseinheitGrid.getCrudFormFactory().setFieldProvider("fuetterungszeit", field -> {
            TimePicker timePicker = new TimePicker("Fütterungszeit");
            return timePicker;
        });

        // Add a ComboBox for 'fuetterungsplan'
        fuetterungseinheitGrid.getCrudFormFactory().setFieldProvider("fuetterungsplan", field -> {
            ComboBox<Fuetterungsplan> comboBox = new ComboBox<>("Fütterungsplan");
            comboBox.setItems(fuetterungsplanService.findAll()); // Load all feeding plans
            comboBox.setItemLabelGenerator(fuetterungsplan -> "Plan-ID: " + fuetterungsplan.getFuetterungsplanId());
            return comboBox;
        });

        // Add a ComboBox for 'mitarbeiter'
        fuetterungseinheitGrid.getCrudFormFactory().setFieldProvider("mitarbeiter", field -> {
            ComboBox<Mitarbeiter> comboBox = new ComboBox<>("Mitarbeiter");
            comboBox.setItems(mitarbeiterService.findAll()); // Load all employees
            comboBox.setItemLabelGenerator(mitarbeiter -> mitarbeiter.getVorname()+ ", Mitarbeiter-ID: " + mitarbeiter.getId());
            return comboBox;
        });

        // CRUD operations
        fuetterungseinheitGrid.setFindAllOperation(fuetterungseinheitService::findAll);
        fuetterungseinheitGrid.setAddOperation(fuetterungseinheitService::addFuetterungseinheit);
        fuetterungseinheitGrid.setUpdateOperation(fuetterungseinheitService::updateFuetterungseinheit);
        fuetterungseinheitGrid.setDeleteOperation(fuetterungseinheitService::deleteFuetterungseinheit);

        fuetterungseinheitGrid.getGrid().removeAllColumns();
        fuetterungseinheitGrid.getGrid().addColumn(Fuetterungseinheit::getFuetterungseinheitId).setHeader("Fütterungseinheit ID").setAutoWidth(true).setSortable(true);
        fuetterungseinheitGrid.getGrid().addColumn(Fuetterungseinheit::getFuetterungsplan).setHeader("Fütterungsplan").setAutoWidth(true);
        fuetterungseinheitGrid.getGrid().addColumn(Fuetterungseinheit::getFuetterungszeit).setHeader("Fütterungszeit").setAutoWidth(true).setSortable(true);
        fuetterungseinheitGrid.getGrid().addColumn(Fuetterungseinheit::getFutterart).setHeader("Futterart").setAutoWidth(true);
        fuetterungseinheitGrid.getGrid().addColumn(Fuetterungseinheit::getFuttermenge).setHeader("Futtermenge").setAutoWidth(true);
        fuetterungseinheitGrid.getGrid().addColumn(Fuetterungseinheit::getMitarbeiter).setHeader("Mitarbeiter").setAutoWidth(true);

        // Button to show 'gesamtbedarf'
        Button gesamtbedarfButton = new Button("Gesamtbedarf anzeigen", click -> {
            List<Fuetterungseinheit> fuetterungseinheiten = fuetterungseinheitService.findAll();
            Map<String, Double> gesamtbedarf = new HashMap<>();

            // computation of Gesamtbedarf
            fuetterungseinheiten.forEach(einheit -> {
                gesamtbedarf.put(
                        einheit.getFutterart(),
                        gesamtbedarf.getOrDefault(einheit.getFutterart(), 0.0) + einheit.getFuttermenge()
                );
            });

            // show the 'gesamtbedarf' as a notification
            StringBuilder bedarfText = new StringBuilder("Gesamtbedarf:\n");
            gesamtbedarf.forEach((futterart, menge) ->
                    bedarfText.append(futterart).append(": ").append(menge).append("\n")
            );

            Notification.show(bedarfText.toString(), 5000, Notification.Position.MIDDLE);
        });

        tabContent.add(fuetterungseinheitGrid, gesamtbedarfButton);
    }


    private void loadFuetterungsplaeneContent(VerticalLayout tabContent) {
        var fuetterungsplaeneList = fuetterungsplanService.findAll();

        // Grid Setup
        GridCrud<Fuetterungsplan> fuetterungsplanGrid = new GridCrud<>(Fuetterungsplan.class);
        fuetterungsplanGrid.getCrudFormFactory().setVisibleProperties();
        fuetterungsplanGrid.getCrudFormFactory().setFieldProvider("showId", field -> null);

        // CRUD operations
        fuetterungsplanGrid.setFindAllOperation(fuetterungsplanService::findAll);
        fuetterungsplanGrid.setAddOperation(fuetterungsplanService::addFuetterungsplan);
        fuetterungsplanGrid.setUpdateOperation(fuetterungsplanService::updateFuetterungsplan);
        fuetterungsplanGrid.setDeleteOperation(fuetterungsplanService::deleteFuetterungsplan);

        // Configure the Grid
        fuetterungsplanGrid.getGrid().removeAllColumns();
        fuetterungsplanGrid.getGrid().addColumn(Fuetterungsplan::getFuetterungsplanId).setHeader("Fütterungsplan-ID").setAutoWidth(true).setSortable(true);
        fuetterungsplanGrid.getGrid().addColumn(Fuetterungsplan::getFuetterungseinheiten).setHeader("Fütterungseinheiten").setAutoWidth(true).setSortable(true);

        tabContent.add(fuetterungsplanGrid);
    }


    public void erzeugeTagesplanDialog(Integer mitarbeiterId) {
        Dialog tagesplanDialog = new Dialog();
        tagesplanDialog.setWidth("600px");
        tagesplanDialog.setHeight("400px");

        // load 'Mitarbeiter' data
        Mitarbeiter mitarbeiter = mitarbeiterService.findMitarbeiterById(mitarbeiterId);

        List<Fuetterungseinheit> tagesplan = mitarbeiterService.erzeugeTagesplan(mitarbeiterId);


        // Grid for Fütterungseinheiten
        Grid<Fuetterungseinheit> fuetterungseinheitGrid = new Grid<>(Fuetterungseinheit.class);
        fuetterungseinheitGrid.setItems(tagesplan);
        fuetterungseinheitGrid.setColumns("fuetterungseinheitId", "fuetterungszeit");

        // add dialog
        tagesplanDialog.add(new H3("Tagesplan für " + mitarbeiter.getVorname() + ", ID: " + mitarbeiterId), fuetterungseinheitGrid);
        tagesplanDialog.open();
    }


}
