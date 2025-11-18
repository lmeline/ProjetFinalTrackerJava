package fr.esgi.tracker.controller;

import java.awt.*;
import java.io.IOException;

import fr.esgi.tracker.App;
import javafx.fxml.FXML;

public class TrackerController {
    @FXML
    Button playButton;
    @FXML
    Button pauseButton;
    @FXML
    Button stopButton;

    public void initialize() {
        // VÉRIFICATION 1 : Est-ce que le bouton a été injecté ?
        if (playButton != null) {
            System.out.println("✅ playButton est connecté ! Texte : " + playButton.getText());
        } else {
            System.out.println("❌ ERREUR : playButton est NULL. Vérifiez le fx:id.");
        }

        // VÉRIFICATION 2 : Ajouter une action simple
        if (playButton != null) {
            playButton.setOnAction(event -> {
                System.out.println("Bouton PLAY cliqué !");
            });
        }
    }
}
