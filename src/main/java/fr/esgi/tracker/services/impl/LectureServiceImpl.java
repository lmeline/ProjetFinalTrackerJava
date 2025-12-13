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

public class LectureServiceImpl implements LectureService {
    private int bpm = 90;
    private StatutLecture statutLecture = StatutLecture.ARRETE;
    private PisteService pisteService;
    private AudioService audioService;
    private ScheduledExecutorService horloge = Executors.newSingleThreadScheduledExecutor();
    private final ExecutorService audioExecutor = Executors.newSingleThreadExecutor();
    private ScheduledFuture<?> tache;
    private int step = 1;
    private List<LectureObserver> observers = new ArrayList<>();

    public LectureServiceImpl(PisteService pisteService, AudioService audioService) {
        this.pisteService = pisteService;
        this.audioService = audioService;
    }

    @Override
    public void play() {
        Piste piste = this.pisteService.getPisteCourante();
        if (this.statutLecture == StatutLecture.ARRETE && this.step != 1) {
            this.step = 1;
            this.notifyObservers(this.step - 1);
        }
        this.arreterHorloge();
        this.statutLecture = StatutLecture.EN_COURS;
        //this.prechargerSequence();
        this.tache = this.horloge.scheduleAtFixedRate(() -> {
            try {
                Note note = piste.getSequence()[this.step - 1];
                if (note != null) audioService.jouerNote(note, piste.getVolume());
                this.notifyObservers(this.step - 1);
                this.incrementerStep();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, (60_000_000_000L / this.bpm/4), TimeUnit.NANOSECONDS);
    }

    @Override
    public void stop() {
        this.arreterHorloge();
        this.statutLecture = StatutLecture.ARRETE;
        this.step = 1;
        this.notifyObservers(this.step -1);
    }

    @Override
    public void pause() {
        this.arreterHorloge();
        this.statutLecture = StatutLecture.EN_PAUSE;
    }

    /**
     * Incrémente le pas sur la séquence, et fait un retour au début (boucle) lorsqu'on arrive au bout de la séquence
     */
    @Override
    public void incrementerStep() {
        if (this.step == 64) {
            this.step = 1;
        } else {
            this.step ++;
        }
        notifyObservers(step);
    }

    /**
     * Arrête l'horloge (ScheduledFuture)
     */
    private void arreterHorloge() {
        if (this.tache != null) {
            this.tache.cancel(false);
        }
    }

    @Override
    public void addObserver(LectureObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(LectureObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public int getStep() {
        return this.step;
    }



    @Override
    public void notifyObservers(int step) {
        for (LectureObserver observer : this.observers) {
            //Platform.runLater(() -> observer.onStepChange(step));
            observer.onStepChange(step);
        }
    }

    @Override
    public void setStatutLecture(StatutLecture statutLecture) {
        this.statutLecture = statutLecture;
    }

    @Override
    public StatutLecture getStatutLecture() {
        return this.statutLecture;
    }
}
