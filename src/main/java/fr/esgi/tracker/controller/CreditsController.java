package fr.esgi.tracker.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Contrôleur de l’écran des crédits de l’application.
 * Il gère l’affichage de l’écran ainsi que le retour
 * vers l’écran principal.
 */

public class CreditsController {

    @FXML
    private Button backButton;

    /**
     * Permet de revenir à l’écran principal de l’application
     * lors de l’interaction avec le bouton de retour.
     */
    @FXML
    public void switchToPrimary() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fr/esgi/tracker/Tracker.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    /**
     * Initialise les composants de l’interface.
     * Applique une animation d’apparition au bouton
     * et gère les effets visuels lors du survol de la souris.
     */
    @FXML
    public void initialize() {
        backButton.setOpacity(0);
        FadeTransition fadeBtn = new FadeTransition(Duration.seconds(2), backButton);
        fadeBtn.setDelay(Duration.seconds(0.5));
        fadeBtn.setFromValue(0);
        fadeBtn.setToValue(1);
        fadeBtn.play();

        backButton.setOnMouseEntered(e -> backButton.setStyle(
                "-fx-background-color: #7878ff; -fx-text-fill: white; "
                        + "-fx-background-radius: 10; -fx-font-size: 14px; -fx-padding: 6 20;"));
        backButton.setOnMouseExited(e -> backButton.setStyle(
                "-fx-background-color: #5a5aff; -fx-text-fill: white; "
                        + "-fx-background-radius: 10; -fx-font-size: 14px; -fx-padding: 6 20;"));
    }
}
