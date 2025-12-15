package fr.esgi.tracker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principale de l’application JavaFX.
 * Gère l’initialisation de la scène et le lancement
 * de l’interface graphique.
 */

public class App extends Application {

    private static Scene scene;

    /**
     * Démarre l’application JavaFX.
     * Initialise la scène principale, charge les ressources
     * graphiques et configure la fenêtre.
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("tracker"), 820,420 );
        stage.setScene(scene);
        stage.setTitle("Tracker");
        stage.setResizable(false);

        Font.loadFont(
                getClass().getResourceAsStream("/fr/esgi/tracker/assets/fonts/LCD.ttf"),
                14
        );

        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        stage.show();
    }

    /**
     * Modifie la racine de la scène courante
     * afin de changer l’écran affiché.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Charge un fichier FXML et retourne
     * le composant graphique correspondant.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Point d’entrée de l’application.
     * Lance le framework JavaFX.
     */
    public static void main(String[] args) {
        launch();
    }

}
