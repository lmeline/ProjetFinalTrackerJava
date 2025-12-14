package fr.esgi.tracker.services;

import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.business.Piste;
import fr.esgi.tracker.business.StatutRecord;

public interface EnregistrementService {
    void EnregistrerNote(Note note, PisteService pisteService, int step);
    void SupprimerNote(Piste piste, int step);
    void setStatutRecord(StatutRecord statutRecord);
    StatutRecord getStatutRecord();
}
