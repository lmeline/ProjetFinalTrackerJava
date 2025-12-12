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
                    "piano", new Instrument("piano", "/fr/esgi/tracker/instruments/piano_C3.wav", Hauteur.C4)
        );
        System.out.println("Instruments chargés");
    }
}
