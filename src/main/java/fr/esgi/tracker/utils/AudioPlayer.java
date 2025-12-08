package fr.esgi.tracker.utils;

import fr.esgi.tracker.business.Instrument;
import fr.esgi.tracker.business.Note;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class AudioPlayer {

    private static volatile boolean stop;
    private static volatile SourceDataLine currentLine = null;

    public static synchronized void playSound(Note note) throws Exception {
        // Stop playback précédent
        /*stop = true;
        if (currentLine != null) {
            currentLine.stop();
            currentLine.close();
        }

        // Démarre nouveau playback
        stop = false;*/
        float speed = (float) note.getHauteur().getFrequence() / (float) note.getInstrument().getHauteurDuSample().getFrequence();
        InputStream is = AudioPlayer.class.getResourceAsStream(note.getInstrument().getCheminFichier());
        if (is == null) throw new IllegalArgumentException("Ressource introuvable : " + note.getInstrument().getCheminFichier());

        AudioInputStream in = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
        AudioFormat f = in.getFormat();

        AudioFormat pitched = new AudioFormat(
                f.getEncoding(),
                f.getSampleRate() * speed,
                f.getSampleSizeInBits(),
                f.getChannels(),
                f.getFrameSize(),
                f.getFrameRate() * speed,
                f.isBigEndian()
        );

        AudioInputStream converted = AudioSystem.getAudioInputStream(pitched, in);
        currentLine = AudioSystem.getSourceDataLine(pitched);

        currentLine.open(pitched);
        currentLine.start();

        byte[] buf = new byte[4096];
        int n;
        while (!stop && (n = converted.read(buf)) != -1) {
            currentLine.write(buf, 0, n);
        }

        currentLine.drain();
        currentLine.stop();
        currentLine.close();
        currentLine = null;

    }
}
