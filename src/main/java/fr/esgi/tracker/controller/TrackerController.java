package fr.esgi.tracker.controller;

import fr.esgi.tracker.business.*;
import fr.esgi.tracker.observer.LectureObserver;
import fr.esgi.tracker.services.*;
import fr.esgi.tracker.services.impl.*;
import fr.esgi.tracker.utils.AudioPlayer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import fr.esgi.tracker.App;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TrackerController implements LectureObserver {
    private LectureService lectureService;
    private PisteService pisteService = new PisteServiceImpl();
    private InstrumentService instrumentService = new InstrumentServiceImpl();
    private AudioService audioService = new AudioServiceImpl();


    @FXML private Button playButton;
    @FXML private Button pauseButton;
    @FXML private Button stopButton;
    @FXML private TableView<Note> TrackerList;
    @FXML private TableColumn<Note, String> NoteList;
    @FXML private ComboBox<String> piste_loader;

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
        buttonController.ButtonPlayPressed();
    }

    @FXML
    private void ButtonPausePressed() {
        buttonController.ButtonPausePressed();
    }

    @FXML
    private void ButtonStopPressed() {
        buttonController.ButtonStopPressed();
    }

    @FXML
    private void ButtonRecordPressed() {
        if (enregistrementService.getStatutRecord() == StatutRecord.ARRETE)
            enregistrementService.setStatutRecord(StatutRecord.EN_COURS);
        else {
            enregistrementService.setStatutRecord(StatutRecord.ARRETE);
        }
        System.out.println(enregistrementService.getStatutRecord());
    }


    private final PianoController pianoController = new PianoController(this);
    private final ButtonController buttonController = new ButtonController(this);
    private final EnregistrementService enregistrementService = new EnregistrementServiceImpl();


    @FXML
    public void initialize() {
        this.pisteService.chargerToutesLesPistes();
        this.pisteService.chargerPiste("4onTheFloor");
        this.instrumentService.chargerTousLesInstruments();
        this.lectureService = new LectureServiceImpl(this.pisteService);
        this.lectureService.addObserver(this);

        // 1) Fournit le texte d'origine
        NoteList.setCellValueFactory(cellData -> {
            Note note = cellData.getValue();
            String label = (note != null) ? note.toString() : "---------";
            return new ReadOnlyStringWrapper(label);
        });

        piste_loader.getItems().addAll(this.pisteService.getToutesLesPistes().keySet());

        piste_loader.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.pisteService.chargerPiste(newVal);
            updateTrackerList();
            this.lectureService.stop();
        });
        piste_loader.setValue("4onTheFloor");


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
        Note note = new Note(Hauteur.valueOf(btn.getId()), instrumentService.getInstrument("piano"), 1.0f);
        //this.audioService.jouerNote(note, 1.0F);
        System.out.println(btn.getId());
        if (enregistrementService.getStatutRecord() == StatutRecord.EN_COURS) {
            this.enregistrementService.EnregistrerNote(note, pisteService, lectureService.getStep()-1);
            updateTrackerList();
            if (lectureService.getStatutLecture() != StatutLecture.EN_COURS) {
                lectureService.incrementerStep();
            }
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

    private void updateTrackerList() {

        ObservableList<Note> notes = FXCollections.observableArrayList(pisteService.getPisteCourante().getSequence());
        notes.add(0, null);
        notes.add(0, null);
        notes.add(0, null);
        notes.add(0, null);
        notes.add(0, null);
        notes.add(0, null);
        notes.add(null);
        notes.add(null);
        notes.add(null);
        notes.add(null);
        notes.add(null);
        notes.add(null);
        notes.add(null);

        TrackerList.setItems(notes);

        highlightStep(lectureService.getStep()+ 6);
    }

    private void highlightStep(int step) {
        if (!TrackerList.getItems().isEmpty()) {
            TrackerList.getSelectionModel().select(step); // sélectionne le premier élément
            TrackerList.scrollTo(step);
            NoteList.setCellFactory(col -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setStyle("");
                        setText(null);
                        return;
                    }

                    int rowIndex = getIndex();

                    // Ton highlight dynamique
                    if (rowIndex == step + 6) {
                        setStyle("-fx-background-color: red; -fx-text-fill: white;");
                    } else {
                        setStyle("");
                    }

                    setText(item);
                }
            });// scroll jusqu’au premier élément si nécessaire
        }
    }

    @Override
    public void onStepChange(int step) {
        System.out.println(step);
        highlightStep(step);

    }
}
