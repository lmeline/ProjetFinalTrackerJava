package fr.esgi.tracker.service;

import fr.esgi.tracker.business.Hauteur;
import fr.esgi.tracker.business.Instrument;
import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.services.InstrumentService;
import fr.esgi.tracker.services.impl.AudioServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AudioServiceImplTest {

    @Mock
    private InstrumentService instrumentService;

    private AudioServiceImpl audioService;

    private Map<String, EnumMap<Hauteur, float[]>> fakeSamplesMap;
    private List<Object[]> voicesList;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        audioService = new AudioServiceImpl(instrumentService);
        fakeSamplesMap = new HashMap<>();
        injecterChamp(audioService, "samples", fakeSamplesMap);
        voicesList = (List<Object[]>) recupererChamp(audioService, "voices");
    }

    @AfterEach
    void tearDown() {
        if (audioService != null) {
            audioService.stopAudio();
        }
    }

    @Test
    @DisplayName("Vérification de la lecture d'une note")
    void testJouerNote() {
        // Arrange
        String nomInstru = "PianoTest";
        Hauteur hauteur = Hauteur.C4;
        Hauteur hauteurInstrument = Hauteur.C4;

        Instrument instrument = mock(Instrument.class);
        when(instrument.getNom()).thenReturn(nomInstru);
        when(instrument.getHauteurDuSample()).thenReturn(hauteurInstrument);

        Note note = mock(Note.class);
        when(note.getInstrument()).thenReturn(instrument);
        when(note.getHauteur()).thenReturn(hauteur);

        float[] fakeAudioData = new float[100];
        EnumMap<Hauteur, float[]> mapHauteur = new EnumMap<>(Hauteur.class);
        mapHauteur.put(hauteur, fakeAudioData);
        fakeSamplesMap.put(nomInstru, mapHauteur);

        // Act
        audioService.jouerNote(note, 0.8f);

        // Assert
        assertFalse(voicesList.isEmpty(), "La liste des voix ne doit pas être vide");
        assertEquals(1, voicesList.size());

        Object[] voice = voicesList.get(0);
        assertNotNull(voice);
        assertEquals(fakeAudioData, voice[0]); // Les données audio
        assertEquals(0.8f, (float) voice[2], 0.001); // Le volume
    }

    @Test
    @DisplayName("Vérification de l'arrêt du service audio")
    void testStopAudio() throws Exception {
        // Act
        audioService.stopAudio();

        // Assert
        boolean isRunning = (boolean) recupererChamp(audioService, "running");
        assertFalse(isRunning, "Le service ne devrait plus être en status 'running'");
    }

    private void injecterChamp(Object objetCible, String nomChamp, Object valeurAInjecter) throws Exception {
        Field field = objetCible.getClass().getDeclaredField(nomChamp);
        field.setAccessible(true);
        field.set(objetCible, valeurAInjecter);
    }

    private Object recupererChamp(Object objetCible, String nomChamp) throws Exception {
        Field field = objetCible.getClass().getDeclaredField(nomChamp);
        field.setAccessible(true);
        return field.get(objetCible);
    }
}