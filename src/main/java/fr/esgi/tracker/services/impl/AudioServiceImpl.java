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

public class AudioServiceImpl implements AudioService {
    //private Map<Hauteur, AudioClip> audioClips = new HashMap<>();
    private Map<String, EnumMap<Hauteur, AudioClip>> audioClipsByInstrument = new HashMap<>();
    private InstrumentService instrumentService;

    public AudioServiceImpl(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }


    @Override
    public void jouerNote(Note note, float volume) {
        AudioClip ac = this.audioClipsByInstrument.get(note.getInstrument().getNom()).get(note.getHauteur());
        ac.setVolume(volume);
        ac.play();
    }





    private double setHauteur(Note note) {
        return note.getHauteur().getFrequence() / note.getInstrument().getHauteurDuSample().getFrequence();
    }

    public void loadAudioClips() {
        System.out.println("debut chargement clips");
        for (Instrument is : instrumentService.getAllInstruments().values()) {
            EnumMap<Hauteur, AudioClip> audioClips = new EnumMap<>(Hauteur.class);
            for (Hauteur h : Hauteur.values()) {
                AudioClip audioClip = new AudioClip(getClass().getResource(is.getCheminFichier()).toExternalForm());
                audioClip.setRate(h.getFrequence()/is.getHauteurDuSample().getFrequence());
                audioClips.put(h, audioClip);
            }
            this.audioClipsByInstrument.put(is.getNom(), audioClips);
        }
        System.out.println("fin chargement clips");

    }
}
