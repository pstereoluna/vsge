package com.vsge.core.rhythm.impl;

import com.vsge.core.rhythm.RhythmPattern;
import com.vsge.core.chord.Chord;
import com.vsge.core.theory.Note;
import java.util.ArrayList;
import java.util.List;

/**
 * Rock power chord rhythm pattern.
 * Pattern: Strong downstrokes with palm muting and aggressive attack.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class RockPowerPattern implements RhythmPattern {
    
    // Rock power pattern: strong downstrokes on every beat
    private static final double[] TIMING = {0.0, 1.0, 2.0, 3.0};
    private static final int[] VELOCITIES = {110, 110, 110, 110}; // High velocity for power
    private static final boolean[] ACCENTS = {true, true, true, true}; // All accented
    private static final String[] TECHNIQUES = {"down", "down", "down", "down"};
    
    @Override
    public List<RhythmEvent> generatePattern(Chord chord, int beatsPerMeasure, int tempo) {
        List<RhythmEvent> events = new ArrayList<>();
        List<Note> chordNotes = chord.getNotes();
        
        // Rock power chords typically use root and fifth (first two notes)
        for (int i = 0; i < TIMING.length && i < beatsPerMeasure; i++) {
            // Play root and fifth with slight delay for power chord effect
            for (int j = 0; j < Math.min(2, chordNotes.size()); j++) {
                Note note = chordNotes.get(j);
                
                // Power chord effect: slight delay between root and fifth
                double noteDelay = j * 0.01; // 10ms delay
                double noteStartTime = TIMING[i] + noteDelay;
                
                // Humanization parameters (minimal for rock consistency)
                double timingOffset = (Math.random() - 0.5) * 0.01; // ±5ms variation
                int velocityVariation = (int)((Math.random() - 0.5) * 4); // ±2 velocity variation
                
                // Rock power: high velocity with slight palm muting effect
                int baseVelocity = VELOCITIES[i % VELOCITIES.length];
                if (j == 1) { // Fifth note slightly softer for palm muting effect
                    baseVelocity -= 5;
                }
                
                events.add(new RhythmEvent(
                    note,
                    noteStartTime,
                    0.8, // Longer duration for power chords
                    baseVelocity,
                    timingOffset,
                    velocityVariation,
                    ACCENTS[i % ACCENTS.length],
                    TECHNIQUES[i % TECHNIQUES.length]
                ));
            }
        }
        
        return events;
    }
    
    @Override
    public String getName() {
        return "Rock Power";
    }
    
    @Override
    public String getDescription() {
        return "Aggressive rock power chord pattern with strong downstrokes";
    }
}
