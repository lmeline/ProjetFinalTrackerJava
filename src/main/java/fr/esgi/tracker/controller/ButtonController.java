package fr.esgi.tracker.controller;

import fr.esgi.tracker.business.StatutLecture;

public class ButtonController {
    private final TrackerController trackerController;

    public ButtonController(TrackerController trackerController) {
        this.trackerController = trackerController;
    }


    public void ButtonPlayPressed() {
        System.out.println("Lecture démarrée");
        trackerController.getLectureService().play();
        trackerController.getLectureService().setStatutLecture(StatutLecture.EN_COURS);
    }

    public void ButtonPausePressed() {
        System.out.println("Lecture mise en pause");
        trackerController.getLectureService().pause();
        StatutLecture statutActuel = trackerController.getLectureService().getStatutLecture();
        if (statutActuel == StatutLecture.EN_PAUSE || statutActuel == StatutLecture.EN_COURS){
            trackerController.getLectureService().setStatutLecture(StatutLecture.EN_PAUSE);
        }
    }

    public void ButtonStopPressed() {
        System.out.println("Lecture mis en arrêt");
        trackerController.getLectureService().stop();
        trackerController.getLectureService().setStatutLecture(StatutLecture.ARRETE);
    }


}
