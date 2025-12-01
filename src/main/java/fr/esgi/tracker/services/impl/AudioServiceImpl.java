package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.services.AudioService;
import javafx.scene.media.AudioClip;

public class AudioServiceImpl implements AudioService {
    @Override
    public void jouerNote(Note note, float volume) {
        System.out.println(note.toString());
        AudioClip clip = note.getInstrument().getAudioClip();

        clip.setRate(this.setHauteur(note));
        clip.play();
    }



    private double setHauteur(Note note) {
        return note.getHauteur().getFrequence() / note.getInstrument().getHauteurDuSample().getFrequence();
    }
}
