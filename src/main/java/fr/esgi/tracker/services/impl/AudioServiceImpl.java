package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.services.AudioService;
import fr.esgi.tracker.utils.AudioPlayer;
import javafx.scene.media.AudioClip;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class AudioServiceImpl implements AudioService {
    private volatile SourceDataLine currentLine; // ligne en cours
    @Override
    public void jouerNote(Note note, float volume) {
        /*try {
            InputStream is = getClass().getResourceAsStream(note.getInstrument().getCheminFichier());
            if (is == null) throw new IllegalArgumentException("Fichier audio introuvable");

            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            AudioFormat format = ais.getFormat();

            // Stop la note précédente si elle est en cours
            if (currentLine != null && currentLine.isOpen()) {
                currentLine.stop();
                currentLine.close();
            }

            // Création de la nouvelle ligne
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, format));
            currentLine = line; // stocker la ligne actuelle

            line.open(format);
            if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gain = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = 20f * (float) Math.log10(volume);
                gain.setValue(dB);
            }

            line.start();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = ais.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            line.drain();
            line.stop();
            line.close();
            ais.close();

            // La note est terminée, on supprime la référence
            if (currentLine == line) currentLine = null;

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            AudioPlayer.playSound(note);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    private double setHauteur(Note note) {
        return note.getHauteur().getFrequence() / note.getInstrument().getHauteurDuSample().getFrequence();
    }
}
