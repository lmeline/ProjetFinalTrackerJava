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
/**
 * Implémentation du service de gestion des pistes musicales.
 * Ce service permet de charger, enregistrer, supprimer
 * et fournir les pistes utilisées dans l’application.
 */

public class PisteServiceImpl implements PisteService {
    private Map<String, Piste> pistes;
    private Piste pisteCourante;
    private final InstrumentService instrumentService = new InstrumentServiceImpl();

    private PisteDao pisteDao = new PisteDao();

    /**
     * Charge une piste à partir de son nom et
     * définit cette piste comme piste courante.
     */
    @Override
    public Piste chargerPiste(String nom) {
        Piste piste = this.pistes.get(nom);
        if (piste == null) {
            return null;
        }
        this.pisteCourante = piste.clone();
        return this.pisteCourante;
    }

    /**
     * Enregistre une piste sous un nom donné
     * et la rend disponible dans la liste des pistes.
     */
    @Override
    public void enregistrerPiste(Piste piste, String nom) {
        piste.setNomPreset(nom);
        this.pistes.put(piste.getNomPreset(), piste);
        this.chargerPiste(piste.getNomPreset());
        this.pisteDao.sauvegarder(piste);
    }

    /**
     * Supprime une piste de la liste des pistes disponibles.
     */
    @Override
    public void supprimerPiste(Piste piste) {
        this.pistes.remove(piste.getNomPreset());
    }

    /**
     * Charge toutes les pistes disponibles.
     * Initialise des pistes par défaut et
     * récupère les pistes persistées.
     */
    @Override
    public void chargerToutesLesPistes() {
        List<Piste> pistes = new ArrayList<>();

        //Piste init :
        Piste pisteInit = new Piste("init", new Note[64]);
        pistes.add(pisteInit);


        //Piste Hells Bells :
        Note[] notes_hb = new Note[64];
        notes_hb[0] = new Note(Hauteur.C3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[2] = new Note(Hauteur.G3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[4] = new Note(Hauteur.C3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[6] = new Note(Hauteur.F3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[10] = new Note(Hauteur.C3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[12] = new Note(Hauteur.ASharp2, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[14] = new Note(Hauteur.DSharp3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[18] = new Note(Hauteur.C3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[20] = new Note(Hauteur.ASharp2, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[22] = new Note(Hauteur.F3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[26] = new Note(Hauteur.C3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[28] = new Note(Hauteur.ASharp2, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[32] = new Note(Hauteur.C3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[34] = new Note(Hauteur.G3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[36] = new Note(Hauteur.C3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[38] = new Note(Hauteur.F3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[42] = new Note(Hauteur.C3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[44] = new Note(Hauteur.ASharp2, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[46] = new Note(Hauteur.DSharp3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[50] = new Note(Hauteur.C3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[52] = new Note(Hauteur.ASharp2, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[54] = new Note(Hauteur.F3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[58] = new Note(Hauteur.DSharp3, instrumentService.getInstrument("guitElec"), 1.0f);
        notes_hb[60] = new Note(Hauteur.D3, instrumentService.getInstrument("guitElec"), 1.0f);
        Piste pisteHellsBells = new Piste("hells_bells", notes_hb);
        pistes.add(pisteHellsBells);

        //Piste SynthWave rythm
        Note[] notes_sw = new Note[64];
        notes_sw[0] = new Note(Hauteur.C3, instrumentService.getInstrument("sw kick"), 1.0f);
        notes_sw[2] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[4] = new Note(Hauteur.C3, instrumentService.getInstrument("sw snare"), 1.0f);
        notes_sw[6] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[8] = new Note(Hauteur.C3, instrumentService.getInstrument("sw kick"), 1.0f);
        notes_sw[10] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[12] = new Note(Hauteur.C3, instrumentService.getInstrument("sw snare"), 1.0f);
        notes_sw[14] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[16] = new Note(Hauteur.C3, instrumentService.getInstrument("sw kick"), 1.0f);
        notes_sw[18] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[20] = new Note(Hauteur.C3, instrumentService.getInstrument("sw snare"), 1.0f);
        notes_sw[22] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[24] = new Note(Hauteur.C3, instrumentService.getInstrument("sw kick"), 1.0f);
        notes_sw[26] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[28] = new Note(Hauteur.C3, instrumentService.getInstrument("sw snare"), 1.0f);
        notes_sw[30] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[32] = new Note(Hauteur.C3, instrumentService.getInstrument("sw kick"), 1.0f);
        notes_sw[34] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[36] = new Note(Hauteur.C3, instrumentService.getInstrument("sw snare"), 1.0f);
        notes_sw[38] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[40] = new Note(Hauteur.C3, instrumentService.getInstrument("sw kick"), 1.0f);
        notes_sw[42] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[44] = new Note(Hauteur.C3, instrumentService.getInstrument("sw snare"), 1.0f);
        notes_sw[46] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[48] = new Note(Hauteur.C3, instrumentService.getInstrument("sw kick"), 1.0f);
        notes_sw[50] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[52] = new Note(Hauteur.C3, instrumentService.getInstrument("sw snare"), 1.0f);
        notes_sw[54] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[56] = new Note(Hauteur.C3, instrumentService.getInstrument("sw kick"), 1.0f);
        notes_sw[58] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        notes_sw[60] = new Note(Hauteur.C3, instrumentService.getInstrument("sw snare"), 1.0f);
        notes_sw[62] = new Note(Hauteur.C3, instrumentService.getInstrument("sw hat"), 1.0f);
        Piste pisteSw = new Piste("synthwave_drums", notes_sw);
        pistes.add(pisteSw);

        this.pisteDao.initialiserDossier(pistes);
        this.pistes = this.pisteDao.chargerTout();
    }

    /**
     * Retourne la piste actuellement sélectionnée.
     */
    @Override
    public Piste getPisteCourante() {
        return this.pisteCourante;
    }

    /**
     * Retourne l’ensemble des pistes disponibles.
     */
    @Override
    public Map<String, Piste> getToutesLesPistes() {
        return this.pistes;
    }


}
