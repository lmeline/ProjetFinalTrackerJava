package fr.esgi.tracker.controller;

import fr.esgi.tracker.business.*;
import fr.esgi.tracker.observer.LectureObserver;
import fr.esgi.tracker.services.*;
import fr.esgi.tracker.services.impl.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TrackerController implements LectureObserver {
    //Services
    private LectureService lectureService;
    private PisteService pisteService = new PisteServiceImpl();
    private InstrumentService instrumentService = new InstrumentServiceImpl();
    private final EnregistrementService enregistrementService = new EnregistrementServiceImpl();
    private AudioService audioService;

    //------------------------
    // Elements FXML
    @FXML private Button playButton;
    @FXML private Button pauseButton;
    @FXML private Button stopButton;
    @FXML private Button recordButton;
    @FXML private Button saveButton;
    @FXML Slider volumeSlider;
    @FXML private ComboBox<String> liste_pistes;
    @FXML private ComboBox<String> liste_instruments;

    @FXML private VBox pisteView;
    @FXML private HBox stepMinusFour;
    @FXML private HBox stepMinusThree;
    @FXML private HBox stepMinusTwo;
    @FXML private HBox stepMinusOne;
    @FXML private HBox currentStep;
    @FXML private HBox stepPlusOne;
    @FXML private HBox stepPlusTwo;
    @FXML private HBox stepPlusThree;
    @FXML private HBox stepPlusFour;
    //Liste des lignes de notes
    private List<HBox> stepRows;


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

    //------------------------------------------------------------------------------------

    //METHODES

    // Getters Services pour les controlleurs secondaire
    public PisteService getPisteService() {
        return pisteService;
    }

    //-----------------------------------------------------

    //Methodes appelées par l'interface

    @FXML
    public void initialize() {
        // Initialisation des services

        //InstrumentService
        this.instrumentService.chargerTousLesInstruments();
        this.instrumentService.setInstrumentCourant(this.instrumentService.getInstrument("guitElec"));

        //PisteService
        this.pisteService.chargerToutesLesPistes();
        this.pisteService.chargerPiste("hells_bells");



        //AudioService
        this.audioService = new AudioServiceImpl(this.instrumentService);
        this.audioService.loadSamples();

        //LectureService
        this.lectureService = new LectureServiceImpl(this.pisteService, this.audioService);
        this.lectureService.addObserver(this);

        //Initialisation d'elements d'interface
        this.initializePisteView();
        this.initializeListePistes();
        this.initializeListeInstruments();
        this.initializeVolumeSlider();
        this.initializePianoKeys();


        this.toggleButtonIcon(stopButton, "on");


    }

    @FXML
    private void openCredits(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fr/esgi/tracker/Credits.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openSauvegardeModale() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/esgi/tracker/EnregistrerPisteModale.fxml"));
            EnregistrerPisteModaleController controller = new EnregistrerPisteModaleController(this);
            loader.setController(controller);
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Fenêtre Modale");
            modalStage.initModality(Modality.APPLICATION_MODAL); // rend la fenêtre modale
            modalStage.initOwner(((Node) saveButton.getScene().getRoot()).getScene().getWindow()); // parent window
            modalStage.setScene(new Scene(root));
            modalStage.showAndWait(); // bloque jusqu'à la fermeture
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buttonPlayPressed() {
        System.out.println("pressed");
        if (lectureService.getStatutLecture() != StatutLecture.EN_COURS) {
            toggleButtonIcon(stopButton, "off");
            toggleButtonIcon(pauseButton, "off");
            toggleButtonIcon(playButton, "on");
        }
        this.lectureService.play();
    }

    @FXML
    private void buttonPausePressed() {
        System.out.println("pressed");
        if (lectureService.getStatutLecture() == StatutLecture.EN_COURS) {
            toggleButtonIcon(stopButton, "off");
            toggleButtonIcon(pauseButton, "on");
            toggleButtonIcon(playButton, "off");
        }
        this.lectureService.pause();
    }

    @FXML
    private void buttonStopPressed() {
        if (lectureService.getStatutLecture() != StatutLecture.ARRETE) {
            toggleButtonIcon(stopButton, "on");
            toggleButtonIcon(pauseButton, "off");
            toggleButtonIcon(playButton, "off");
        }
        this.lectureService.stop();

    }

    @FXML
    private void buttonRecordPressed() {
        if (enregistrementService.getStatutRecord() != StatutRecord.ARRETE) {
            toggleButtonIcon(recordButton, "off");
        } else {
            toggleButtonIcon(recordButton, "on");
        }

        if (enregistrementService.getStatutRecord() == StatutRecord.ARRETE)
            enregistrementService.setStatutRecord(StatutRecord.EN_COURS);
        else {
            enregistrementService.setStatutRecord(StatutRecord.ARRETE);
        }
    }

    @FXML
    public void noteTriggered(ActionEvent e) {
        Button btn = (Button) e.getSource();
        Note note = new Note(Hauteur.valueOf(btn.getId()), instrumentService.getInstrumentCourant(), 1.0f);

        if (enregistrementService.getStatutRecord() == StatutRecord.EN_COURS) {
            this.enregistrementService.enregistrerNote(note, pisteService, lectureService.getStep());
            if (lectureService.getStatutLecture() != StatutLecture.EN_COURS) {
                lectureService.incrementerStep();
                this.audioService.jouerNote(note, pisteService.getPisteCourante().getVolume());
            }
        } else {
            this.audioService.jouerNote(note, pisteService.getPisteCourante().getVolume());
        }
    }

    //Implémentation interface Observer
    @Override
    public void onStepChange(int step) {
        Platform.runLater(()-> updatePisteView(pisteService.getPisteCourante(), step));
    }


    // Methodes additionnelles

    private void toggleButtonIcon(Button button, String value){
        String buttonName = button.getId().replace("Button", "");
        String path = "/fr/esgi/tracker/assets/icons/" + buttonName + "_" + value + ".png";
        Image newImage = new Image(getClass().getResourceAsStream(path));
        ImageView imageView = new ImageView(newImage);
        imageView.setFitHeight(25);
        imageView.setFitWidth(37);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        switch (value) {
            case "on" :
                button.getStyleClass().add(buttonName + "Time");
                break;
            case "off" :
                button.getStyleClass().remove(buttonName + "Time");
                break;
        }
    }

    private void initializeVolumeSlider() {
        volumeSlider.setValue(pisteService.getPisteCourante().getVolume() * 100);
        //Volume
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            float val = newValue.floatValue()/100;
            pisteService.getPisteCourante().setVolume(val);
        });

    }

    private void initializeListePistes() {
        liste_pistes.getItems().addAll(this.pisteService.getToutesLesPistes().keySet());
        liste_pistes.setValue(pisteService.getPisteCourante().getNomPreset());

        liste_pistes.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.pisteService.chargerPiste(newVal);
            updatePisteView(pisteService.getPisteCourante(), 0);
            stopButton.fire();
        });
    }
    public void updateListePistes() {
        liste_pistes.getItems().add(pisteService.getPisteCourante().getNomPreset());
        liste_pistes.setValue(pisteService.getPisteCourante().getNomPreset());
    }

    private void initializeListeInstruments() {
        liste_instruments.getItems().addAll(this.instrumentService.getAllInstruments().keySet());
        liste_instruments.setValue(this.instrumentService.getInstrumentCourant().getNom());

        liste_instruments.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.instrumentService.setInstrumentCourant(this.instrumentService.getInstrument(newVal));
        });
    }

    private void initializePisteView() {
        stepRows  = List.of(
        stepMinusFour,
        stepMinusThree,
        stepMinusTwo,
        stepMinusOne,
        currentStep,
        stepPlusOne,
        stepPlusTwo,
        stepPlusThree,
        stepPlusFour
        );
        updatePisteView(pisteService.getPisteCourante(), 0);
    }
    private void updatePisteView(Piste piste, int currentStepIndex) {
        int totalSteps = piste.getSequence().length;
        int centerIndex = 4; // currentStep

        for (int i = 0; i < stepRows.size(); i++) {
            int stepIndex =
                    (currentStepIndex + (i - centerIndex) + totalSteps) % totalSteps;

            HBox row = stepRows.get(i);

            Note note = piste.getSequence()[stepIndex];

            Label stepLabel = (Label) row.lookup(".step_label");
            Label noteLabel = (Label) row.lookup(".note_label");
            Label instrumentLabel = (Label) row.lookup(".inst_label");

            stepLabel.setText(String.format("%02d", stepIndex));
            noteLabel.setText(note != null ? note.getHauteur().toString().replace("Sharp", "♯") : "---");
            instrumentLabel.setText(note != null ? note.getInstrument().getNom() : "------");

            row.getStyleClass().remove("active");
            if (i == centerIndex) {
                row.getStyleClass().add("active");
            }

            if (stepIndex % 4 == 0) {
                if (!stepLabel.getStyleClass().contains("step_label_highlight")) {
                    stepLabel.getStyleClass().remove("step_label_base");
                    stepLabel.getStyleClass().add("step_label_highlight");
                }

            } else {
                if (!stepLabel.getStyleClass().contains("step_label_base")) {
                    stepLabel.getStyleClass().remove("step_label_highlight");
                    stepLabel.getStyleClass().add("step_label_base");
                }

            }
        }
    }

    private void initializePianoKeys() {
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
                        case COMMA: {
                            if (this.lectureService.getStatutLecture() == StatutLecture.EN_COURS){
                                break;
                            }
                            if (event.isShiftDown() && this.enregistrementService.getStatutRecord() == StatutRecord.EN_COURS){
                                this.enregistrementService.supprimerNote(this.pisteService.getPisteCourante(), this.lectureService.getStep());
                            }
                            this.lectureService.decrementerStep();
                        } break;
                        case SEMICOLON: {
                            if (this.lectureService.getStatutLecture() == StatutLecture.EN_COURS){
                                break;
                            }
                            if (event.isShiftDown() && this.enregistrementService.getStatutRecord() == StatutRecord.EN_COURS){
                                this.enregistrementService.supprimerNote(this.pisteService.getPisteCourante(), this.lectureService.getStep());
                            }
                            this.lectureService.incrementerStep();
                        }
                    }
                });

                C2.requestFocus();
            }
        });
    }


}
