package fr.esgi.tracker.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.esgi.tracker.business.Piste;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Classe utilitaire de gestion des pistes au format JSON.
 * Permet la lecture et l’écriture des données des pistes
 * sur le système de fichiers.
 */

public class PisteJsonManager {

    /**
     * Dossier contenant les fichiers JSON des pistes.
     * Il est créé dans le répertoire personnel de l’utilisateur.
     */
    private static final Path dossierPresets = Paths.get(System.getProperty("user.home"), "ProjetFinalTracker", "presets");

    /**
     * Charge l’ensemble des pistes stockées au format JSON
     * depuis le dossier des presets.
     */
    public static Map<String, Piste> chargerToutesLesPistes() {
        Map<String, Piste> pistes = new HashMap<>();
        for (File fichier : dossierPresets.toFile().listFiles()) {
            if (fichier.isFile() && fichier.getName().endsWith(".json")) {
                try {
                    Piste piste = chargerPisteDepuisJson(fichier.getName().replace(".json", ""));
                    pistes.put(piste.getNomPreset(), piste);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return pistes;
    }

    /**
     * Initialise le dossier de sauvegarde des pistes.
     * Si le dossier est vide, les pistes fournies
     * sont automatiquement sauvegardées.
     */
    public static void initializeDirectory(List<Piste> pistes) {
        try {
            if (!Files.exists(dossierPresets)) {
                Files.createDirectories(dossierPresets);
            }

            if (Files.list(dossierPresets).findAny().isEmpty()) {
                for (Piste piste : pistes) {
                    sauvegarderPisteEnJson(piste);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sauvegarde une piste dans un fichier JSON
     * au sein du dossier des presets.
     */
    public static void sauvegarderPisteEnJson(Piste piste) {
        try {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .create();

            Path fichierJson = dossierPresets.resolve(piste.getNomPreset() + ".json");

            String pisteJson = gson.toJson(piste);

            Files.writeString(fichierJson, pisteJson);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge une piste depuis un fichier JSON
     * identifié par son nom.
     */
    public static Piste chargerPisteDepuisJson(String nom) throws Exception {
        Path jsonFile = dossierPresets.resolve(nom + ".json");
        if (!Files.exists(jsonFile)) {
            throw new FileNotFoundException("Fichier introuvable : " + jsonFile);
        }
        String jsonString = Files.readString(jsonFile);
        return new Gson().fromJson(jsonString, Piste.class);
    }
}
