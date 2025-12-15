package fr.esgi.tracker.services;

import fr.esgi.tracker.business.StatutLecture;
import fr.esgi.tracker.observer.LectureObservable;

/**
 * Service dédié à la lecture des pistes musicales.
 */

public interface LectureService extends LectureObservable {
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

    /**
     * Retourne la position de lecture actuelle
     */
    int getStep();

    /**
     * Incrémente la position de lecture de 1
     */
    void incrementerStep();

    /**
     * Décrémente la position de lecture de 1
     */
    void decrementerStep();

    /**
     * Met à jour le statut de lecture
     * @param statutLecture le statut de lecture à appliquer (EN_COURS/EN_PAUSE/ARRETE)
     */
    void setStatutLecture(StatutLecture statutLecture);

    /**
     * Retourne le statut de lecture actuel
     */
    StatutLecture getStatutLecture();
}
