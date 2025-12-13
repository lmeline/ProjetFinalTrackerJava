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


    public void ButtonPlayPressed(ActionEvent event) {

        System.out.println("Lecture démarrée");
        Button btn = (Button) event.getSource();
        Image image = new Image(getClass().getResourceAsStream("/fr/esgi/tracker/assets/icons/play_on.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(37);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        btn.setGraphic(imageView);
        trackerController.getLectureService().play();
    }

    public void ButtonPausePressed(ActionEvent event) {
        Button btn = (Button) event.getSource();
        Image image = new Image(getClass().getResourceAsStream("/fr/esgi/tracker/assets/icons/pause_on.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(37);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        btn.setGraphic(imageView);
        System.out.println("Lecture mise en pause");

        trackerController.getLectureService().pause();
    }

    public void ButtonStopPressed(ActionEvent event) {
        Button btn = (Button) event.getSource();
        Image image = new Image(getClass().getResourceAsStream("/fr/esgi/tracker/assets/icons/stop_on.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(37);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        btn.setGraphic(imageView);
        trackerController.getLectureService().stop();
        System.out.println("Lecture arrêtée");
    }


}
