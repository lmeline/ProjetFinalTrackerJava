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

public class PisteJsonManager {

    private static final Path dossierPresets = Paths.get(System.getProperty("user.home"), "ProjetFinalTracker", "presets");

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

    public static void sauvegarderPisteEnJson(Piste piste) {
        try {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .create();

            Path fichierJson = dossierPresets.resolve(piste.getNomPreset() + ".json");

            String pisteJson = gson.toJson(piste);

            Files.writeString(fichierJson, pisteJson);

            System.out.println("piste" + piste.getNomPreset() + "sauvegardée !");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Piste chargerPisteDepuisJson(String nom) throws Exception {
        Path jsonFile = dossierPresets.resolve(nom + ".json");
        if (!Files.exists(jsonFile)) {
            throw new FileNotFoundException("Fichier introuvable : " + jsonFile);
        }
        String jsonString = Files.readString(jsonFile);
        return new Gson().fromJson(jsonString, Piste.class);
    }
}
