package com.vsge.core.rhythm.impl;

import com.vsge.core.rhythm.RhythmPattern;
import com.vsge.core.chord.Chord;
import com.vsge.core.theory.Note;
import java.util.ArrayList;
import java.util.List;

/**
 * Jazz comping rhythm pattern.
 * Pattern: Syncopated rhythm with emphasis on 2 and 4, sparse chord voicings.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class JazzCompingPattern implements RhythmPattern {
    
    // Jazz comping pattern: syncopated with emphasis on 2 and 4
    private static final double[] TIMING = {0.0, 0.75, 1.5, 2.25, 3.0};
    private static final int[] VELOCITIES = {70, 85, 70, 85, 70}; // Emphasis on 2 and 4
    private static final boolean[] ACCENTS = {false, true, false, true, false};
    private static final String[] TECHNIQUES = {"chord", "chord", "chord", "chord", "chord"};
    
    @Override
    public List<RhythmEvent> generatePattern(Chord chord, int beatsPerMeasure, int tempo) {
        List<RhythmEvent> events = new ArrayList<>();
        List<Note> chordNotes = chord.getNotes();
        
        // Jazz comping typically uses 3-4 note voicings, emphasize 3rd and 7th
        for (int i = 0; i < TIMING.length && i < beatsPerMeasure; i++) {
            // Play chord notes with slight stagger for jazz feel
            for (int j = 0; j < chordNotes.size(); j++) {
                Note note = chordNotes.get(j);
                
                // Jazz voicing: slight delay between notes for sophisticated sound
                double noteDelay = j * 0.01; // 10ms delay between notes
                double noteStartTime = TIMING[i] + noteDelay;
                
                // Humanization parameters (more subtle for jazz)
                double timingOffset = (Math.random() - 0.5) * 0.02; // ±10ms variation
                int velocityVariation = (int)((Math.random() - 0.5) * 6); // ±3 velocity variation
                
                // Emphasize 3rd and 7th (typically indices 1 and 3 in jazz chords)
                int baseVelocity = VELOCITIES[i % VELOCITIES.length];
                if (j == 1 || j == 3) { // 3rd and 7th
                    baseVelocity += 10;
                }
                
                events.add(new RhythmEvent(
                    note,
                    noteStartTime,
                    0.6, // Longer duration for jazz chords
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
        return "Jazz Comping";
    }
    
    @Override
    public String getDescription() {
        return "Syncopated jazz comping with emphasis on beats 2 and 4";
    }
}
