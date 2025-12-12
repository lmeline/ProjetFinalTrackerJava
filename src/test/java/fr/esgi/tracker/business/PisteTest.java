package fr.esgi.tracker.business;

import fr.esgi.tracker.services.InstrumentService;
import fr.esgi.tracker.services.impl.InstrumentServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PisteTest {
    private final InstrumentService instrumentService = new InstrumentServiceImpl();

    @Test
    @DisplayName(value = "Cette méthode teste les getters setters de la classe métier Piste")
    public void testerGettersSetters() {
        //Arrange
        String nom = "PisteTest";
        Piste piste = new Piste(nom);
        Note[] notes = new Note[64];
        notes[0] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);

        // Act
        piste.setNomPreset(nom);
        piste.setSequence(notes);

        // Assert
        assertNotNull(piste.getNomPreset());
        assertNotNull(piste.getId());
        assertNotNull(piste.getNomPreset());
        assertNotNull(piste.getSequence());
        assertEquals(nom, piste.getNomPreset());
        assertEquals(notes, piste.getSequence());
    }
}
