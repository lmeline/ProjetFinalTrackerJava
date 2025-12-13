package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Hauteur;
import fr.esgi.tracker.business.Instrument;
import fr.esgi.tracker.services.InstrumentService;

import java.util.HashMap;
import java.util.Map;

public class InstrumentServiceImpl implements InstrumentService {

    private Map<String, Instrument> instruments = new HashMap<>();
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
        this.instruments = Map.of(
                    "piano", new Instrument("piano", "/fr/esgi/tracker/instruments/piano_C3.wav", Hauteur.C3),
                    "guitM", new Instrument("guitM", "/fr/esgi/tracker/instruments/guitar_amped_mid.wav", Hauteur.C3),
                    "guitL", new Instrument("guitL", "/fr/esgi/tracker/instruments/guitar_amped_low.wav", Hauteur.C3),
                    "kickdrum", new Instrument("kickdrum", "/fr/esgi/tracker/instruments/kickdrum.wav", Hauteur.C3),
                    "snaredrum", new Instrument("snaredrum", "/fr/esgi/tracker/instruments/snaredrum.wav", Hauteur.C3),
                    "hh closed", new Instrument("hh closed", "/fr/esgi/tracker/instruments/closed_hat.wav", Hauteur.C3),
                "sw kick", new Instrument("sw kick", "/fr/esgi/tracker/instruments/sw_kick.wav", Hauteur.C3),
                "sw snare", new Instrument("sw snare", "/fr/esgi/tracker/instruments/sw_snare.wav", Hauteur.C3),
                "sw bass", new Instrument("sw bass", "/fr/esgi/tracker/instruments/sw_bass.wav", Hauteur.C3)
        );
        System.out.println("Instruments chargés");
    }
}
