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

    }


}
