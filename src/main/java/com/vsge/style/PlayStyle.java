package com.vsge.style;

import com.vsge.music.chord.Chord;
import com.vsge.music.theory.Note;
import java.util.List;

/**
 * Strategy pattern interface for different playing styles.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public interface PlayStyle {
  /**
   * Generates a sequence of notes for the given chord.
   * @param chord The chord to play
   * @param beatsPerMeasure Number of beats in a measure
   * @param tempo BPM
   * @return List of timed note events
   */
  List<NoteEvent> generatePattern(Chord chord, int beatsPerMeasure, int tempo);

  /**
   * Returns the name of this playing style.
   */
  String getName();

  /**
   * Returns a description of this playing style.
   */
  String getDescription();

  /**
   * Represents a note with timing information.
   */
  class NoteEvent {
    private final Note note;
    private final double startTime;  // in beats
    private final double duration;   // in beats
    private final int velocity;      // MIDI velocity (0-127)

    public NoteEvent(Note note, double startTime, double duration, int velocity) {
      this.note = note;
      this.startTime = startTime;
      this.duration = duration;
      this.velocity = velocity;
    }

    // Getters...
    public Note getNote() { return note; }
    public double getStartTime() { return startTime; }
    public double getDuration() { return duration; }
    public int getVelocity() { return velocity; }
  }
}

