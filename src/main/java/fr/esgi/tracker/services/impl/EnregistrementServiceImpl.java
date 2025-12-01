package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.business.Piste;
import fr.esgi.tracker.services.EnregistrementService;

public class EnregistrementServiceImpl implements EnregistrementService {
    @Override
    public void EnregistrerNote(Note note, Piste piste, int step) {
        piste.getSequence()[step - 1] = note;
    }

    @Override
    public void SupprimerNote(Piste piste, int step) {
        piste.getSequence()[step - 1] = null;
    }
}
