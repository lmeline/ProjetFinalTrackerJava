package fr.esgi.tracker.controller;

import fr.esgi.tracker.business.*;
import fr.esgi.tracker.observer.LectureObserver;
import fr.esgi.tracker.services.*;
import fr.esgi.tracker.services.impl.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TrackerController implements LectureObserver {
    private LectureService lectureService;
    private PisteService pisteService = new PisteServiceImpl();
    private InstrumentService instrumentService = new InstrumentServiceImpl();
    private final EnregistrementService enregistrementService = new EnregistrementServiceImpl();
    private AudioService audioService;


    @FXML private Button playButton;
    @FXML private Button pauseButton;
    @FXML private Button stopButton;
    @FXML private Button recordButton;
    @FXML private Button saveButton;
    @FXML Slider volumeSlider;

    @FXML private ComboBox<String> piste_loader;
    @FXML private ComboBox<String> instrumentList;



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

    @FXML
    private void ButtonPlayPressed() {
        System.out.println("pressed");
        if (lectureService.getStatutLecture() != StatutLecture.EN_COURS) {
            toggleButtonIcon(stopButton, "off");
            toggleButtonIcon(pauseButton, "off");
            toggleButtonIcon(playButton, "on");
        }
        buttonController.ButtonPlayPressed();
    }

    @FXML
    private void ButtonPausePressed() {
        System.out.println("pressed");
        if (lectureService.getStatutLecture() == StatutLecture.EN_COURS) {
            toggleButtonIcon(stopButton, "off");
            toggleButtonIcon(pauseButton, "on");
            toggleButtonIcon(playButton, "off");
        }
        buttonController.ButtonPausePressed();
    }

    @FXML
    private void ButtonStopPressed() {
        if (lectureService.getStatutLecture() != StatutLecture.ARRETE) {
            toggleButtonIcon(stopButton, "on");
            toggleButtonIcon(pauseButton, "off");
            toggleButtonIcon(playButton, "off");
        }
        buttonController.ButtonStopPressed();

    }

    @FXML
    private void ButtonRecordPressed() {
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


    private final PianoController pianoController = new PianoController(this);
    private final ButtonController buttonController = new ButtonController(this);


    @FXML
    public void initialize() {
        // Initialisation des services

        //PisteService
        this.pisteService.chargerToutesLesPistes();
        this.pisteService.chargerPiste("hells_bells");

        //InstrumentService
        this.instrumentService.chargerTousLesInstruments();
        this.instrumentService.setInstrumentCourant(this.instrumentService.getInstrument("piano"));

        //AudioService
        this.audioService = new AudioServiceImpl(this.instrumentService);
        this.audioService.loadAudioClips();

        //LectureService
        this.lectureService = new LectureServiceImpl(this.pisteService, this.audioService);
        this.lectureService.addObserver(this);

        this.initializeListeners();
        this.toggleButtonIcon(stopButton, "on");

        this.initializePisteView();



        instrumentList.getItems().addAll(this.instrumentService.getAllInstruments().keySet());
        instrumentList.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.instrumentService.setInstrumentCourant(this.instrumentService.getInstrument(newVal));
        });
        instrumentList.setValue(this.instrumentService.getInstrumentCourant().getNom());


        piste_loader.getItems().addAll(this.pisteService.getToutesLesPistes().keySet());

        piste_loader.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.pisteService.chargerPiste(newVal);
            updatePisteView(pisteService.getPisteCourante(), 0);
            this.lectureService.stop();
        });
        piste_loader.setValue(pisteService.getPisteCourante().getNomPreset());


        pianoController.initKeys(
                C2, CSharp2, D2, DSharp2, E2, F2, FSharp2,
                G2, GSharp2, A2, ASharp2, B2,
                C3, CSharp3, D3, DSharp3, E3, F3,
                FSharp3, G3, GSharp3, A3, ASharp3, B3
        );

    }

    public LectureService getLectureService() {
        return lectureService;
    }

    public InstrumentService getInstrumentService() {
        return instrumentService;
    }

    @FXML
    public void noteTriggered(ActionEvent e) {
        Button btn = (Button) e.getSource();
        Note note = new Note(Hauteur.valueOf(btn.getId()), instrumentService.getInstrumentCourant(), 1.0f);

        if (enregistrementService.getStatutRecord() == StatutRecord.EN_COURS) {
            this.enregistrementService.EnregistrerNote(note, pisteService, lectureService.getStep());
            if (lectureService.getStatutLecture() != StatutLecture.EN_COURS) {
                lectureService.incrementerStep();
                this.audioService.jouerNote(note, pisteService.getPisteCourante().getVolume());
            }
        } else {
            this.audioService.jouerNote(note, pisteService.getPisteCourante().getVolume());
        }
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


    @Override
    public void onStepChange(int step) {
        Platform.runLater(()-> updatePisteView(pisteService.getPisteCourante(), step));
    }

    public EnregistrementService getEnregistrementService() {
        return enregistrementService;
    }

    public PisteService getPisteService() {
        return pisteService;
    }

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

    private void initializeListeners() {
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            float val = newValue.floatValue()/100;
            pisteService.getPisteCourante().setVolume(val);
        });
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

    public void updatePisteLoader() {
        piste_loader.getItems().add(pisteService.getPisteCourante().getNomPreset());
        piste_loader.setValue(pisteService.getPisteCourante().getNomPreset());
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

    }


    public void updatePisteView(Piste piste, int currentStepIndex) {
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
        }
    }


}
