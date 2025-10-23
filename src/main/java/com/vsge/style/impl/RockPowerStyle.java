package com.vsge.style.impl;

import com.vsge.style.PlayStyle;
import com.vsge.music.chord.Chord;
import com.vsge.music.theory.Note;
import java.util.ArrayList;
import java.util.List;

/**
 * Rock power chord style implementation.
 * Creates a heavy, driving rhythm pattern.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class RockPowerStyle implements PlayStyle {
    private static final double[] TIMING = {0, 1.0, 2.0, 3.0};
    private static final int BASE_VELOCITY = 100;
    private static final int VELOCITY_VARIATION = 20;
    
    @Override
    public List<NoteEvent> generatePattern(Chord chord, int beatsPerMeasure, int tempo) {
        List<NoteEvent> events = new ArrayList<>();
        List<Note> chordNotes = chord.getNotes();
        
        // Rock power chords emphasize root and fifth
        for (int i = 0; i < TIMING.length && i < beatsPerMeasure; i++) {
            // Play root and fifth with emphasis
            Note root = chordNotes.get(0);
            Note fifth = chordNotes.size() > 2 ? chordNotes.get(2) : chordNotes.get(1);
            
            int velocity = BASE_VELOCITY + 
                (int)(Math.random() * VELOCITY_VARIATION) - (VELOCITY_VARIATION / 2);
            
            // Root note (stronger)
            events.add(new NoteEvent(
                root,
                TIMING[i],
                0.8,  // Longer duration for power
                Math.max(80, Math.min(127, velocity))
            ));
            
            // Fifth note (slightly softer)
            events.add(new NoteEvent(
                fifth,
                TIMING[i] + 0.1,  // Slight delay for punch
                0.7,
                Math.max(60, Math.min(110, velocity - 10))
            ));
            
            // Add third on strong beats for fuller sound
            if (i % 2 == 0 && chordNotes.size() > 1) {
                Note third = chordNotes.get(1);
                events.add(new NoteEvent(
                    third,
                    TIMING[i] + 0.05,
                    0.6,
                    Math.max(50, Math.min(100, velocity - 20))
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
        return "Heavy, driving rhythm with emphasis on root and fifth";
    }
}
