package fr.esgi.tracker.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PisteTest {

    @Test
    @DisplayName("Vérifications getters setters de la classe métier Piste")
    void testerGettersSetters() {
        // Arrange
        String nom = "PisteTest";
        Piste piste = new Piste(nom);
        Instrument instrumentFake = new Instrument("piano", "dummy.wav", Hauteur.C4);

        Note[] notes = new Note[64];
        notes[0] = new Note(Hauteur.D3, instrumentFake, 1.0f);

        // Act
        piste.setNomPreset(nom);
        piste.setSequence(notes);

        // Assert
        assertNotNull(piste.getNomPreset());
        assertNotNull(piste.getId());
        assertNotNull(piste.getSequence());

        assertEquals(nom, piste.getNomPreset());
        assertArrayEquals(notes, piste.getSequence());
    }
}