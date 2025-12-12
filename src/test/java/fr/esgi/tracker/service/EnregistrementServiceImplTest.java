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

    // On utilise un vrai tableau pour simuler la séquence de notes
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
        when(pisteService.getPisteCourante()).thenReturn(piste);
        when(piste.getSequence()).thenReturn(sequence);

        // Act
        enregistrementService.EnregistrerNote(note, pisteService, stepIndex);

        // Assert
        assertEquals(note, sequence[stepIndex]);
        // Vérifie que la méthode getPisteCourante a été appelée
        verify(pisteService).getPisteCourante();
    }

    @Test
    @DisplayName("Vérifier la suppression d'une note à l'index step - 1")
    void testSupprimerNote() {
        // Arrange
        int stepInput = 5;
        int realIndex = 4;
        // On préremplit le tableau pour la suppression future
        sequence[realIndex] = note;
        when(piste.getSequence()).thenReturn(sequence);

        // Act
        enregistrementService.SupprimerNote(piste, stepInput);

        // Assert
        assertNull(sequence[realIndex], "La note à l'index 4 aurait dû être supprimée");
    }
}