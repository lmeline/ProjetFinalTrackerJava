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
                "init",
                new Note[64]
        );

        pistes.add(pisteInit);

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
