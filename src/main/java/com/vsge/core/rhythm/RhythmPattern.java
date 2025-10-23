package com.vsge.core.rhythm;

import com.vsge.core.chord.Chord;
import com.vsge.core.theory.Note;
import java.util.List;

/**
 * Interface for defining rhythm patterns for different playing styles.
 * Each pattern defines how notes are played in terms of timing and emphasis.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public interface RhythmPattern {
    
    /**
     * Generates rhythm events for a chord based on the pattern.
     * 
     * @param chord the chord to play
     * @param beatsPerMeasure number of beats in a measure
     * @param tempo BPM
     * @return List of rhythm events
     */
    List<RhythmEvent> generatePattern(Chord chord, int beatsPerMeasure, int tempo);
    
    /**
     * Returns the name of this rhythm pattern.
     */
    String getName();
    
    /**
     * Returns a description of this rhythm pattern.
     */
    String getDescription();
    
    /**
     * Represents a rhythm event with enhanced timing and dynamics.
     */
    class RhythmEvent {
        private final Note note;
        private final double startTime;        // in beats
        private final double duration;         // in beats
        private final int velocity;            // MIDI velocity (0-127)
        private final double timingOffset;     // humanization timing offset
        private final int velocityVariation;   // humanization velocity variation
        private final boolean isAccent;        // whether this note is accented
        private final String technique;        // playing technique (strum, pluck, etc.)
        
        public RhythmEvent(Note note, double startTime, double duration, int velocity,
                          double timingOffset, int velocityVariation, boolean isAccent, String technique) {
            this.note = note;
            this.startTime = startTime;
            this.duration = duration;
            this.velocity = velocity;
            this.timingOffset = timingOffset;
            this.velocityVariation = velocityVariation;
            this.isAccent = isAccent;
            this.technique = technique;
        }
        
        // Getters
        public Note getNote() { return note; }
        public double getStartTime() { return startTime; }
        public double getDuration() { return duration; }
        public int getVelocity() { return velocity; }
        public double getTimingOffset() { return timingOffset; }
        public int getVelocityVariation() { return velocityVariation; }
        public boolean isAccent() { return isAccent; }
        public String getTechnique() { return technique; }
        
        /**
         * Gets the final velocity with humanization applied.
         */
        public int getFinalVelocity() {
            int varied = velocity + velocityVariation;
            return Math.max(20, Math.min(127, varied));
        }
        
        /**
         * Gets the final start time with humanization applied.
         */
        public double getFinalStartTime() {
            return startTime + timingOffset;
        }
    }
}
