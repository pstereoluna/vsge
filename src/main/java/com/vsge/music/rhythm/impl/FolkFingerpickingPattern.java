package com.vsge.music.rhythm.impl;

import com.vsge.music.rhythm.RhythmPattern;
import com.vsge.music.chord.Chord;
import com.vsge.music.theory.Note;
import java.util.ArrayList;
import java.util.List;

/**
 * Folk fingerpicking rhythm pattern.
 * Pattern: thumb plays bass notes, fingers play melody notes in sequence.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class FolkFingerpickingPattern implements RhythmPattern {
    
    // Folk fingerpicking pattern: bass-melody-melody-melody
    private static final double[] TIMING = {0.0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5};
    private static final int[] NOTE_INDICES = {0, 2, 1, 2, 0, 2, 1, 2}; // bass-melody-melody-melody
    private static final int[] VELOCITIES = {80, 60, 70, 60, 80, 60, 70, 60}; // bass stronger
    private static final boolean[] ACCENTS = {true, false, false, false, true, false, false, false};
    
    @Override
    public List<RhythmEvent> generatePattern(Chord chord, int beatsPerMeasure, int tempo) {
        List<RhythmEvent> events = new ArrayList<>();
        List<Note> chordNotes = chord.getNotes();
        
        // Ensure we have at least 3 notes for proper fingerpicking
        if (chordNotes.size() < 3) {
            return events;
        }
        
        for (int i = 0; i < TIMING.length && i < beatsPerMeasure * 2; i++) {
            int noteIndex = NOTE_INDICES[i % NOTE_INDICES.length] % chordNotes.size();
            Note note = chordNotes.get(noteIndex);
            
            // Humanization parameters
            double timingOffset = (Math.random() - 0.5) * 0.05; // ±25ms variation
            int velocityVariation = (int)((Math.random() - 0.5) * 10); // ±5 velocity variation
            
            // Bass notes get more emphasis
            int baseVelocity = VELOCITIES[i % VELOCITIES.length];
            if (noteIndex == 0) { // Bass note
                baseVelocity += 10;
            }
            
            events.add(new RhythmEvent(
                note,
                TIMING[i],
                0.4, // Short duration for fingerpicking
                baseVelocity,
                timingOffset,
                velocityVariation,
                ACCENTS[i % ACCENTS.length],
                noteIndex == 0 ? "thumb" : "finger"
            ));
        }
        
        return events;
    }
    
    @Override
    public String getName() {
        return "Folk Fingerpicking";
    }
    
    @Override
    public String getDescription() {
        return "Traditional folk fingerpicking with thumb on bass, fingers on melody";
    }
}
