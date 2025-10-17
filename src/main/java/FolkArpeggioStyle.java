// ========== FolkArpeggioStyle.java ==========
package com.vsge.style;

import com.vsge.core.theory.Note;
import com.vsge.core.chord.Chord;
import java.util.ArrayList;
import java.util.List;

/**
 * Folk arpeggio playing style implementation.
 * Creates a fingerpicking pattern typical of folk music.
 */
public class FolkArpeggioStyle implements PlayStyle {
  private static final double[] PATTERN_TIMING = {0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5};
  private static final int[] PATTERN_NOTES = {0, 2, 1, 2, 0, 2, 1, 2}; // Index into chord notes

  @Override
  public List<NoteEvent> generatePattern(Chord chord, int beatsPerMeasure, int tempo) {
    List<NoteEvent> events = new ArrayList<>();
    List<Note> chordNotes = chord.getNotes();

    for (int i = 0; i < PATTERN_TIMING.length && i < beatsPerMeasure * 2; i++) {
      int noteIndex = PATTERN_NOTES[i % PATTERN_NOTES.length] % chordNotes.size();
      Note note = chordNotes.get(noteIndex);

      // Add subtle velocity variation for more natural sound
      int velocity = 64 + (int)(Math.random() * 20) - 10;

      events.add(new NoteEvent(
          note,
          PATTERN_TIMING[i],
          0.4,  // Short duration for arpeggio notes
          velocity
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
