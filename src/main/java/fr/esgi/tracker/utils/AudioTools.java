package fr.esgi.tracker.utils;

import fr.esgi.tracker.business.Note;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.Arrays;
/**
 * Classe utilitaire pour les opérations liées à l’audio.
 * Elle fournit des méthodes statiques pour le traitement
 * et la conversion des données audio.
 */

public class AudioTools {

    /**
     * Charge un fichier WAV et le convertit en tableau de valeurs flottantes.
     * Les données audio sont normalisées entre -1.0 et 1.0.
     */
    public static float[] getFloatArrayFromWav(String path) throws Exception {
        URL url = AudioTools.class.getResource(path);
        AudioInputStream in = AudioSystem.getAudioInputStream(url);
        AudioFormat fmt = in.getFormat();

        byte[] raw = in.readAllBytes();
        int samplesCount = raw.length / 2;
        float[] out = new float[samplesCount];

        for (int i = 0; i < samplesCount; i++) {
            int low = raw[i * 2] & 0xff;
            int high = raw[i * 2 + 1];
            int val = (high << 8) | low;
            out[i] = val / 32768f;
        }

        return out;
    }

    /**
     * Convertit un tableau de valeurs flottantes en données PCM 16 bits,
     * prêtes à être envoyées vers une ligne audio.
     */
    public static byte[] convertFloatArrayToPCM16Array(float[] mixArray) {
        byte[] pcm = new byte[mixArray.length * 2];
        for (int i = 0; i < mixArray.length; i++) {
            float f = Math.max(-1f, Math.min(1f, mixArray[i]));
            short s = (short) (f * 32767);
            pcm[i * 2] = (byte) (s & 0xff);
            pcm[i * 2 + 1] = (byte) ((s >> 8) & 0xff);
        }
        return pcm;
    }

    /**
     * Réinitialise un tableau de mixage audio
     * en mettant toutes les valeurs à zéro.
     */
    public static float[] clearMixArray(float[] mixArray){
        float[] mix = new float[mixArray.length];
        Arrays.fill(mix, 0);
        return mix;
    }

    /**
     * Calcule le ratio de pitch à appliquer à une note
     * en fonction de sa hauteur et de celle du sample d’origine.
     */
    public static float getPitchRatio(Note note) {
        return (float) (note.getHauteur().getFrequence() /
                note.getInstrument().getHauteurDuSample().getFrequence());
    }


}
