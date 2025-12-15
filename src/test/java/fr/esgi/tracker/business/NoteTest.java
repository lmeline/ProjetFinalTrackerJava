package fr.esgi.tracker.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {

    private final Hauteur HAUTEUR_TEST = Hauteur.A2;
    private final Instrument INSTRUMENT_TEST = new Instrument("Piano", "path", Hauteur.A2);

    @Test
    @DisplayName("Vérification du  et getters")
    void testConstructeur() {
        // Arrange
        float volumeAttendu = 0.5f;

        // Act
        Note note = new Note(HAUTEUR_TEST, INSTRUMENT_TEST, volumeAttendu);

        // Assert
        assertEquals(HAUTEUR_TEST, note.getHauteur());
        assertEquals(INSTRUMENT_TEST, note.getInstrument());
        assertEquals(volumeAttendu, note.getVolume(), "Le volume doit être initialisé par le constructeur");
    }

    @Test
    @DisplayName("Vérification des setters")
    void testSetters() {
        // Arrange
        Note note = new Note(HAUTEUR_TEST, INSTRUMENT_TEST, 1.0f);

        Hauteur nouvelleHauteur = Hauteur.C4;
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
}