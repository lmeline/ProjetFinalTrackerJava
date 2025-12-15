package fr.esgi.tracker.services;

import fr.esgi.tracker.business.Piste;

import java.util.Map;
/**
 * Service métier de gestion des pistes musicales.
 */

public interface PisteService {

    Piste chargerPiste(String nom);

    void enregistrerPiste(Piste piste, String nom);

    void supprimerPiste(Piste piste);

    void chargerToutesLesPistes();

    Piste getPisteCourante();

    Map<String, Piste> getToutesLesPistes();

}


