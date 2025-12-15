package fr.esgi.tracker.controller;

import fr.esgi.tracker.services.PisteService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * Contrôleur de la fenêtre modale d’enregistrement des pistes.
 * Cette fenêtre permet de sauvegarder la piste courante
 * en lui attribuant un nom.
 */

public class EnregistrerPisteModaleController {
    private TrackerController mainController;

    @FXML private Button saveButton;
    @FXML private TextField nomPiste;

    /**
     * Crée le contrôleur de la fenêtre modale en conservant
     * une référence vers le contrôleur principal.
     */
    public EnregistrerPisteModaleController(TrackerController mainController) {
        this.mainController = mainController;
    }

    /**
     * Initialise le comportement de la fenêtre modale.
     * Gère l’enregistrement de la piste et la fermeture
     * de la fenêtre après validation.
     */
    @FXML
    public void initialize() {
        // ex : initialisation de la modale
        saveButton.setOnAction(e -> {
            PisteService pisteService = mainController.getPisteService();
            pisteService.enregistrerPiste(pisteService.getPisteCourante(), nomPiste.getText());
            mainController.updateListePistes();

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        });
    }


}
