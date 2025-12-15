package fr.esgi.tracker.services;

import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.business.Piste;
import fr.esgi.tracker.business.StatutRecord;
/**
 * Service dédié à l’enregistrement des pistes musicales.
 */

public interface EnregistrementService {

    /**
     * Enregistre une note dans la piste courante
     * @param note la note à enregistrer
     * @param piste la piste dans laquelle ajouter une note
     * @param step la step à laquelle la note doit être enregistrée
     */
    void enregistrerNote(Note note, Piste piste, int step);

    /**
     * Supprime une note dans la piste donnée
     * @param piste la piste dans laquelle supprimer une note
     * @param step la step à laquelle la note doit être supprimée
     */
    void supprimerNote(Piste piste, int step);

    /**
     * Met à jour le statut d'enregistrement (ARRET ou EN_COURS)
     * @param statutRecord le statut d'enregistrement
     */
    void setStatutRecord(StatutRecord statutRecord);
    StatutRecord getStatutRecord();
}
