package fr.esgi.tracker.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;

public class ButtonController {
    private final TrackerController trackerController;

    public ButtonController(TrackerController trackerController) {
        this.trackerController = trackerController;
    }


    public void ButtonPlayPressed() {
        trackerController.getLectureService().play();
    }

    public void ButtonPausePressed() {

        trackerController.getLectureService().pause();
    }

    public void ButtonStopPressed() {
        trackerController.getLectureService().stop();
        System.out.println("Lecture arrêtée");
    }


}
