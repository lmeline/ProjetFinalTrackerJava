package fr.esgi.tracker.business;

import javafx.scene.media.AudioClip;

import java.util.Objects;

public class Instrument {
    private Long id;
    private String nom;
    private String cheminFichier;
    private Hauteur hauteurDuSample;
    private static Long compteur = 0L;

    public Instrument(String nom, String cheminFichier, Hauteur hauteurDuSample) {
        this.id = ++compteur;
        this.nom = nom;
        this.cheminFichier = cheminFichier;
        this.hauteurDuSample = hauteurDuSample;
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

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public Hauteur gethauteurDuSample() {
        return hauteurDuSample;
    }

    public void sethauteurDuSample(Hauteur hauteurDuSample) {
        this.hauteurDuSample = hauteurDuSample;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Instrument that = (Instrument) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNom(), that.getNom()) && Objects.equals(getCheminFichier(), that.getCheminFichier()) && gethauteurDuSample() == that.gethauteurDuSample();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNom(), getCheminFichier(), gethauteurDuSample());
    }

    @Override
    public String toString() {
        return this.nom + " | " + this.hauteurDuSample;
    }
}


