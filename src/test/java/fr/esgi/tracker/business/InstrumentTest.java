package fr.esgi.tracker.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstrumentTest {

    @Test
    @DisplayName("Test ID Unique")
    void testIdUnique() {
        // Act
        Instrument i1 = new Instrument("Instru1", "path1", Hauteur.A2);
        Instrument i2 = new Instrument("Instru2", "path2", Hauteur.A2);

        // Assert
        assertNotEquals(i1.getId(), i2.getId(), "Les IDs devraient être différents");
    }

    @Test
    @DisplayName("Test Setters")
    void testSetters() {
        // Arrange
        Instrument instrument = new Instrument("Piano", "path", Hauteur.C4);

        // Act
        instrument.setNom("Guitare");
        instrument.setCheminFichier("new/path.wav");
        instrument.sethauteurDuSample(Hauteur.A2); // Correction ici

        // Assert
        assertEquals("Guitare", instrument.getNom());
        assertEquals("new/path.wav", instrument.getCheminFichier());
        assertEquals(Hauteur.A2, instrument.getHauteurDuSample());
    }

}