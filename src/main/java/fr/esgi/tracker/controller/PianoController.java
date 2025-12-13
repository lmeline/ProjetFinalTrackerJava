package fr.esgi.tracker.controller;

import fr.esgi.tracker.business.*;
import fr.esgi.tracker.services.AudioService;
import fr.esgi.tracker.services.InstrumentService;
import fr.esgi.tracker.services.impl.AudioServiceImpl;
import fr.esgi.tracker.services.impl.InstrumentServiceImpl;
import javafx.scene.control.Button;
import javafx.scene.Scene;

public class PianoController {
    private final AudioService audioService = new AudioServiceImpl(new InstrumentServiceImpl());
    private final TrackerController trackerController;

    public PianoController(TrackerController trackerController) {
        this.trackerController = trackerController;
    }

    public void initKeys(
            Button C2, Button CSharp2, Button D2, Button DSharp2,
            Button E2, Button F2, Button FSharp2, Button G2, Button GSharp2,
            Button A2, Button ASharp2, Button B2, Button C3, Button CSharp3,
            Button D3, Button DSharp3, Button E3, Button F3, Button FSharp3,
            Button G3, Button GSharp3, Button A3, Button ASharp3, Button B3
    ) {

        // CLAVIER PC → PIANO
        C2.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {

                newScene.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case A: C2.fire(); break;
                        case DIGIT2: CSharp2.fire(); break;
                        case Z: D2.fire(); break;
                        case DIGIT3: DSharp2.fire(); break;
                        case E: E2.fire(); break;
                        case R: F2.fire(); break;
                        case DIGIT5: FSharp2.fire(); break;
                        case T: G2.fire(); break;
                        case DIGIT6: GSharp2.fire(); break;
                        case Y: A2.fire(); break;
                        case DIGIT7: ASharp2.fire(); break;
                        case U: B2.fire(); break;
                        case I: C3.fire(); break;
                        case DIGIT9: CSharp3.fire(); break;
                        case O: D3.fire(); break;
                        case DIGIT0: DSharp3.fire(); break;
                        case P: E3.fire(); break;
                        case W: F3.fire(); break;
                        case S: FSharp3.fire(); break;
                        case X: G3.fire(); break;
                        case D: GSharp3.fire(); break;
                        case C: A3.fire(); break;
                        case F: ASharp3.fire(); break;
                        case V: B3.fire(); break;
                        case DOLLAR: {
                            if (trackerController.getEnregistrementService().getStatutRecord() == StatutRecord.EN_COURS && trackerController.getLectureService().getStatutLecture() == StatutLecture.ARRETE) {
                                System.out.println("enter pressed");
                                trackerController.getEnregistrementService().EnregistrerNote(null, trackerController.getPisteService(), trackerController.getLectureService().getStep());
                                trackerController.getLectureService().incrementerStep();
                                //trackerController.updatePisteView(trackerController.getPisteService().getPisteCourante(), trackerController.getLectureService().getStep() - 1);
                            } else {
                                trackerController.getLectureService().incrementerStep();
                            }
                        } break;
                    }
                });

                C2.requestFocus();
            }
        });

    }
}
