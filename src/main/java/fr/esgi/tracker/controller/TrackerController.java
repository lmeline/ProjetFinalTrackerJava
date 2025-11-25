package fr.esgi.tracker.controller;

import fr.esgi.tracker.business.Note;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import fr.esgi.tracker.App;
import fr.esgi.tracker.services.LectureService;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class TrackerController  {
    private LectureService lectureService;

    @FXML private Button playButton;
    @FXML private Button pauseButton;
    @FXML private Button stopButton;
    @FXML private TableView<String> TrackerList;
    @FXML private TableColumn<String, String> NoteList;

    private ObservableList<String> data;
    private Timeline timeline;
    private int currentRow = 0;

    // Touches Blanches
    @FXML private Button C2;
    @FXML private Button D2;
    @FXML private Button E2;
    @FXML private Button F2;
    @FXML private Button G2;
    @FXML private Button A2;
    @FXML private Button B2;
    @FXML private Button C3;
    @FXML private Button D3;
    @FXML private Button E3;
    @FXML private Button F3;
    @FXML private Button G3;
    @FXML private Button A3;
    @FXML private Button B3;

    // Touches noires
    @FXML private Button CSharp2;
    @FXML private Button DSharp2;
    @FXML private Button FSharp2;
    @FXML private Button GSharp2;
    @FXML private Button ASharp2;
    @FXML private Button CSharp3;
    @FXML private Button DSharp3;
    @FXML private Button FSharp3;
    @FXML private Button GSharp3;
    @FXML private Button ASharp3;
    @FXML
    private void ButtonPausePressed(ActionEvent event) {
        System.out.println("Memo: cliquer pause - Démarrage de l'enregistrement/lecture.");
        // Logique pour DÉMARRER
    }
    @FXML
    private void ButtonPlayPressed(ActionEvent event) {
        System.out.println("Memo: cliquer play - Démarrage de l'enregistrement/lecture.");
        // Logique pour DÉMARRER
    } @FXML
    private void ButtonStopPressed(ActionEvent event) {
        System.out.println("Memo: cliquer stop - Démarrage de l'enregistrement/lecture.");
        // Logique pour DÉMARRER
    }

    @FXML
    public void initialize() {

        // Associer la colonne aux données
        NoteList.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue())
        );

        // Remplir 64 lignes
        data = FXCollections.observableArrayList();
        for (int i = 0; i < 64; i++) {
            data.add(String.format("%02d ---", i));
        }

        TrackerList.setItems(data);

        // Animation
        timeline = new Timeline(
                new KeyFrame(Duration.millis(200), e -> {
                    currentRow = (currentRow + 1) % data.size();
                    TrackerList.getSelectionModel().select(currentRow);
                    TrackerList.scrollTo(currentRow);
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);

        C2.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                // Listener clavier sur la scène
                newScene.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case A : C2.fire(); break;
                        case DIGIT2 : CSharp2.fire(); break;
                        case Z : D2.fire(); break;
                        case DIGIT3 : DSharp2.fire(); break;
                        case E : E2.fire(); break;
                        case R : F2.fire(); break;
                        case DIGIT5 : FSharp2.fire(); break;
                        case T : G2.fire(); break;
                        case DIGIT6 : GSharp2.fire(); break;
                        case Y : A2.fire(); break;
                        case DIGIT7 : ASharp2.fire(); break;
                        case U : B2.fire(); break;
                        case I : C3.fire(); break;
                        case DIGIT9 : CSharp3.fire(); break;
                        case O : D3.fire(); break;
                        case DIGIT0: DSharp3.fire(); break;
                        case P : E3.fire(); break;
                        case W : F3.fire(); break;
                        case S : FSharp3.fire(); break;
                        case X : G3.fire(); break;
                        case D : GSharp3.fire(); break;
                        case C : A3.fire(); break;
                        case F : ASharp3.fire(); break;
                        case V : B3.fire(); break;
                    }
                });

                // Donner le focus pour capter les touches
                C2.requestFocus();
            }
        });

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
