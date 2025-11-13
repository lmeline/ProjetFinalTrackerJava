package fr.esgi.tracker.business;

import java.util.Objects;

public class Piste {
    private int id;
    private static int compteur = 0;
    private float volume;
    private Sequence SequenceCourante;

    public Piste(Sequence SequenceCourante) {
        this.id = ++compteur;
        this.volume = 1.0f;
        this.SequenceCourante = SequenceCourante;
    }

    public int getId() {
        return id;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public Sequence getSequenceCourante() {
        return SequenceCourante;
    }

    public void setSequenceCourante(Sequence sequenceCourante) {
        SequenceCourante = sequenceCourante;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Piste piste = (Piste) o;
        return getId() == piste.getId() && Float.compare(getVolume(), piste.getVolume()) == 0 && Objects.equals(getSequenceCourante(), piste.getSequenceCourante());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVolume(), getSequenceCourante());
    }

    @Override
    public String toString() {
        return "Piste " + this.getId() + " | Sequence courante : " + this.getSequenceCourante().getNom();
    }
}
