package fr.esgi.tracker.business;

import javafx.scene.media.AudioClip;

import java.net.URL;
import java.util.Objects;

public class Instrument {
    private Long id;
    private String nom;
    private URL cheminFichier;
    private AudioClip audioClip;
    private Hauteur hauteurDuSample;
    private static Long compteur = 0L;

    public Instrument(String nom, String cheminFichier, Hauteur hauteurDuSample) {
        this.id = ++compteur;
        this.nom = nom;
        this.hauteurDuSample = hauteurDuSample;

        URL url = getClass().getResource(cheminFichier);
        if (url == null) {
            throw new RuntimeException("Fichier audio non trouvé dans le classpath : " + cheminFichier);
        }
        this.cheminFichier = url;
        this.audioClip = new AudioClip(url.toExternalForm());
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

    public URL getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = getClass().getResource(cheminFichier);
    }

    public Hauteur gethauteurDuSample() {
        return hauteurDuSample;
    }

    public void sethauteurDuSample(Hauteur hauteurDuSample) {
        this.hauteurDuSample = hauteurDuSample;
    }

    public AudioClip getAudioClip() {
        return audioClip;
    }

    public Hauteur getHauteurDuSample() {
        return hauteurDuSample;
    }

    public void setHauteurDuSample(Hauteur hauteurDuSample) {
        this.hauteurDuSample = hauteurDuSample;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Instrument that = (Instrument) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNom(), that.getNom()) && Objects.equals(getCheminFichier(), that.getCheminFichier()) && Objects.equals(getAudioClip(), that.getAudioClip()) && getHauteurDuSample() == that.getHauteurDuSample();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNom(), getCheminFichier(), getAudioClip(), getHauteurDuSample());
    }

    @Override
    public String toString() {
        return this.nom + " | " + this.hauteurDuSample;
    }
}


