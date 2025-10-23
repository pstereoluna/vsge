package com.vsge.style.impl;

import com.vsge.style.PlayStyle;
import com.vsge.music.chord.Chord;
import com.vsge.music.theory.Note;
import java.util.ArrayList;
import java.util.List;

/**
 * Folk arpeggio playing style implementation.
 * Creates a fingerpicking pattern typical of folk music.
 * Pattern: root-3rd-5th-3rd repeated.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class FolkArpeggioStyle implements PlayStyle {
    private static final double[] TIMING = {0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5};
    private static final int[] NOTE_INDICES = {0, 2, 1, 2, 0, 2, 1, 2};
    private static final int BASE_VELOCITY = 64;
    private static final int VELOCITY_VARIATION = 20;
    
    @Override
    public List<NoteEvent> generatePattern(Chord chord, int beatsPerMeasure, int tempo) {
        List<NoteEvent> events = new ArrayList<>();
        List<Note> chordNotes = chord.getNotes();
        
        for (int i = 0; i < TIMING.length && i < beatsPerMeasure * 2; i++) {
            int noteIndex = NOTE_INDICES[i % NOTE_INDICES.length] % chordNotes.size();
            Note note = chordNotes.get(noteIndex);
            
            // Add subtle velocity variation for natural sound
            int velocity = BASE_VELOCITY + 
                (int)(Math.random() * VELOCITY_VARIATION) - (VELOCITY_VARIATION / 2);
            
            events.add(new NoteEvent(
                note,
                TIMING[i],
                0.4,  // Short duration for arpeggio
                Math.max(20, Math.min(100, velocity))  // Clamp velocity
            ));
        }
        
        return events;
    }
    
    @Override
    public String getName() {
        return "Folk Arpeggio";
    }
    
    @Override
    public String getDescription() {
        return "Gentle fingerpicking pattern common in folk and acoustic music";
    }
}
