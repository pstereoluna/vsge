package com.vsge.engine.playback;

import com.vsge.audio.AudioService;
import com.vsge.audio.ImprovedMidiService;
import com.vsge.music.chord.Chord;
import com.vsge.music.progression.ChordProgression;
import com.vsge.music.rhythm.RhythmPattern;
import com.vsge.music.rhythm.RhythmPatternFactory;
import com.vsge.music.rhythm.RhythmPattern.RhythmEvent;
import com.vsge.engine.timing.TempoController;
import com.vsge.engine.humanizer.HumanizationSettings;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Enhanced playback engine with rhythm patterns and humanization.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class EnhancedPlaybackEngine {
    private static final Logger logger = Logger.getLogger(EnhancedPlaybackEngine.class.getName());
    
    private final AudioService audioService;
    private final TempoController tempoController;
    private final ScheduledExecutorService scheduler;
    private final HumanizationSettings humanizationSettings;
    private volatile boolean isPlaying = false;
    private volatile boolean isPaused = false;
    
    public EnhancedPlaybackEngine() {
        this.audioService = ImprovedMidiService.getInstance();
        this.tempoController = new TempoController();
        this.scheduler = Executors.newScheduledThreadPool(4);
        this.humanizationSettings = new HumanizationSettings();
    }
    
    /**
     * Plays a chord with the specified rhythm pattern and humanization.
     * 
     * @param chord the chord to play
     * @param styleName the style name (folk, pop, jazz, rock)
     * @param tempo the tempo in BPM
     */
    public void playChord(Chord chord, String styleName, int tempo) {
        if (chord == null || styleName == null) {
            throw new IllegalArgumentException("Chord and style cannot be null");
        }
        
        try {
            RhythmPattern pattern = RhythmPatternFactory.create(styleName);
            playChordWithPattern(chord, pattern, tempo);
        } catch (IllegalArgumentException e) {
            logger.warning("Unknown style: " + styleName + ", using default");
            RhythmPattern defaultPattern = RhythmPatternFactory.create("folk");
            playChordWithPattern(chord, defaultPattern, tempo);
        }
    }
    
    /**
     * Plays a chord with a specific rhythm pattern.
     * 
     * @param chord the chord to play
     * @param pattern the rhythm pattern
     * @param tempo the tempo in BPM
     */
    public void playChordWithPattern(Chord chord, RhythmPattern pattern, int tempo) {
        tempoController.setTempo(tempo);
        List<RhythmEvent> events = pattern.generatePattern(chord, 4, tempo);
        playRhythmEvents(events);
    }
    
    /**
     * Plays a chord progression with rhythm patterns.
     * 
     * @param progression the chord progression
     * @param styleName the style name
     * @param tempo the tempo in BPM
     */
    public void playProgression(ChordProgression progression, String styleName, int tempo) {
        if (progression == null || styleName == null) {
            throw new IllegalArgumentException("Progression and style cannot be null");
        }
        
        try {
            RhythmPattern pattern = RhythmPatternFactory.create(styleName);
            List<Chord> chords = progression.generateChords();
            
            long startTime = System.currentTimeMillis();
            long beatDuration = tempoController.getBeatDurationMs();
            
            for (int i = 0; i < chords.size(); i++) {
                Chord chord = chords.get(i);
                long chordStartTime = startTime + (i * progression.getBeatsPerChord() * beatDuration);
                
                // Schedule chord playback
                scheduler.schedule(() -> {
                    if (!isPaused) {
                        List<RhythmEvent> events = pattern.generatePattern(chord, 4, tempo);
                        playRhythmEvents(events);
                    }
                }, chordStartTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            }
        } catch (IllegalArgumentException e) {
            logger.warning("Unknown style: " + styleName + ", using default");
            RhythmPattern defaultPattern = RhythmPatternFactory.create("folk");
            // Continue with default pattern...
        }
    }
    
    /**
     * Plays rhythm events with humanization applied.
     * 
     * @param events the rhythm events to play
     */
    private void playRhythmEvents(List<RhythmEvent> events) {
        if (events == null || events.isEmpty()) {
            return;
        }
        
        long beatDuration = tempoController.getBeatDurationMs();
        
        for (RhythmEvent event : events) {
            // Apply humanization
            double finalStartTime = applyHumanization(event.getFinalStartTime());
            long startTimeMs = (long)(finalStartTime * beatDuration);
            
            long durationMs = (long)(event.getDuration() * beatDuration);
            int finalVelocity = applyVelocityHumanization(event.getFinalVelocity());
            
            // Apply swing if enabled
            if (humanizationSettings.isSwingEnabled()) {
                startTimeMs = applySwing(startTimeMs, beatDuration);
            }
            
            scheduler.schedule(() -> {
                if (!isPaused) {
                    audioService.playNote(
                        event.getNote().getMidiNumber(),
                        finalVelocity,
                        durationMs
                    );
                }
            }, startTimeMs, TimeUnit.MILLISECONDS);
        }
    }
    
    /**
     * Applies timing humanization to a start time.
     * 
     * @param startTime the original start time
     * @return humanized start time
     */
    private double applyHumanization(double startTime) {
        if (!humanizationSettings.isTimingHumanizationEnabled()) {
            return startTime;
        }
        
        double offset = (Math.random() - 0.5) * humanizationSettings.getTimingOffsetRange();
        return startTime + offset;
    }
    
    /**
     * Applies velocity humanization to a velocity value.
     * 
     * @param velocity the original velocity
     * @return humanized velocity
     */
    private int applyVelocityHumanization(int velocity) {
        if (!humanizationSettings.isVelocityHumanizationEnabled()) {
            return velocity;
        }
        
        int variation = (int)((Math.random() - 0.5) * humanizationSettings.getVelocityVariationRange());
        return Math.max(20, Math.min(127, velocity + variation));
    }
    
    /**
     * Applies swing feel to timing.
     * 
     * @param startTimeMs the start time in milliseconds
     * @param beatDurationMs the beat duration in milliseconds
     * @return swung start time
     */
    private long applySwing(long startTimeMs, long beatDurationMs) {
        double swingRatio = humanizationSettings.getSwingRatio();
        if (swingRatio <= 0.0) {
            return startTimeMs;
        }
        
        // Apply swing to off-beats (every other 8th note)
        double beatPosition = (double)startTimeMs / beatDurationMs;
        if (Math.floor(beatPosition * 2) % 2 == 1) { // Off-beat
            return startTimeMs + (long)(swingRatio * beatDurationMs * 0.25);
        }
        
        return startTimeMs;
    }
    
    /**
     * Sets humanization settings for a specific style.
     * 
     * @param styleName the style name
     */
    public void setStyleHumanization(String styleName) {
        switch (styleName.toLowerCase()) {
            case "folk":
                copySettings(HumanizationSettings.folkPreset());
                break;
            case "pop":
                copySettings(HumanizationSettings.popPreset());
                break;
            case "jazz":
                copySettings(HumanizationSettings.jazzPreset());
                break;
            case "rock":
                copySettings(HumanizationSettings.rockPreset());
                break;
            default:
                logger.warning("Unknown style for humanization: " + styleName);
        }
    }
    
    /**
     * Copies settings from another HumanizationSettings object.
     * 
     * @param settings the settings to copy
     */
    private void copySettings(HumanizationSettings settings) {
        humanizationSettings.setTimingOffsetRange(settings.getTimingOffsetRange());
        humanizationSettings.setTimingHumanizationEnabled(settings.isTimingHumanizationEnabled());
        humanizationSettings.setVelocityVariationRange(settings.getVelocityVariationRange());
        humanizationSettings.setVelocityHumanizationEnabled(settings.isVelocityHumanizationEnabled());
        humanizationSettings.setDurationVariationRange(settings.getDurationVariationRange());
        humanizationSettings.setDurationHumanizationEnabled(settings.isDurationHumanizationEnabled());
        humanizationSettings.setStrumDelay(settings.getStrumDelay());
        humanizationSettings.setStrummingEffectEnabled(settings.isStrummingEffectEnabled());
        humanizationSettings.setSwingRatio(settings.getSwingRatio());
        humanizationSettings.setSwingEnabled(settings.isSwingEnabled());
    }
    
    /**
     * Gets the current humanization settings.
     * 
     * @return the humanization settings
     */
    public HumanizationSettings getHumanizationSettings() {
        return humanizationSettings;
    }
    
    // Standard playback control methods
    public void start() {
        isPlaying = true;
        isPaused = false;
        logger.info("Enhanced playback started");
    }
    
    public void pause() {
        isPaused = true;
        logger.info("Enhanced playback paused");
    }
    
    public void resume() {
        isPaused = false;
        logger.info("Enhanced playback resumed");
    }
    
    public void stop() {
        isPlaying = false;
        isPaused = false;
        audioService.stopAll();
        scheduler.shutdownNow();
        logger.info("Enhanced playback stopped");
    }
    
    public boolean isPlaying() {
        return isPlaying && !isPaused;
    }
    
    public boolean isPaused() {
        return isPaused;
    }
    
    public int getTempo() {
        return tempoController.getTempo();
    }
    
    public void setTempo(int tempo) {
        tempoController.setTempo(tempo);
    }
    
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
        logger.info("Enhanced playback engine closed");
    }
}
