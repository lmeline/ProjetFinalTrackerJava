package fr.esgi.tracker.services;

import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.business.Piste;

public interface EnregistrementService {
    void EnregistrerNote(Note note, Piste piste, int step);
    void SupprimerNote(Piste piste, int step);
}
