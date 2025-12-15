package fr.esgi.tracker.business;

import java.util.Arrays;
import java.util.Objects;

/**
 * Représente une piste musicale de l’application.
 * Contient les informations nécessaires à la lecture
 * et à la gestion d’une séquence de notes.
 */


public class Piste implements Cloneable {
    private int id;
    private String nomPreset;
    private static int compteur = 0;
    private float volume;
    private Note[] sequence;

    /**
     * Crée une piste associée à un preset et à une séquence de notes.
     * Le volume est initialisé à une valeur par défaut.
     */
    public Piste(String nomPreset, Note[] sequence) {
        this.nomPreset = nomPreset;
        this.id = ++compteur;
        this.volume = 0.5f;
        this.sequence = sequence;
    }

    /**
     * Crée une piste associée à un preset sans séquence de notes.
     * Le volume est initialisé à sa valeur maximale.
     */
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

    /**
     * Permet de comparer deux pistes afin de déterminer
     * si elles possèdent les mêmes caractéristiques.
     */
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

    /**
     * Fournit une représentation textuelle de la piste,
     * principalement utilisée pour l’affichage.
     */
    @Override
    public String toString() {
        return "Piste " + this.getId() + " | preset : " + this.getNomPreset() + " | volume : " + this.getVolume();
    }

    /**
     * Crée une copie de la piste en dupliquant la séquence de notes.
     * La nouvelle piste est indépendante de l’originale.
     */
    @Override
    public Piste clone() {
        Note[] notesClone = Arrays.copyOf(this.sequence, this.sequence.length); // nouveau tableau
        return new Piste(this.nomPreset, notesClone);
    }

}
