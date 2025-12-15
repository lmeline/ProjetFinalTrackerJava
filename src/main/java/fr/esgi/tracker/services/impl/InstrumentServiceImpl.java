package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Hauteur;
import fr.esgi.tracker.business.Instrument;
import fr.esgi.tracker.services.InstrumentService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Implémentation du service de gestion des instruments.
 * Ce service permet de charger, stocker et fournir
 * les instruments disponibles dans l’application.
 */

public class InstrumentServiceImpl implements InstrumentService {

    private Map<String, Instrument> instruments = new LinkedHashMap<>();
    private Instrument instrumentCourant;
    /**
     * Initialise le service et charge l’ensemble
     * des instruments disponibles.
     */
    public InstrumentServiceImpl() {
        this.chargerTousLesInstruments();
    }

    /**
     * Retourne l’instrument actuellement sélectionné.
     */
    public Instrument getInstrumentCourant() {
        return instrumentCourant;
    }

    /**
     * Définit l’instrument actuellement sélectionné.
     */
    public void setInstrumentCourant(Instrument instrumentCourant) {
        this.instrumentCourant = instrumentCourant;
    }

    /**
     * Retourne un instrument à partir de son nom.
     */
    @Override
    public Instrument getInstrument(String nom) {
        return this.instruments.get(nom);
    }

    /**
     * Retourne l’ensemble des instruments chargés.
     */
    @Override
    public Map<String, Instrument> getAllInstruments() {
        return this.instruments;
    }

    /**
     * Charge tous les instruments de l’application
     * et les rend disponibles à l'utilisation.
     */
    private void chargerTousLesInstruments() {
        this.instruments.put("piano", new Instrument("piano", "/fr/esgi/tracker/instruments/piano_C3.wav", Hauteur.C3));
        this.instruments.put("guitElec", new Instrument("guitElec", "/fr/esgi/tracker/instruments/guitar_amped_mid.wav", Hauteur.C3));
        this.instruments.put("sw kick", new Instrument("sw kick", "/fr/esgi/tracker/instruments/sw_kick.wav", Hauteur.C3));
        this.instruments.put("sw snare", new Instrument("sw snare", "/fr/esgi/tracker/instruments/sw_snare.wav", Hauteur.C3));
        this.instruments.put("sw hat", new Instrument("sw hat", "/fr/esgi/tracker/instruments/sw_hat.wav", Hauteur.C3));
        this.instruments.put("sw bass", new Instrument("sw bass", "/fr/esgi/tracker/instruments/sw_bass.wav", Hauteur.C3));
    }
}
