package fr.esgi.tracker.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstrumentTest {

    @Test
    @DisplayName("Vérification du constructeur et getters")
    void testConstructeur() {
        // Arrange
        String nom = "Flute";
        String chemin = "path/to/flute.wav";
        Hauteur hauteur = Hauteur.C4;

        // Act
        Instrument instrument = new Instrument(nom, chemin, hauteur);

        // Assert
        assertNotNull(instrument.getId(), "L'ID doit être généré automatiquement");
        assertEquals(nom, instrument.getNom());
        assertEquals(chemin, instrument.getCheminFichier());
        assertEquals(hauteur, instrument.getHauteurDuSample());
    }

    @Test
    @DisplayName("Vérification Id Unique")
    void testIdUnique() {
        // Act
        Instrument i1 = new Instrument("Instru1", "path1", Hauteur.A2);
        Instrument i2 = new Instrument("Instru2", "path2", Hauteur.A2);

        // Assert
        assertNotEquals(i1.getId(), i2.getId(), "Chaque instrument doit avoir un ID unique (auto-incrément)");
        assertNotEquals(i1, i2, "Deux instruments créés séparément ne doivent pas être égaux");
    }

    @Test
    @DisplayName("Véirification des setters")
    void testSetters() {
        // Arrange
        Instrument instrument = new Instrument("Piano", "path", Hauteur.C4);

        // Act
        instrument.setNom("Guitare");
        instrument.setCheminFichier("new/path.wav");
        instrument.sethauteurDuSample(Hauteur.A2);

        // Assert
        assertEquals("Guitare", instrument.getNom());
        assertEquals("new/path.wav", instrument.getCheminFichier());
        assertEquals(Hauteur.A2, instrument.getHauteurDuSample());
    }
}