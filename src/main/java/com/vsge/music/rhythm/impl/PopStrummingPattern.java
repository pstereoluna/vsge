package com.vsge.music.rhythm.impl;

import com.vsge.music.rhythm.RhythmPattern;
import com.vsge.music.chord.Chord;
import com.vsge.music.theory.Note;
import java.util.ArrayList;
import java.util.List;

/**
 * Pop strumming rhythm pattern.
 * Pattern: Down-up-down-up with emphasis on downstrokes.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class PopStrummingPattern implements RhythmPattern {
    
    // Pop strumming pattern: down-up-down-up with emphasis
    private static final double[] TIMING = {0.0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5};
    private static final int[] VELOCITIES = {90, 60, 85, 65, 90, 60, 85, 65}; // Downstrokes stronger
    private static final boolean[] ACCENTS = {true, false, true, false, true, false, true, false};
    private static final String[] TECHNIQUES = {"down", "up", "down", "up", "down", "up", "down", "up"};
    
    @Override
    public List<RhythmEvent> generatePattern(Chord chord, int beatsPerMeasure, int tempo) {
        List<RhythmEvent> events = new ArrayList<>();
        List<Note> chordNotes = chord.getNotes();
        
        for (int i = 0; i < TIMING.length && i < beatsPerMeasure * 2; i++) {
            // Play all chord notes simultaneously for strumming
            for (int j = 0; j < chordNotes.size(); j++) {
                Note note = chordNotes.get(j);
                
                // Strumming effect: slight delay between strings
                double stringDelay = j * 0.02; // 20ms delay between strings
                double noteStartTime = TIMING[i] + stringDelay;
                
                // Humanization parameters
                double timingOffset = (Math.random() - 0.5) * 0.03; // ±15ms variation
                int velocityVariation = (int)((Math.random() - 0.5) * 8); // ±4 velocity variation
                
                // Downstrokes are stronger and more consistent
                int baseVelocity = VELOCITIES[i % VELOCITIES.length];
                if (TECHNIQUES[i % TECHNIQUES.length].equals("down")) {
                    baseVelocity += 5;
                }
                
                events.add(new RhythmEvent(
                    note,
                    noteStartTime,
                    0.3, // Short duration for strumming
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
        return "Pop Strumming";
    }
    
    @Override
    public String getDescription() {
        return "Upbeat pop strumming with down-up pattern and strong downstrokes";
    }
}
