package com.datenbanken.zoological_app;

import com.datenbanken.zoological_app.entity.Fuetterungseinheit;
import com.datenbanken.zoological_app.entity.Mitarbeiter;
import com.datenbanken.zoological_app.service.MitarbeiterService;
import com.datenbanken.zoological_app.repository.FuetterungseinheitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MitarbeiterTagesplanTest {


    @Mock
    private FuetterungseinheitRepository fuetterungseinheitRepository;

    @InjectMocks
    private MitarbeiterService mitarbeiterService;

    private Mitarbeiter mitarbeiter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock Mitarbeiter data
        mitarbeiter = new Mitarbeiter();
        mitarbeiter.setId(1);
        mitarbeiter.setVorname("Max");
        mitarbeiter.setNachname("Mustermann");

        // Create some feeding units
        Fuetterungseinheit fuetterungseinheit1 = new Fuetterungseinheit();
        fuetterungseinheit1.setFuetterungseinheitId(1L);
        fuetterungseinheit1.setFuetterungszeit(LocalTime.parse("08:00"));

        Fuetterungseinheit fuetterungseinheit2 = new Fuetterungseinheit();
        fuetterungseinheit2.setFuetterungseinheitId(2L);
        fuetterungseinheit2.setFuetterungszeit(LocalTime.parse("12:00"));

        // Mock the repository call for Fuetterungseinheit
        when(fuetterungseinheitRepository.findByMitarbeiterId(1)).thenReturn(Arrays.asList(fuetterungseinheit1, fuetterungseinheit2));
    }

    @Test
    public void testErzeugeTagesplanDialog() {
        // Arrange
        Integer mitarbeiterId = 1;

        // Act
        List<Fuetterungseinheit> tagesplan = mitarbeiterService.erzeugeTagesplan(mitarbeiterId);

        // Assert: Check that the right Fuetterungseinheiten were fetched
        assertNotNull(tagesplan);
        assertEquals(2, tagesplan.size());

        // Assert that the Fuetterungszeit values are of type LocalTime and match the expected values
        assertEquals(LocalTime.parse("08:00"), tagesplan.get(0).getFuetterungszeit());
        assertEquals(LocalTime.parse("12:00"), tagesplan.get(1).getFuetterungszeit());

        // Verify that the correct repository method was called
        verify(fuetterungseinheitRepository, times(1)).findByMitarbeiterId(mitarbeiterId);
    }
}
