package fr.esgi.tracker.service;

import fr.esgi.tracker.business.Hauteur;
import fr.esgi.tracker.business.Instrument;
import fr.esgi.tracker.services.impl.InstrumentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class InstrumentServiceImplTest {

    @InjectMocks
    private InstrumentServiceImpl instrumentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Devrait charger les instruments par défaut à l'initialisation")
    void testChargementInitial() {
        // Act
        Map<String, Instrument> instruments = instrumentService.getAllInstruments();

        // Assert
        assertNotNull(instruments, "La map d'instruments ne devrait pas être null");
        assertFalse(instruments.isEmpty(), "La map ne devrait pas être vide");
        assertTrue(instruments.containsKey("piano"), "La map devrait contenir le piano");
    }

    @Test
    @DisplayName("Devrait récupérer un instrument spécifique par son nom")
    void testGetInstrument_Nominal() {
        // Act
        Instrument resultat = instrumentService.getInstrument("piano");

        // Assert
        assertNotNull(resultat);
        assertEquals("piano", resultat.getNom());
        assertEquals(Hauteur.C4, resultat.getHauteurDuSample());
    }

    @Test
    @DisplayName("Devrait retourner null pour un instrument inconnu")
    void testGetInstrument_Inconnu() {
        // Act
        Instrument resultat = instrumentService.getInstrument("guitare_imaginare");

        // Assert
        assertNull(resultat);
    }

    @Test
    @DisplayName("Devrait modifier et récupérer l'instrument courant")
    void testInstrumentCourant() {
        // Arrange
        Instrument piano = instrumentService.getInstrument("piano");

        // Act
        instrumentService.setInstrumentCourant(piano);
        Instrument courant = instrumentService.getInstrumentCourant();

        // Assert
        assertNotNull(courant);
        assertEquals(piano, courant);
        assertEquals("piano", courant.getNom());
    }
}