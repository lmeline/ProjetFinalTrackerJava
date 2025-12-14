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
    @DisplayName("Vérification chargement initial des instruments")
    void testChargementInitial() {
        // Act
        Map<String, Instrument> instruments = instrumentService.getAllInstruments();

        // Assert
        assertNotNull(instruments, "La map d'instruments ne devrait pas être null");
        assertFalse(instruments.isEmpty(), "La map ne devrait pas être vide");
        assertTrue(instruments.containsKey("piano"), "La map devrait contenir le piano");
    }

    @Test
    @DisplayName("Récupération d'un instrument par son nom")
    void testGetInstrument_Nominal() {
        // Act
        Instrument resultat = instrumentService.getInstrument("piano");

        // Assert
        assertNotNull(resultat);
        assertEquals("piano", resultat.getNom());
        assertEquals(Hauteur.C4, resultat.getHauteurDuSample());
    }

    @Test
    @DisplayName("Vérification instrument Null")
    void testGetInstrument_Inconnu() {
        // Act
        Instrument resultat = instrumentService.getInstrument("guitare_imaginare");

        // Assert
        assertNull(resultat);
    }

    @Test
    @DisplayName("Vérification recupération Intrument courant")
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