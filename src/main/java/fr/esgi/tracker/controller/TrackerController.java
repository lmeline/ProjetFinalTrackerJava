package fr.esgi.tracker.controller;

import fr.esgi.tracker.business.Hauteur;
import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.business.Piste;
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
import javafx.scene.control.Button;
import fr.esgi.tracker.App;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TrackerController  {
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
    private final PianoController pianoController = new PianoController(this);
    private final ButtonController buttonController = new ButtonController(this);
    private final EnregistrementService enregistrementService = new EnregistrementServiceImpl();


    @FXML
    public void initialize() {
        this.pisteService.chargerToutesLesPistes();
        this.pisteService.chargerPiste("4onTheFloor");
        this.instrumentService.chargerTousLesInstruments();
        this.lectureService = new LectureServiceImpl(this.pisteService);

        NoteList.setCellValueFactory(cellData -> {
            Note note = cellData.getValue();
            String label = (note != null) ? note.toString() : "-- | ----";
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
        this.audioService.jouerNote(note, 1.0F);
        System.out.println(btn.getId());
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
        TrackerList.setItems(notes);
        highlightStep(0);
    }

    private void highlightStep(int step) {
        if (!TrackerList.getItems().isEmpty()) {
            TrackerList.getSelectionModel().select(step); // sélectionne le premier élément
            TrackerList.scrollTo(step); // scroll jusqu’au premier élément si nécessaire
        }
    }
}
