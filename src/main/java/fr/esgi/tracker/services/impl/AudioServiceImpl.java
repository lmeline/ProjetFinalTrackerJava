package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Hauteur;
import fr.esgi.tracker.business.Instrument;
import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.services.AudioService;
import fr.esgi.tracker.services.InstrumentService;
import fr.esgi.tracker.utils.AudioPlayer;
import javafx.scene.media.AudioClip;

import javax.sound.sampled.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


import javax.sound.sampled.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class AudioServiceImpl implements AudioService {

    private final InstrumentService instrumentService;

    // Map<Instrument, Map<Hauteur, float[]>>
    private Map<String, EnumMap<Hauteur, float[]>> samples = new HashMap<>();

    // Liste de "voix" en cours : Object[] { float[] data, Float position, Float volume, Float speed }
    private final List<Object[]> voices = Collections.synchronizedList(new ArrayList<>());

    private SourceDataLine line;
    private boolean running = false;
    private final int bufferSize = 512;
    private final float[] mix = new float[bufferSize];

    public AudioServiceImpl(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;

        try {
            AudioFormat fmt = new AudioFormat(44100, 16, 1, true, false);
            line = AudioSystem.getSourceDataLine(fmt);
            int bufferSizeBytes = 4096; // ou 8192 pour un peu plus de sécurité
            line.open(fmt, bufferSizeBytes);
            line.start();

            running = true;
            Thread audioThread = new Thread(this::audioLoop, "AudioEngine");
            audioThread.setPriority(Thread.MAX_PRIORITY); // PRIORITÉ MAX pour réduire la latence
            audioThread.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Précharge tous les samples en RAM dans Map<String, EnumMap<Hauteur, float[]>>
     */
    public void loadAudioClips() {
        System.out.println("Début chargement samples...");

        for (Instrument is : instrumentService.getAllInstruments().values()) {
            EnumMap<Hauteur, float[]> hauteurMap = new EnumMap<>(Hauteur.class);

            for (Hauteur h : Hauteur.values()) {
                try {
                    float[] data = loadWavToFloatArray(getClass().getResource(is.getCheminFichier()));
                    hauteurMap.put(h, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            samples.put(is.getNom(), hauteurMap);
        }

        System.out.println("Samples chargés !");
    }

    /**
     * Jouer une note avec volume et pitch (ratio)
     */
    @Override
    public void jouerNote(Note note, float volume) {
        EnumMap<Hauteur, float[]> map = samples.get(note.getInstrument().getNom());
        if (map == null) return;

        float[] data = map.get(note.getHauteur());
        if (data == null) return;

        float pitchRatio = (float) (note.getHauteur().getFrequence() /
                note.getInstrument().getHauteurDuSample().getFrequence());

        Object[] voice = new Object[]{data, 0f, volume, pitchRatio};
        voices.add(voice);
    }

    /**
     * Convertit un fichier WAV en float[]
     */
    private float[] loadWavToFloatArray(URL url) throws Exception {
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
     * Boucle audio continue : mixage et écriture vers SourceDataLine
     */
    private void audioLoop() {

        while (running) {
            Arrays.fill(mix, 0f);

            synchronized (voices) {
                Iterator<Object[]> it = voices.iterator();
                while (it.hasNext()) {
                    Object[] v = it.next();
                    float[] data = (float[]) v[0];
                    float position = (float) v[1];
                    float volume = (float) v[2];
                    float speed = (float) v[3];

                    for (int i = 0; i < bufferSize; i++) {
                        int idx = (int) position;
                        if (idx >= data.length) {
                            it.remove(); // OK maintenant
                            break;
                        }
                        mix[i] += data[idx] * volume;
                        position += speed;
                    }

                    v[1] = position;
                }
            }

            // Convertir float -> PCM16
            byte[] pcm = new byte[bufferSize * 2];
            for (int i = 0; i < bufferSize; i++) {
                float f = Math.max(-1f, Math.min(1f, mix[i]));
                short s = (short) (f * 32767);
                pcm[i * 2] = (byte) (s & 0xff);
                pcm[i * 2 + 1] = (byte) ((s >> 8) & 0xff);
            }

            line.write(pcm, 0, pcm.length);
        }
    }

    /**
     * Arrêter l’audio proprement
     */
    public void stopAudio() {
        running = false;
        if (line != null) {
            line.drain();
            line.close();
        }
    }
}

