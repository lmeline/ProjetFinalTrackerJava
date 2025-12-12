package fr.esgi.tracker.services;

import fr.esgi.tracker.business.Instrument;

import java.util.Map;

public interface InstrumentService {


    Instrument getInstrument(String nom);

    Map<String, Instrument> getAllInstruments();

    void chargerTousLesInstruments();

    void setInstrumentCourant(Instrument instrument);

    Instrument getInstrumentCourant();



}
