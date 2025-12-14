package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Hauteur;
import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.business.Piste;
import fr.esgi.tracker.dao.PisteDao;
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

    private PisteDao pisteDao = new PisteDao();

    @Override
    public Piste chargerPiste(String nom) {
        System.out.println("piste " + nom + " chargée");
        this.pisteCourante = this.pistes.get(nom);
        return this.pisteCourante;
    }

    @Override
    public void enregistrerPiste(Piste piste, String nom) {
        Piste newPiste = piste.clone();
        newPiste.setNomPreset(nom);
        this.pistes.put(newPiste.getNomPreset(), newPiste);
        this.chargerPiste(newPiste.getNomPreset());
        System.out.println("piste " + nom + " enregistrée");
        System.out.println("pisteCourante : " + this.pisteCourante.getNomPreset());
        this.pisteDao.sauvegarder(newPiste);
    }

    @Override
    public void supprimerPiste(Piste piste) {
        this.pistes.remove(piste.getNomPreset());
    }

    @Override
    public void chargerToutesLesPistes() {
        List<Piste> pistes = new ArrayList<>();

        //Piste 1 :
        Piste pisteInit = new Piste("init", new Note[64]);

        pistes.add(pisteInit);

        this.pisteDao.initialiserDossier(pistes);
        this.pistes = this.pisteDao.chargerTout();
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
