package com.datenbanken.zoological_app;

import com.datenbanken.zoological_app.entity.Besucher;
import com.datenbanken.zoological_app.entity.Show;
import com.datenbanken.zoological_app.service.BesucherService;
import com.datenbanken.zoological_app.service.ShowService;
import com.datenbanken.zoological_app.view.KaufbestaetigungView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.component.UI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith({MockitoExtension.class, SpringExtension.class})
class KaufbestaetigungViewTest {

    @Mock
    private BesucherService besucherService;

    @Mock
    private ShowService showService;

    @InjectMocks
    private KaufbestaetigungView kaufbestaetigungView;

    private final LocalDate testDatum = LocalDate.of(2025, 1, 7);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock Vaadin UI environment
        UI testUI = new UI();
        UI.setCurrent(testUI);

        kaufbestaetigungView = new KaufbestaetigungView(besucherService, showService);
    }

    @Test
    void testBesuchsdatumHandling() {
        // Arrange
        RouteParameters routeParameters = new RouteParameters("besuchsdatum", testDatum.toString());
        BeforeEnterEvent event = mock(BeforeEnterEvent.class);
        when(event.getRouteParameters()).thenReturn(routeParameters);

        // Act
        kaufbestaetigungView.beforeEnter(event);

        // Assert
        assertEquals(testDatum, kaufbestaetigungView.getBesuchsdatum());
    }

    @Test
    void testDisplayGekaufteTickets() {
        // Arrange
        RouteParameters routeParameters = new RouteParameters("besuchsdatum", testDatum.toString());
        BeforeEnterEvent event = mock(BeforeEnterEvent.class);
        when(event.getRouteParameters()).thenReturn(routeParameters);

        List<Besucher> mockBesucherList = mockBesucherList();
        when(besucherService.findByBesuchsdatum(testDatum)).thenReturn(mockBesucherList);

        // Act
        kaufbestaetigungView.beforeEnter(event);
        Grid<Besucher> besucherGrid = kaufbestaetigungView.getBesucherGrid();

        // Assert
        assertNotNull(besucherGrid);
        long count = besucherGrid.getDataProvider().fetch(new com.vaadin.flow.data.provider.Query<>()).count();
        assertEquals(2, count);
    }

    @Test
    void testShowBuchenButtonInteraction() {
        // Arrange
        RouteParameters routeParameters = new RouteParameters("besuchsdatum", testDatum.toString());
        BeforeEnterEvent event = mock(BeforeEnterEvent.class);
        when(event.getRouteParameters()).thenReturn(routeParameters);

        List<Besucher> mockBesucherList = mockBesucherList();
        when(besucherService.findByBesuchsdatum(testDatum)).thenReturn(mockBesucherList);

        List<Show> mockShowList = mockShowList();
        when(showService.findShowsByDate(testDatum)).thenReturn(mockShowList);

        // Mocking static methods like Notification.show()
        try (MockedStatic<Notification> notificationMock = Mockito.mockStatic(Notification.class)) {
            // Act
            kaufbestaetigungView.beforeEnter(event); // Initialize components
            Button bookShowButton = kaufbestaetigungView.getBookShowButton();

            Grid<Besucher> besucherGrid = kaufbestaetigungView.getBesucherGrid();
            Grid<Show> showGrid = kaufbestaetigungView.getShowGrid();

            // Select a Besucher and Show
            besucherGrid.select(mockBesucherList.get(0));
            showGrid.select(mockShowList.get(0));

            // Click the button to trigger the notification
            bookShowButton.click();

            // Assert: Verify the update of the Besucher
            verify(besucherService, times(1)).updateBesucher(any(Besucher.class));

            // Fix for verifying the static method Notification.show()
            notificationMock.verify(() -> Notification.show(eq("Show erfolgreich gebucht!"), eq(3000), eq(Notification.Position.MIDDLE)), times(1));
        }
    }


    private List<Besucher> mockBesucherList() {
        Show show1 = new Show(1L, "Show 1", testDatum, "10:00", "Pinguinfütterung", 50);
        Show show2 = new Show(2L, "Show 2", testDatum, "12:00", "Pinguinfütterung", 50);

        Besucher besucher1 = new Besucher();
        besucher1.setBesucherId(1L);
        besucher1.setBesuchsdatum(testDatum);
        besucher1.setShows(new ArrayList<>(List.of(show1)));

        Besucher besucher2 = new Besucher();
        besucher2.setBesucherId(2L);
        besucher2.setBesuchsdatum(testDatum);
        besucher2.setShows(new ArrayList<>(List.of(show2)));

        return new ArrayList<>(List.of(besucher1, besucher2));
    }

    private List<Show> mockShowList() {
        Show show1 = new Show(1L, "Show 1", testDatum, "10:00", "Pinguinfütterung", 50);
        Show show2 = new Show(2L, "Show 2", testDatum, "12:00", "Pinguinfütterung", 50);
        return new ArrayList<>(List.of(show1, show2));
    }

}

