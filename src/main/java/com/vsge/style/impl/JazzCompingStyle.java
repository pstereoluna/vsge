package com.vsge.style.impl;

import com.vsge.style.PlayStyle;
import com.vsge.core.chord.Chord;
import com.vsge.core.theory.Note;
import java.util.ArrayList;
import java.util.List;

/**
 * Jazz comping style implementation.
 * Creates a sophisticated jazz accompaniment pattern.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class JazzCompingStyle implements PlayStyle {
    private static final double[] TIMING = {0, 0.75, 1.5, 2.25, 3.0};
    private static final int BASE_VELOCITY = 70;
    private static final int VELOCITY_VARIATION = 25;
    
    @Override
    public List<NoteEvent> generatePattern(Chord chord, int beatsPerMeasure, int tempo) {
        List<NoteEvent> events = new ArrayList<>();
        List<Note> chordNotes = chord.getNotes();
        
        // Jazz comping typically emphasizes the 3rd and 7th
        for (int i = 0; i < TIMING.length && i < beatsPerMeasure; i++) {
            // Alternate between full chord and partial chord
            if (i % 2 == 0) {
                // Full chord on strong beats
                for (Note note : chordNotes) {
                    int velocity = BASE_VELOCITY + 
                        (int)(Math.random() * VELOCITY_VARIATION) - (VELOCITY_VARIATION / 2);
                    
                    events.add(new NoteEvent(
                        note,
                        TIMING[i],
                        0.6,  // Longer duration for jazz
                        Math.max(30, Math.min(100, velocity))
                    ));
                }
            } else {
                // Partial chord on weak beats (3rd and 7th emphasis)
                if (chordNotes.size() >= 2) {
                    Note third = chordNotes.get(1);
                    Note seventh = chordNotes.size() > 3 ? chordNotes.get(3) : chordNotes.get(2);
                    
                    int velocity = BASE_VELOCITY + 
                        (int)(Math.random() * VELOCITY_VARIATION) - (VELOCITY_VARIATION / 2);
                    
                    events.add(new NoteEvent(
                        third,
                        TIMING[i],
                        0.4,
                        Math.max(30, Math.min(100, velocity))
                    ));
                    
                    events.add(new NoteEvent(
                        seventh,
                        TIMING[i],
                        0.4,
                        Math.max(30, Math.min(100, velocity))
                    ));
                }
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
        return "Sophisticated jazz accompaniment with emphasis on 3rd and 7th";
    }
}
