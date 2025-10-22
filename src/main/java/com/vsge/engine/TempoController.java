package com.vsge.engine;

/**
 * Manages tempo and timing calculations for playback.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class TempoController {
    private static final int DEFAULT_TEMPO = 120;
    private static final int MIN_TEMPO = 60;
    private static final int MAX_TEMPO = 200;
    
    private int tempo;
    
    public TempoController() {
        this.tempo = DEFAULT_TEMPO;
    }
    
    public TempoController(int tempo) {
        setTempo(tempo);
    }
    
    /**
     * Sets the tempo in beats per minute.
     * 
     * @param tempo the tempo in BPM
     * @throws IllegalArgumentException if tempo is out of range
     */
    public void setTempo(int tempo) {
        if (tempo < MIN_TEMPO || tempo > MAX_TEMPO) {
            throw new IllegalArgumentException(
                "Tempo must be between " + MIN_TEMPO + " and " + MAX_TEMPO + " BPM"
            );
        }
        this.tempo = tempo;
    }
    
    /**
     * Gets the current tempo.
     * 
     * @return tempo in BPM
     */
    public int getTempo() {
        return tempo;
    }
    
    /**
     * Calculates the duration of one beat in milliseconds.
     * 
     * @return beat duration in milliseconds
     */
    public long getBeatDurationMs() {
        return 60000L / tempo; // 60 seconds * 1000 ms / BPM
    }
    
    /**
     * Calculates the duration of a measure in milliseconds.
     * 
     * @param beatsPerMeasure number of beats per measure
     * @return measure duration in milliseconds
     */
    public long getMeasureDurationMs(int beatsPerMeasure) {
        return getBeatDurationMs() * beatsPerMeasure;
    }
    
    /**
     * Converts beats to milliseconds.
     * 
     * @param beats number of beats
     * @return duration in milliseconds
     */
    public long beatsToMs(double beats) {
        return (long)(beats * getBeatDurationMs());
    }
    
    /**
     * Converts milliseconds to beats.
     * 
     * @param ms duration in milliseconds
     * @return number of beats
     */
    public double msToBeats(long ms) {
        return (double)ms / getBeatDurationMs();
    }
}
