package fr.esgi.tracker.services;

import fr.esgi.tracker.business.Piste;

public interface PisteService {

    Piste chargerPiste(String nom) throws Exception;

    void enregistrerPiste(Piste piste);

    void supprimerPiste(Piste piste);

    void chargerToutesLesPistes();

    Piste getPisteCourante();

}


