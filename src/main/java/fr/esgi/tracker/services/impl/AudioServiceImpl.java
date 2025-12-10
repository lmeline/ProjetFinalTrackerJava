package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Hauteur;
import fr.esgi.tracker.business.Instrument;
import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.services.AudioService;
import fr.esgi.tracker.services.InstrumentService;
import javafx.scene.media.AudioClip;

import javax.sound.sampled.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AudioServiceImpl implements AudioService {
    //private Map<Hauteur, AudioClip> audioClips = new HashMap<>();
    private Map<String, EnumMap<Hauteur, AudioClip[]>> audioClipsByInstrument = new HashMap<>();
    private Map<String, EnumMap<Hauteur, AtomicInteger>> audioClipsIndexes = new HashMap<>();
    private InstrumentService instrumentService;

    public AudioServiceImpl(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }


    @Override
    public void jouerNote(Note note, float volume) {
        EnumMap<Hauteur, AudioClip[]> poolMap = this.audioClipsByInstrument.get(note.getInstrument().getNom());
        EnumMap<Hauteur, AtomicInteger> indexMap = this.audioClipsIndexes.get(note.getInstrument().getNom());

        if (poolMap != null && indexMap != null){
            AudioClip[] pool = poolMap.get(note.getHauteur());
            AtomicInteger indexTracker = indexMap.get(note.getHauteur());

            if (pool != null && indexTracker != null) {
                int currentPoolIndex = indexTracker.getAndIncrement() % 4;
                AudioClip ac = pool[currentPoolIndex];
                ac.setVolume(volume);
                ac.play();
            }
        }
    }





    private double setHauteur(Note note) {
        return note.getHauteur().getFrequence() / note.getInstrument().getHauteurDuSample().getFrequence();
    }

    public void loadAudioClips() {
        System.out.println("debut chargement clips");
        for (Instrument is : instrumentService.getAllInstruments().values()) {
            EnumMap<Hauteur, AudioClip[]> audioClips = new EnumMap<>(Hauteur.class);
            EnumMap<Hauteur, AtomicInteger> hauteurIndices = new EnumMap<>(Hauteur.class);
            for (Hauteur h : Hauteur.values()) {
                AudioClip[] audioClipsArray = new AudioClip[4];
                for (int i = 0; i < 4; i++) {
                    AudioClip audioClip = new AudioClip(getClass().getResource(is.getCheminFichier()).toExternalForm());
                    audioClip.setRate(h.getFrequence()/is.getHauteurDuSample().getFrequence());
                    audioClip.setVolume(0);
                    audioClip.play();
                    audioClip.stop();
                    audioClip.setVolume(1.0);
                    audioClipsArray[i] = audioClip;
                }
                audioClips.put(h, audioClipsArray);
                hauteurIndices.put(h, new AtomicInteger(0));
            }
            this.audioClipsByInstrument.put(is.getNom(), audioClips);
            this.audioClipsIndexes.put(is.getNom(), hauteurIndices);
        }
        System.out.println("fin chargement clips");

    }
}
