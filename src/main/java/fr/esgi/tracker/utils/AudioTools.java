package fr.esgi.tracker.utils;

import fr.esgi.tracker.business.Note;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.Arrays;

public class AudioTools {

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

    public static float[] clearMixArray(float[] mixArray){
        float[] mix = new float[mixArray.length];
        Arrays.fill(mix, 0);
        return mix;
    }

    public static float getPitchRatio(Note note) {
        return (float) (note.getHauteur().getFrequence() /
                note.getInstrument().getHauteurDuSample().getFrequence());
    }


}
