package fr.esgi.tracker.services;

import fr.esgi.tracker.business.StatutLecture;
import fr.esgi.tracker.observer.LectureObservable;

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

    int getStep();

    void incrementerStep();

    void setStatutLecture(StatutLecture statutLecture);

    StatutLecture getStatutLecture();
}
