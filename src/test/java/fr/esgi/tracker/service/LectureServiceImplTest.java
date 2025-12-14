package fr.esgi.tracker.service;

import fr.esgi.tracker.business.Hauteur;
import fr.esgi.tracker.business.Instrument;
import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.business.Piste;
import fr.esgi.tracker.business.StatutLecture;
import fr.esgi.tracker.observer.LectureObserver;
import fr.esgi.tracker.services.AudioService;
import fr.esgi.tracker.services.PisteService;
import fr.esgi.tracker.services.impl.LectureServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.lang.reflect.Field;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LectureServiceImplTest {

    @InjectMocks
    private LectureServiceImpl lectureService;

    @Mock
    private PisteService pisteService;

    @Mock
    private AudioService audioService;

    @Mock
    private ScheduledExecutorService horlogeMock;

    @Mock
    private ScheduledFuture<?> tacheMock;

    @Mock
    private Piste pisteMock;

    @Mock
    private LectureObserver observerMock;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        injecterChamp(lectureService, "horloge", horlogeMock);
    }

    @Test
    @DisplayName("Vérification Bouton Play : Horloge et Statut")
    void testPlay_Demarrage() {
        // Arrange
        when(pisteService.getPisteCourante()).thenReturn(pisteMock);
        doReturn(tacheMock).when(horlogeMock).scheduleAtFixedRate(any(), anyLong(), anyLong(), any());

        // Act
        lectureService.play();

        // Assert
        assertEquals(StatutLecture.EN_COURS, lectureService.getStatutLecture());
        verify(horlogeMock).scheduleAtFixedRate(any(Runnable.class), eq(0L), anyLong(), eq(TimeUnit.NANOSECONDS));
    }

    @Test
    @DisplayName("Vérification Bouton Play : Jouer des notes et incrémenter le step")
    void testPlay_LogiqueInterne() {
        // Arrange
        when(pisteService.getPisteCourante()).thenReturn(pisteMock);
        Note[] sequence = new Note[64];
        Instrument instrumentTest = new Instrument("PianoTest", "dummy.wav", Hauteur.C4);
        Note noteTest = new Note(Hauteur.C4, instrumentTest, 100);
        sequence[0] = noteTest;
        when(pisteMock.getSequence()).thenReturn(sequence);
        when(pisteMock.getVolume()).thenReturn(100.0F);
        doReturn(tacheMock).when(horlogeMock).scheduleAtFixedRate(any(), anyLong(), anyLong(), any());

        // Act
        lectureService.play();
        ArgumentCaptor<Runnable> captor = ArgumentCaptor.forClass(Runnable.class);
        verify(horlogeMock).scheduleAtFixedRate(captor.capture(), anyLong(), anyLong(), any());
        Runnable tacheLecture = captor.getValue();
        tacheLecture.run();

        // Assert
        verify(audioService).jouerNote(eq(noteTest), anyFloat());
        assertEquals(1, lectureService.getStep());
    }

    @Test
    @DisplayName("Vérification BOuton Stop : Arret Horloge et réinitialisation step")
    void testStop() throws Exception {
        injecterChamp(lectureService, "tache", tacheMock);
        lectureService.setStatutLecture(StatutLecture.EN_COURS);
        injecterChamp(lectureService, "step", 10);

        // Act
        lectureService.stop();

        // Assert
        assertEquals(StatutLecture.ARRETE, lectureService.getStatutLecture());
        assertEquals(0, lectureService.getStep());
        verify(tacheMock).cancel(false);
    }

    @Test
    @DisplayName("Vérification Bouton Pause : Arret Horloge sans réinitialisation step")
    void testPause() throws Exception {
        injecterChamp(lectureService, "tache", tacheMock);
        lectureService.setStatutLecture(StatutLecture.EN_COURS);
        injecterChamp(lectureService, "step", 5);

        // Act
        lectureService.pause();

        // Assert
        assertEquals(StatutLecture.EN_PAUSE, lectureService.getStatutLecture());
        assertEquals(5, lectureService.getStep());
        verify(tacheMock).cancel(false);
    }

    @Test
    @DisplayName("Vérification Incrémentation Step")
    void testIncrementerStep_Boucle() throws Exception {
        injecterChamp(lectureService, "step", 0);
        lectureService.incrementerStep();
        assertEquals(1, lectureService.getStep());

        injecterChamp(lectureService, "step", 63);
        lectureService.incrementerStep();
        assertEquals(0, lectureService.getStep());
    }

    @Test
    @DisplayName("Vérification Observer")
    void testObserver() {
        lectureService.addObserver(observerMock);
        lectureService.notifyObservers(5);
        verify(observerMock).onStepChange(5);

        lectureService.removeObserver(observerMock);
        lectureService.notifyObservers(6);
        verify(observerMock, times(1)).onStepChange(anyInt());
    }

    private void injecterChamp(Object objetCible, String nomChamp, Object valeurAInjecter) throws Exception {
        Field field = objetCible.getClass().getDeclaredField(nomChamp);
        field.setAccessible(true);
        field.set(objetCible, valeurAInjecter);
    }
}