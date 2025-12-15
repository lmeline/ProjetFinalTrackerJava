package fr.esgi.tracker.services;

import fr.esgi.tracker.business.Note;
/**
 * Service de gestion des éléments audio de l’application.
 */

public interface AudioService {

    /**
     * Joue la note passée en paramètre
     * @param note La note à jouer
     * @param volume Le master volume du tracker
     */
    void jouerNote(Note note, float volume);

    /**
     * Charge les samples des instruments pour toutes les notes disponibles
     */
    void loadSamples();
}
