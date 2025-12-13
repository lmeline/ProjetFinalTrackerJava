package fr.esgi.tracker.dao;

import fr.esgi.tracker.business.Piste;
import fr.esgi.tracker.utils.PisteJsonManager;
import java.util.List;
import java.util.Map;


public class PisteDao {
    public void sauvegarder(Piste piste) {
        PisteJsonManager.sauvegarderPisteEnJson(piste);
    }

    public void initialiserDossier(List<Piste> pistes) {
        PisteJsonManager.initializeDirectory(pistes);
    }

    public Map<String, Piste> chargerTout() {
        return PisteJsonManager.chargerToutesLesPistes();
    }
}