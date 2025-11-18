package fr.esgi.tracker.business;

public class Tracker {
    private Piste piste;
    private int bpm = 120;
    private float volume = 1.0f;

    public Tracker(Piste piste) {
        this.piste = piste;

    }

    public int getBpm() {
        return bpm;
    }

    public Piste getPiste() {
        return piste;
    }
}
