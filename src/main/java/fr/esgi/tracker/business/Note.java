package fr.esgi.tracker.business;

import java.util.Objects;
/**
 * Représente une note de musique.
 * Une note est définie par une hauteur, un instrument
 * et un niveau de volume.
 */

public class Note {
    private Hauteur hauteur;
    private Instrument instrument;
    private float volume;

    /**
     * Crée une note avec une hauteur, un instrument et un volume.
     * Le volume est automatiquement borné entre 0.0 et 1.0.
     */
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

    /**
     * Permet de comparer deux notes afin de déterminer
     * si elles possèdent les mêmes caractéristiques.
     */
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

    /**
     * Fournit une représentation textuelle de la note,
     * principalement utilisée pour l’affichage.
     */
    @Override
    public String toString() {
        return this.getHauteur().name() + " | " + this.getInstrument().getNom();
    }
}
