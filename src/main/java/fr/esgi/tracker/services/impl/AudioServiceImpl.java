package fr.esgi.tracker.services.impl;

import fr.esgi.tracker.business.Hauteur;
import fr.esgi.tracker.business.Instrument;
import fr.esgi.tracker.business.Note;
import fr.esgi.tracker.services.AudioService;
import fr.esgi.tracker.services.InstrumentService;
import fr.esgi.tracker.utils.AudioTools;

import javax.sound.sampled.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;


import java.util.*;

public class AudioServiceImpl implements AudioService {

    private final InstrumentService instrumentService;
    private Map<String, EnumMap<Hauteur, float[]>> samples = new HashMap<>();
    private final List<Object[]> voices = Collections.synchronizedList(new ArrayList<>());

    private SourceDataLine line;
    private boolean running = false;
    private final int bufferSize = 512;
    private float[] mixer = new float[bufferSize];

    public AudioServiceImpl(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
        running = true;
        this.initializeSourceDataLine();
        this.startAudioLoop();
    }

    private void startAudioLoop() {
        Thread audioThread = new Thread(this::audioLoop, "AudioEngine");
        audioThread.setPriority(Thread.MAX_PRIORITY);
        audioThread.start();
    }

    private void initializeSourceDataLine(){
        try {
            AudioFormat fmt = new AudioFormat(44100, 16, 1, true, false);
            line = AudioSystem.getSourceDataLine(fmt);
            int bufferSizeBytes = 4096; // ou 8192 pour un peu plus de sécurité
            line.open(fmt, bufferSizeBytes);
            line.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Précharge tous les samples en RAM dans Map<String, EnumMap<Hauteur, float[]>>
     */
    public void loadSamples() {
        System.out.println("Début chargement samples...");

        for (Instrument is : instrumentService.getAllInstruments().values()) {
            EnumMap<Hauteur, float[]> hauteurMap = new EnumMap<>(Hauteur.class);

            for (Hauteur h : Hauteur.values()) {
                try {
                    float[] data = AudioTools.getFloatArrayFromWav(is.getCheminFichier());
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
        float[] data = samples.get(note.getInstrument().getNom()).get(note.getHauteur());

        Object[] voice = new Object[]{data, 0f, volume, AudioTools.getPitchRatio(note)};

        voices.add(voice);
    }

    /**
     * Boucle audio continue : mixage et écriture vers SourceDataLine
     */
    private void audioLoop() {
        while (running) {
            mixer = AudioTools.clearMixArray(mixer);

            synchronized (voices) {
                Iterator<Object[]> it = voices.iterator();
                while (it.hasNext()) {
                    Object[] voice = it.next();
                    float[] data = (float[]) voice[0];
                    float position = (float) voice[1];
                    float volume = (float) voice[2];
                    float speed = (float) voice[3];

                    for (int i = 0; i < bufferSize; i++) {
                        int idx = (int) position;
                        if (idx >= data.length) {
                            it.remove(); // OK maintenant
                            break;
                        }
                        mixer[i] += data[idx] * volume;
                        position += speed;
                    }

                    voice[1] = position;
                }
            }

            byte[] pcm = AudioTools.convertFloatArrayToPCM16Array(mixer);
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

