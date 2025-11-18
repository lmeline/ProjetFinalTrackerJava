package fr.esgi.tracker.services;

public interface LectureService {
    /**
     * Lance la lecture du tracker
     */
    void play();

    /**
     * Stop la lecture du tracker (retour au début de la séquence)
     */
    void stop();

    /**
     * Met la lecture en pause (conservation de la position dans la séquence)
     */
    void pause();
}
