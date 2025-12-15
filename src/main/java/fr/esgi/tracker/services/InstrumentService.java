package fr.esgi.tracker.services;

import fr.esgi.tracker.business.Instrument;

import java.util.Map;
/**
 * Service de gestion des instruments.
 */

public interface InstrumentService {

    /**
     * Retourne un instrument en fonction de son nom
     * @param nom le nom de l'instrument
     * @return l'instrument demandé
     */
    Instrument getInstrument(String nom);

    /**
     * Retourne un Map de tout les instruments préchargés
     * @return tous les instruments
     */
    Map<String, Instrument> getAllInstruments();


    /**
     * Défini un instrument courant
     * @param instrument l'instrument à definir comme courant
     */
    void setInstrumentCourant(Instrument instrument);


    /**
     * Retourne l'instrument courant
     * @return l'instrument courant
     */
    Instrument getInstrumentCourant();



}
