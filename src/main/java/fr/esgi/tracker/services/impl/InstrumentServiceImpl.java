package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Hauteur;
import fr.esgi.tracker.business.Instrument;
import fr.esgi.tracker.services.InstrumentService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class InstrumentServiceImpl implements InstrumentService {

    private Map<String, Instrument> instruments = new LinkedHashMap<>();
    private Instrument instrumentCourant;

    public Instrument getInstrumentCourant() {
        return instrumentCourant;
    }

    public void setInstrumentCourant(Instrument instrumentCourant) {
        this.instrumentCourant = instrumentCourant;
    }

    public InstrumentServiceImpl() {
        this.chargerTousLesInstruments();
    }

    @Override
    public Instrument getInstrument(String nom) {
        return this.instruments.get(nom);
    }

    @Override
    public Map<String, Instrument> getAllInstruments() {
        return this.instruments;
    }

    @Override
    public void chargerTousLesInstruments() {
        this.instruments.put("piano", new Instrument("piano", "/fr/esgi/tracker/instruments/piano_C3.wav", Hauteur.C3));
        this.instruments.put("guitElec", new Instrument("guitElec", "/fr/esgi/tracker/instruments/guitar_amped_mid.wav", Hauteur.C3));
        this.instruments.put("sw kick", new Instrument("sw kick", "/fr/esgi/tracker/instruments/sw_kick.wav", Hauteur.C3));
        this.instruments.put("sw snare", new Instrument("sw snare", "/fr/esgi/tracker/instruments/sw_snare.wav", Hauteur.C3));
        this.instruments.put("sw hat", new Instrument("sw hat", "/fr/esgi/tracker/instruments/sw_hat.wav", Hauteur.C3));
        this.instruments.put("sw bass", new Instrument("sw bass", "/fr/esgi/tracker/instruments/sw_bass.wav", Hauteur.C3));
    }
}
