package fr.esgi.tracker.observer;

public interface LectureObserver {

    /**
     * Notifie les observers lors d'un changement dans la position de lecture (step)
     * @param step la position de lecture
     */
    void onStepChange(int step);
}
