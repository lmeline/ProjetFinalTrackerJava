package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.business.Piste;
import fr.esgi.tracker.business.StatutLecture;
import fr.esgi.tracker.observer.LectureObserver;
import fr.esgi.tracker.services.AudioService;
import fr.esgi.tracker.services.LectureService;
import fr.esgi.tracker.services.PisteService;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
/**
 * Implémentation du service de lecture des pistes musicales.
 * Ce service gère la lecture temporelle des notes,
 * le contrôle du rythme et la notification des observateurs.
 */

public class LectureServiceImpl implements LectureService {
    private int bpm = 90;
    private StatutLecture statutLecture = StatutLecture.ARRETE;
    private PisteService pisteService;
    private AudioService audioService;
    private ScheduledExecutorService horloge = Executors.newSingleThreadScheduledExecutor();
    private final ExecutorService audioExecutor = Executors.newSingleThreadExecutor();
    private ScheduledFuture<?> tache;
    private int step = 0;
    private List<LectureObserver> observers = new ArrayList<>();

    /**
     * Initialise le service de lecture avec les services nécessaires
     * à la gestion des pistes et à la sortie audio.
     */
    public LectureServiceImpl(PisteService pisteService, AudioService audioService) {
        this.pisteService = pisteService;
        this.audioService = audioService;
    }

    /**
     * Démarre la lecture de la piste courante
     * selon le tempo défini.
     */
    @Override
    public void play() {
        Piste piste = this.pisteService.getPisteCourante();
        if (this.statutLecture == StatutLecture.ARRETE && this.step != 0) {
            this.step = 0;
            this.notifyObservers(this.step);
        }
        this.arreterHorloge();
        this.statutLecture = StatutLecture.EN_COURS;
        //this.prechargerSequence();
        this.tache = this.horloge.scheduleAtFixedRate(() -> {
            try {
                Note note = piste.getSequence()[this.step];
                if (note != null) audioService.jouerNote(note, piste.getVolume());
                this.notifyObservers(this.step);
                this.incrementerStep();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, (60_000_000_000L / this.bpm/4), TimeUnit.NANOSECONDS);
    }

    /**
     * Arrête complètement la lecture et réinitialise
     * la position de lecture.
     */
    @Override
    public void stop() {
        this.arreterHorloge();
        this.statutLecture = StatutLecture.ARRETE;
        this.step = 0;
        this.notifyObservers(this.step);
    }

    /**
     * Met la lecture en pause sans réinitialiser la position.
     */
    @Override
    public void pause() {
        this.arreterHorloge();
        this.statutLecture = StatutLecture.EN_PAUSE;
    }

    /**
     * Incrémente le pas sur la séquence et effectue
     * un retour au début lorsque la fin est atteinte.
     */
    @Override
    public void incrementerStep() {
        if (this.step == 63) {
            this.step = 0;
        } else {
            this.step ++;
        }
        notifyObservers(step);
    }

    /**
     * Décrémente le pas sur la séquence et effectue
     * un retour à la fin lorsque le début est atteint.
     */
    @Override
    public void decrementerStep() {
        if (this.step == 0) {
            this.step = 63;
        } else {
            this.step --;
        }
        notifyObservers(step);
    }

    /**
     * Arrête l’horloge de lecture si elle est active.
     */
    private void arreterHorloge() {
        if (this.tache != null) {
            this.tache.cancel(false);
        }
    }

    /**
     * Ajoute un observateur à la liste des observateurs
     * notifiés lors des changements d’étape.
     */
    @Override
    public void addObserver(LectureObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Supprime un observateur de la liste des observateurs.
     */
    @Override
    public void removeObserver(LectureObserver observer) {
        this.observers.remove(observer);
    }

    /**
     * Retourne l’étape courante de lecture.
     */
    @Override
    public int getStep() {
        return this.step;
    }



    /**
     * Notifie l’ensemble des observateurs d’un changement d’étape.
     */
    @Override
    public void notifyObservers(int step) {
        for (LectureObserver observer : this.observers) {
            //Platform.runLater(() -> observer.onStepChange(step));
            observer.onStepChange(step);
        }
    }

    /**
     * Modifie l’état de la lecture.
     */
    @Override
    public void setStatutLecture(StatutLecture statutLecture) {
        this.statutLecture = statutLecture;
    }

    /**
     * Retourne l’état courant de la lecture.
     */
    @Override
    public StatutLecture getStatutLecture() {
        return this.statutLecture;
    }
}
