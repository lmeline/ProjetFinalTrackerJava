package fr.esgi.tracker.business;

import javafx.scene.media.AudioClip;

import java.util.Objects;
/**
 * Représente un instrument de musique utilisé dans l’application.
 * Un instrument est associé à un fichier audio et à une hauteur
 * de référence pour la lecture des notes.
 */

public class Instrument {
    private Long id;
    private String nom;
    private String cheminFichier;
    private Hauteur hauteurDuSample;
    private static Long compteur = 0L;

    /**
     * Initialise un nouvel instrument avec ses informations principales.
     * L’identifiant est généré automatiquement lors de la création.
     */
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

    public Hauteur getHauteurDuSample() {
        return hauteurDuSample;
    }

    public void sethauteurDuSample(Hauteur hauteurDuSample) {
        this.hauteurDuSample = hauteurDuSample;
    }


    /**
     * Permet de comparer deux instruments afin de déterminer
     * s’ils représentent le même instrument.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Instrument that = (Instrument) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNom(), that.getNom()) && Objects.equals(getCheminFichier(), that.getCheminFichier()) && hauteurDuSample == that.hauteurDuSample;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNom(), getCheminFichier(), hauteurDuSample);
    }

    /**
     * Retourne une représentation textuelle de l’instrument,
     * principalement utilisée pour l’affichage.
     */
    @Override
    public String toString() {
        return this.nom + " | " + this.hauteurDuSample;
    }
}
