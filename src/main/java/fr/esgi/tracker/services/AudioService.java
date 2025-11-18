package fr.esgi.tracker.services;

import fr.esgi.tracker.business.Note;

public interface AudioService {

    /**
     * Joue la note passée en paramètre
     * @param note La note à jouer
     * @param volume Le master volume du tracker
     */
    void jouerNote(Note note, float volume);
}
