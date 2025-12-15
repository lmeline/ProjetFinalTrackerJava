package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.business.Piste;
import fr.esgi.tracker.business.StatutRecord;
import fr.esgi.tracker.services.EnregistrementService;
import fr.esgi.tracker.services.PisteService;
/**
 * Implémentation du service d’enregistrement des pistes musicales.
 * Ce service gère l’ajout et la suppression des notes
 * ainsi que l’état du mode d’enregistrement.
 */

public class EnregistrementServiceImpl implements EnregistrementService {
    private StatutRecord statutRecord = StatutRecord.ARRETE;

    /**
     * Enregistre une note dans la piste courante
     * à l’étape indiquée.
     */
    @Override
    public void enregistrerNote(Note note, PisteService pisteService, int step) {
        pisteService.getPisteCourante().getSequence()[step] = note;
    }

    /**
     * Supprime la note enregistrée à une étape donnée
     * d’une piste.
     */
    @Override
    public void supprimerNote(Piste piste, int step) {
        piste.getSequence()[step] = null;
    }

    /**
     * Modifie l’état du mode d’enregistrement.
     */
    @Override
    public void setStatutRecord(StatutRecord statutRecord) {
        this.statutRecord = statutRecord;
    }

    /**
     * Retourne l’état courant du mode d’enregistrement.
     */
    @Override
    public StatutRecord getStatutRecord() {
        return this.statutRecord;
    }
}
