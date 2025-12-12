package fr.esgi.tracker.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {
    private final Hauteur HAUTEUR_TEST = Hauteur.A2;
    private final Instrument INSTRUMENT_TEST = new Instrument("Piano", "path", Hauteur.A2);

    @Test
    @DisplayName("Test Setters")
    void testSetters() {
        // Arrange
        Note note = new Note(HAUTEUR_TEST, INSTRUMENT_TEST, 1.0f);
        Hauteur nouvelleHauteur = Hauteur.C4; // Supposons que C4 existe
        Instrument nouvelInstru = new Instrument("Flute", "path", Hauteur.C4);
        float nouveauVolume = 0.8f;

        // Act
        note.setHauteur(nouvelleHauteur);
        note.setInstrument(nouvelInstru);
        note.setVolume(nouveauVolume);

        // Assert
        assertEquals(nouvelleHauteur, note.getHauteur());
        assertEquals(nouvelInstru, note.getInstrument());
        assertEquals(nouveauVolume, note.getVolume());
    }

    @Test
    @DisplayName("Test Equals et HashCode")
    void testEquals_Identique() {
        // Arrange
        Note n1 = new Note(HAUTEUR_TEST, INSTRUMENT_TEST, 1.0f);
        n1.setVolume(1.0f);

        Note n2 = new Note(HAUTEUR_TEST, INSTRUMENT_TEST, 1.0f);
        n2.setVolume(1.0f);

        // Act & Assert
        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());
    }



}