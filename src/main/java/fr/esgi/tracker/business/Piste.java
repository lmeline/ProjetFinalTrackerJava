package fr.esgi.tracker.business;

import java.util.Arrays;
import java.util.Objects;

public class Piste {
    private int id;
    private String nomPreset;
    private static int compteur = 0;
    private float volume;
    private Note[] sequence;

    public Piste(String nomPreset, Note[] sequence) {
        this.nomPreset = nomPreset;
        this.id = ++compteur;
        this.volume = 1.0f;
        this.sequence = sequence;
    }

    public Piste(String nomPreset){
        this.nomPreset = nomPreset;
        this.id = ++compteur;
        this.volume = 1.0f;
        this.sequence = null;
    }

    public int getId() {
        return id;
    }

    public Note[] getSequence() {
        return sequence;
    }

    public void setSequence(Note[] sequence) {
        this.sequence = sequence;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public String getNomPreset() {
        return nomPreset;
    }

    public void setNomPreset(String nomPreset) {
        this.nomPreset = nomPreset;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Piste piste = (Piste) o;
        return getId() == piste.getId() && getNomPreset() == piste.getNomPreset() && Float.compare(getVolume(), piste.getVolume()) == 0 && Objects.deepEquals(getSequence(), piste.getSequence());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNomPreset(), getVolume(), Arrays.hashCode(getSequence()));
    }

    @Override
    public String toString() {
        return "Piste " + this.getId() + " | preset : " + this.getNomPreset() + " | volume : " + this.getVolume();
    }
}