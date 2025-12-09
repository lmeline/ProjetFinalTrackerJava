package fr.esgi.tracker.observer;

public interface LectureObservable {
    void addObserver(LectureObserver observer);
    void removeObserver(LectureObserver observer);
    void notifyObservers(int step);
}
