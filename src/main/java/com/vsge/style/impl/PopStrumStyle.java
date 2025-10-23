package com.vsge.style.impl;

import com.vsge.style.PlayStyle;
import com.vsge.music.chord.Chord;
import com.vsge.music.theory.Note;
import java.util.ArrayList;
import java.util.List;

/**
 * Pop strumming style implementation.
 * Creates a typical pop/rock strumming pattern.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class PopStrumStyle implements PlayStyle {
    private static final double[] TIMING = {0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5};
    private static final int BASE_VELOCITY = 80;
    private static final int VELOCITY_VARIATION = 15;
    
    @Override
    public List<NoteEvent> generatePattern(Chord chord, int beatsPerMeasure, int tempo) {
        List<NoteEvent> events = new ArrayList<>();
        List<Note> chordNotes = chord.getNotes();
        
        for (int i = 0; i < TIMING.length && i < beatsPerMeasure * 2; i++) {
            // Play all chord notes simultaneously for strumming
            for (Note note : chordNotes) {
                int velocity = BASE_VELOCITY + 
                    (int)(Math.random() * VELOCITY_VARIATION) - (VELOCITY_VARIATION / 2);
                
                events.add(new NoteEvent(
                    note,
                    TIMING[i],
                    0.3,  // Short duration for strum
                    Math.max(40, Math.min(110, velocity))
                ));
            }
        }
        
        return events;
    }
    
    @Override
    public String getName() {
        return "Pop Strum";
    }
    
    @Override
    public String getDescription() {
        return "Upbeat strumming pattern typical of pop and rock music";
    }
}
