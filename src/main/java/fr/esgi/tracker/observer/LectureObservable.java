package fr.esgi.tracker.observer;

public interface LectureObservable {
    /**
     * Ajoute un observer à la liste des observers
     * @param observer l'observer à ajouter
     */
    void addObserver(LectureObserver observer);

    /**
     * Supprime un observer de la liste des observers
     * @param observer l'observer à retirer
     */
    void removeObserver(LectureObserver observer);

    /**
     * Notifie tous les observers de la liste des observers
     * @param step la position de lecture à transmettre
     */
    void notifyObservers(int step);
}
