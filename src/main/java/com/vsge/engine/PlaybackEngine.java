package com.vsge.engine;

import com.vsge.audio.AudioService;
import com.vsge.audio.ImprovedMidiService;
import com.vsge.core.chord.Chord;
import com.vsge.core.progression.ChordProgression;
import com.vsge.style.PlayStyle;
import com.vsge.style.PlayStyle.NoteEvent;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Core playback coordination engine.
 * Manages timing and execution of musical sequences.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class PlaybackEngine {
    private static final Logger logger = Logger.getLogger(PlaybackEngine.class.getName());
    
    private final AudioService audioService;
    private final TempoController tempoController;
    private final ScheduledExecutorService scheduler;
    private volatile boolean isPlaying = false;
    private volatile boolean isPaused = false;
    
    public PlaybackEngine() {
        this.audioService = ImprovedMidiService.getInstance();
        this.tempoController = new TempoController();
        this.scheduler = Executors.newScheduledThreadPool(2);
    }
    
    /**
     * Plays a single chord with the specified style.
     * 
     * @param chord the chord to play
     * @param style the playing style
     * @param tempo the tempo in BPM
     */
    public void playChord(Chord chord, PlayStyle style, int tempo) {
        if (chord == null || style == null) {
            throw new IllegalArgumentException("Chord and style cannot be null");
        }
        
        tempoController.setTempo(tempo);
        List<NoteEvent> events = style.generatePattern(chord, 4, tempo);
        playNoteEvents(events);
    }
    
    /**
     * Plays a chord progression with the specified style.
     * 
     * @param progression the chord progression
     * @param style the playing style
     * @param tempo the tempo in BPM
     */
    public void playProgression(ChordProgression progression, PlayStyle style, int tempo) {
        if (progression == null || style == null) {
            throw new IllegalArgumentException("Progression and style cannot be null");
        }
        
        tempoController.setTempo(tempo);
        List<Chord> chords = progression.generateChords();
        
        long startTime = System.currentTimeMillis();
        long beatDuration = tempoController.getBeatDurationMs();
        
        for (int i = 0; i < chords.size(); i++) {
            Chord chord = chords.get(i);
            long chordStartTime = startTime + (i * progression.getBeatsPerChord() * beatDuration);
            
            // Schedule chord playback
            scheduler.schedule(() -> {
                if (!isPaused) {
                    List<NoteEvent> events = style.generatePattern(chord, 4, tempo);
                    playNoteEvents(events);
                }
            }, chordStartTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
    }
    
    /**
     * Plays a sequence of note events with proper timing.
     * 
     * @param events the note events to play
     */
    private void playNoteEvents(List<NoteEvent> events) {
        if (events == null || events.isEmpty()) {
            return;
        }
        
        long beatDuration = tempoController.getBeatDurationMs();
        
        for (NoteEvent event : events) {
            long startTime = (long)(event.getStartTime() * beatDuration);
            long duration = (long)(event.getDuration() * beatDuration);
            
            scheduler.schedule(() -> {
                if (!isPaused) {
                    audioService.playNote(
                        event.getNote().getMidiNumber(),
                        event.getVelocity(),
                        duration
                    );
                }
            }, startTime, TimeUnit.MILLISECONDS);
        }
    }
    
    /**
     * Starts playback.
     */
    public void start() {
        isPlaying = true;
        isPaused = false;
        logger.info("Playback started");
    }
    
    /**
     * Pauses playback.
     */
    public void pause() {
        isPaused = true;
        logger.info("Playback paused");
    }
    
    /**
     * Resumes playback.
     */
    public void resume() {
        isPaused = false;
        logger.info("Playback resumed");
    }
    
    /**
     * Stops playback and clears all scheduled events.
     */
    public void stop() {
        isPlaying = false;
        isPaused = false;
        audioService.stopAll();
        scheduler.shutdownNow();
        logger.info("Playback stopped");
    }
    
    /**
     * Checks if playback is currently active.
     * 
     * @return true if playing, false otherwise
     */
    public boolean isPlaying() {
        return isPlaying && !isPaused;
    }
    
    /**
     * Checks if playback is paused.
     * 
     * @return true if paused, false otherwise
     */
    public boolean isPaused() {
        return isPaused;
    }
    
    /**
     * Gets the current tempo.
     * 
     * @return tempo in BPM
     */
    public int getTempo() {
        return tempoController.getTempo();
    }
    
    /**
     * Sets the tempo.
     * 
     * @param tempo tempo in BPM
     */
    public void setTempo(int tempo) {
        tempoController.setTempo(tempo);
    }
    
    /**
     * Closes the playback engine and releases resources.
     */
    public void close() {
        stop();
        try {
            if (!scheduler.awaitTermination(2, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        logger.info("Playback engine closed");
    }
}
