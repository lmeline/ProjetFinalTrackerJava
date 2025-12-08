package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.business.Piste;
import fr.esgi.tracker.business.StatutLecture;
import fr.esgi.tracker.services.AudioService;
import fr.esgi.tracker.services.LectureService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class LectureServiceImpl implements LectureService {
    private Piste piste;
    private int bpm = 120;
    private StatutLecture statutLecture = StatutLecture.ARRETE;
    private AudioService audioService;
    private ScheduledExecutorService horloge = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> tache;
    private int step = 1;

    public LectureServiceImpl(Piste piste) {
        this.piste = piste;
        this.audioService = new AudioServiceImpl();
    }

    @Override
    public void play() {
        this.arreterHorloge();
        this.statutLecture = StatutLecture.EN_COURS;
        //this.prechargerSequence();
        this.tache = this.horloge.scheduleAtFixedRate(() -> {
            try {
                Note note = this.piste.getSequence()[this.step - 1];
                if (note != null) new Thread(() -> {audioService.jouerNote(note, this.piste.getVolume());}).start();
                //System.out.println("Step" + this.step);
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
    }

    @Override
    public void pause() {
        this.arreterHorloge();
        this.statutLecture = StatutLecture.EN_PAUSE;
    }

    /**
     * Incrémente le pas sur la séquence, et fait un retour au début (boucle) lorsqu'on arrive au bout de la séquence
     */
    private void incrementerStep() {
        if (this.step == 64) {
            this.step = 1;
        } else {
            this.step ++;
        }
    }

    /**
     * Arrête l'horloge (ScheduledFuture)
     */
    private void arreterHorloge() {
        if (this.tache != null) {
            this.tache.cancel(false);
        }
    }

    private void prechargerSequence() {
        for (Note note : this.piste.getSequence()) {
            if (note != null) audioService.jouerNote(note, 0);
        }
    }
}
