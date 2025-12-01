package fr.esgi.tracker.controller;

import javafx.scene.control.Button;
import javafx.scene.Scene;

public class PianoController {

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
                    }
                });

                C2.requestFocus();
            }
        });

        // ACTIONS DES TOUCHES
        C2.setOnAction(e -> System.out.println("C2 joué !"));
        CSharp2.setOnAction(e -> System.out.println("C#2 joué !"));
        D2.setOnAction(e -> System.out.println("D2 joué !"));
        DSharp2.setOnAction(e -> System.out.println("D#2 joué !"));
        E2.setOnAction(e -> System.out.println("E2 joué !"));
        F2.setOnAction(e -> System.out.println("F2 joué !"));
        FSharp2.setOnAction(e -> System.out.println("F#2 joué !"));
        G2.setOnAction(e -> System.out.println("G2 joué !"));
        GSharp2.setOnAction(e -> System.out.println("G#2 joué !"));
        A2.setOnAction(e -> System.out.println("A2 joué !"));
        ASharp2.setOnAction(e -> System.out.println("A#2 joué !"));
        B2.setOnAction(e -> System.out.println("B2 joué !"));
        C3.setOnAction(e -> System.out.println("C3 joué !"));
        CSharp3.setOnAction(e -> System.out.println("C#3 joué !"));
        D3.setOnAction(e -> System.out.println("D3 joué !"));
        DSharp3.setOnAction(e -> System.out.println("D#3 joué !"));
        E3.setOnAction(e -> System.out.println("E3 joué !"));
        F3.setOnAction(e -> System.out.println("F3 joué !"));
        FSharp3.setOnAction(e -> System.out.println("F#3 joué !"));
        G3.setOnAction(e -> System.out.println("G3 joué !"));
        GSharp3.setOnAction(e -> System.out.println("G#3 joué !"));
        A3.setOnAction(e -> System.out.println("A3 joué !"));
        ASharp3.setOnAction(e -> System.out.println("A#3 joué !"));
        B3.setOnAction(e -> System.out.println("B3 joué !"));
    }
}
