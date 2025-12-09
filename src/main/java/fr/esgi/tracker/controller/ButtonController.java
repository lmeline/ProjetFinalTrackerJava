package fr.esgi.tracker.controller;

public class ButtonController {
    private final TrackerController trackerController;

    public ButtonController(TrackerController trackerController) {
        this.trackerController = trackerController;
    }


    public void ButtonPlayPressed() {
        System.out.println("Lecture démarrée");
        trackerController.getLectureService().play();
    }

    public void ButtonPausePressed() {
        System.out.println("Lecture mise en pause");
        trackerController.getLectureService().pause();
    }

    public void ButtonStopPressed() {
        trackerController.getLectureService().stop();
        System.out.println("Lecture arrêtée");
    }


}
