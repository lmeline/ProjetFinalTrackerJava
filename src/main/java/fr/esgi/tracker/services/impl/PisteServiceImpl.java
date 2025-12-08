package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Hauteur;
import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.business.Piste;
import fr.esgi.tracker.services.InstrumentService;
import fr.esgi.tracker.services.PisteService;
import fr.esgi.tracker.utils.PisteJsonManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PisteServiceImpl implements PisteService {
    private Map<String, Piste> pistes;
    private Piste pisteCourante;
    private final InstrumentService instrumentService = new InstrumentServiceImpl();

    @Override
    public Piste chargerPiste(String nom) {
        System.out.println("piste " + nom + " chargée");
        this.pisteCourante = this.pistes.get(nom);
        return this.pisteCourante;
    }

    @Override
    public void enregistrerPiste(Piste piste) {
        this.pistes.put(piste.getNomPreset(), piste);
        PisteJsonManager.sauvegarderPisteEnJson(piste);
    }

    @Override
    public void supprimerPiste(Piste piste) {
        this.pistes.remove(piste.getNomPreset());
    }

    @Override
    public void chargerToutesLesPistes() {
        List<Piste> pistes = new ArrayList<>();

        //Piste 1 :
        Piste pisteInit = new Piste(
                "Init",
                new Note[64]
        );

        //Piste 2 :
        Note[] notes = new Note[64];
        notes[0] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[2] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[4] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[8] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[12] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[16] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[20] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[24] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[28] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[32] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[36] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[40] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[44] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[48] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[52] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[56] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);
        notes[60] = new Note(Hauteur.D3, instrumentService.getInstrument("piano"), 1.0f);

        Piste piste4onTheFloor = new Piste(
                "4onTheFloor",
                notes
        );
        pistes.add(pisteInit);
        pistes.add(piste4onTheFloor);

        PisteJsonManager.initializeDirectory(pistes);
        this.pistes = PisteJsonManager.chargerToutesLesPistes();
    }

    @Override
    public Piste getPisteCourante() {
        return this.pisteCourante;
    }

    @Override
    public Map<String, Piste> getToutesLesPistes() {
        return this.pistes;
    }
}
