package fr.esgi.tracker.controller;

import fr.esgi.tracker.services.PisteService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EnregistrerPisteModaleController {
    private TrackerController mainController;

    @FXML private Button saveButton;
    @FXML private TextField nomPiste;

    public EnregistrerPisteModaleController(TrackerController mainController) {
        this.mainController = mainController;
    }

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
