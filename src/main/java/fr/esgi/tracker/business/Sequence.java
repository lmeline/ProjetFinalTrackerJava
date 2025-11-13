package fr.esgi.tracker.business;

import java.util.Arrays;
import java.util.Objects;

public class Sequence {
    private Long id;
    private String nom;
    private Note[] notes;
    private String cheminFichier;
    private static Long compteur = 0L;

    public Sequence(String nom, Note[] notes, String cheminFichier) {
        this.id = ++compteur;
        this.nom = nom;
        this.notes = notes;
        this.cheminFichier = cheminFichier;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Note[] getNotes() {
        return notes;
    }

    public void setNotes(Note[] notes) {
        this.notes = notes;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sequence sequence = (Sequence) o;
        return Objects.equals(getId(), sequence.getId()) && Objects.equals(getNom(), sequence.getNom()) && Objects.deepEquals(getNotes(), sequence.getNotes()) && Objects.equals(getCheminFichier(), sequence.getCheminFichier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNom(), Arrays.hashCode(getNotes()), getCheminFichier());
    }

    @Override
    public String toString() {
        return "Sequence " + this.getNom() ;
    }
}
