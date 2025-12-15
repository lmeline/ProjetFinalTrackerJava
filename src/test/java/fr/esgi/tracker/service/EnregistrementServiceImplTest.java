package fr.esgi.tracker.service;

import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.business.Piste;
import fr.esgi.tracker.business.StatutRecord;
import fr.esgi.tracker.services.PisteService;
import fr.esgi.tracker.services.impl.EnregistrementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnregistrementServiceImplTest {

    @InjectMocks
    private EnregistrementServiceImpl enregistrementService;

    @Mock
    private PisteService pisteService;

    @Mock
    private Piste piste;

    @Mock
    private Note note;

    private Note[] sequence;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sequence = new Note[64];
    }

    @Test
    @DisplayName("Vérification du statut ARRETE par défaut")
    void testStatutDefaut() {
        // Assert
        assertEquals(StatutRecord.ARRETE, enregistrementService.getStatutRecord());
    }

    @Test
    @DisplayName("Véirification du changement de statut")
    void testSetStatutRecord() {
        // Act
        enregistrementService.setStatutRecord(StatutRecord.EN_COURS);

        // Assert
        assertEquals(StatutRecord.EN_COURS, enregistrementService.getStatutRecord());
    }

    @Test
    @DisplayName("Vérifier l'enregistrement d'une note à l'index step")
    void testEnregistrerNote() {
        // Arrange
        int stepIndex = 3;
        when(piste.getSequence()).thenReturn(sequence);

        // Act
        enregistrementService.enregistrerNote(note, piste, stepIndex);

        // Assert
        assertEquals(note, sequence[stepIndex]);
    }

    @Test
    @DisplayName("Vérifier la suppression d'une note à l'index step")
    void testSupprimerNote() {
        // Arrange
        int stepInput = 5;
        // On préremplit le tableau pour la suppression future
        sequence[stepInput] = note;
        when(piste.getSequence()).thenReturn(sequence);

        // Act
        enregistrementService.supprimerNote(piste, stepInput);

        // Assert
        assertNull(sequence[stepInput], "La note à l'index 5 aurait dû être supprimée");
    }
}