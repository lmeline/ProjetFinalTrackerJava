package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.business.Piste;
import fr.esgi.tracker.business.StatutRecord;
import fr.esgi.tracker.services.EnregistrementService;
import fr.esgi.tracker.services.PisteService;

public class EnregistrementServiceImpl implements EnregistrementService {
    private StatutRecord statutRecord = StatutRecord.ARRETE;

    @Override
    public void enregistrerNote(Note note, PisteService pisteService, int step) {
        pisteService.getPisteCourante().getSequence()[step] = note;
    }

    @Override
    public void supprimerNote(Piste piste, int step) {
        piste.getSequence()[step] = null;
    }

    @Override
    public void setStatutRecord(StatutRecord statutRecord) {
        this.statutRecord = statutRecord;
    }

    @Override
    public StatutRecord getStatutRecord() {
        return this.statutRecord;
    }
}