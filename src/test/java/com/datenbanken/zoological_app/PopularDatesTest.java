package com.datenbanken.zoological_app;

import com.datenbanken.zoological_app.repository.BesucherRepository;
import com.datenbanken.zoological_app.service.BesucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PopularDatesTest {

    @Mock
    private BesucherRepository besucherRepository;

    @InjectMocks
    private BesucherService besucherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllPopularDates() {
        // Prepare mock data
        LocalDate date1 = LocalDate.of(2025, 1, 5);
        LocalDate date2 = LocalDate.of(2025, 1, 6);
        LocalDate date3 = LocalDate.of(2025, 1, 7);

        // Mock the behavior of findAllPopularDates() -> mock data must be in descending order of visitor count
        when(besucherRepository.findAllPopularDates()).thenReturn(Arrays.asList(
                new Object[] {date1, 15L},
                new Object[] {date2, 10L},
                new Object[] {date3, 5L}
        ));

        // Call the service method
        String result = besucherService.findAllPopularDates();

        // Verify the result
        String expected = date1 + ", Besucher: " + 15L;
        assertEquals(expected, result);
    }


    @Test
    void testFindAllPopularDatesWhenNoPopularDates() {
        // Mock the behavior of findAllPopularDates() when no data is available
        when(besucherRepository.findAllPopularDates()).thenReturn(Arrays.asList());

        // Call the service method
        String result = besucherService.findAllPopularDates();

        // Verify the result
        assertEquals("", result);
    }
}
