package fr.esgi.tracker.services;

import fr.esgi.tracker.business.Piste;

import java.util.Map;
/**
 * Service métier de gestion des pistes musicales.
 */

public interface PisteService {

    /**
     * Définie une nouvelle pisteCourante à partir de son nom
     * @param nom le nom de la piste à définir
     * @return La piste définie
     */
    Piste chargerPiste(String nom);

    /**
     * Enregistre une nouvelle piste
     * @param piste l'objet piste à enregistrer
     * @param nom le nom à appliquer à la nouvelle piste
     */
    void enregistrerPiste(Piste piste, String nom);

    /**
     * Supprime une piste
     * @param piste la piste à supprimer
     */
    void supprimerPiste(Piste piste);


    /**
     * Renvoie la piste courante
     * @return la piste courante
     */
    Piste getPisteCourante();

    /**
     * Renvoie toutes les pistes disponibles
     * @return une map contenant toutes les pistes
     */
    Map<String, Piste> getToutesLesPistes();

}


