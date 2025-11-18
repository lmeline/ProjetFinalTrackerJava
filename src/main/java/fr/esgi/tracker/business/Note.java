package fr.esgi.tracker.business;

import java.util.Objects;

public class Note {
    private Hauteur hauteur;
    private Instrument instrument;
    private float volume;

    public Note(Hauteur hauteur, Instrument instrument, float volume) {
        this.hauteur = hauteur;
        this.instrument = instrument;
        if (volume > 1.0f) {
            this.volume = 1.0f;
        } else if (volume < 0.0f) {
            this.volume = 0.0f;
        } else {
            this.volume = volume;
        }
    }

    public Hauteur getHauteur() {
        return hauteur;
    }

    public void setHauteur(Hauteur hauteur) {
        this.hauteur = hauteur;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Float.compare(getVolume(), note.getVolume()) == 0 && getHauteur() == note.getHauteur() && Objects.equals(getInstrument(), note.getInstrument());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHauteur(), getInstrument(), getVolume());
    }

    @Override
    public String toString() {
        return "Note : " + this.getHauteur() + " | Instrument : " + this.getInstrument().getNom() + " | Volume : " + this.getVolume() ;
    }
}
