package fr.esgi.tracker.utils;

import fr.esgi.tracker.business.Instrument;
import fr.esgi.tracker.business.Note;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class AudioPlayer {

    private static volatile boolean stop;
    private static volatile SourceDataLine currentLine = null;

    public static void playSound(Note note, float volume) {
        new Thread(() -> {
            try {
                float pitchFactor = (float) note.getHauteur().getFrequence() /
                        (float) note.getInstrument().getHauteurDuSample().getFrequence();

                InputStream is = AudioPlayer.class.getResourceAsStream(note.getInstrument().getCheminFichier());
                if (is == null) {
                    System.err.println("Ressource introuvable : " + note.getInstrument().getCheminFichier());
                    return;
                }

                AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
                AudioFormat format = ais.getFormat();

                // Ouvre une ligne audio DANS LE FORMAT ORIGINAL
                SourceDataLine line = AudioSystem.getSourceDataLine(format);
                line.open(format);
                //setVolume(line, volume);
                line.start();

                int frameSize = format.getFrameSize();
                int sampleRate = (int) format.getSampleRate();

                byte[] sample = ais.readAllBytes(); // On charge tout (ok pour petits samples)
                int totalFrames = sample.length / frameSize;

                double pos = 0;

                byte[] frameBuffer = new byte[frameSize];

                while (pos < totalFrames) {
                    int frameIndex = (int) pos;

                    // Copie d'une frame
                    System.arraycopy(sample, frameIndex * frameSize, frameBuffer, 0, frameSize);
                    line.write(frameBuffer, 0, frameSize);

                    pos += pitchFactor; // <-- pitch réel ici (plus grand = plus aigu)
                }

                line.drain();
                line.stop();
                line.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}
