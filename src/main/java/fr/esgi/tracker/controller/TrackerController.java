package fr.esgi.tracker.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import fr.esgi.tracker.App;
import fr.esgi.tracker.services.LectureService;
import java.net.URL;
import java.util.ResourceBundle;

public class TrackerController  {
    private LectureService lectureService;
    @FXML
    Button playButton;
    @FXML
    Button pauseButton;
    @FXML
    Button stopButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
