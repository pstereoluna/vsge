// ========== Chord.java ==========
package com.vsge.core.chord;

import java.util.*;

/**
 * Abstract base class for all chord types.
 * Demonstrates inheritance and template method pattern.
 */
public abstract class Chord {
  protected final Note root;
  protected final List<Note> notes;
  protected final String symbol;

  protected Chord(Note root, String symbol) {
    this.root = root;
    this.symbol = symbol;
    this.notes = new ArrayList<>();
    buildChord(); // Template method
  }

  /**
   * Template method for building chord notes.
   * Subclasses implement getIntervals() to define structure.
   */
  protected void buildChord() {
    notes.clear();
    notes.add(root);
    for (Interval interval : getIntervals()) {
      notes.add(root.transpose(interval.getSemitones()));
    }
  }

  /**
   * Abstract method for subclasses to define their intervals.
   */
  protected abstract List<Interval> getIntervals();

  /**
   * Factory method for creating chord instances.
   */
  public static Chord create(Note root, ChordType type) {
    switch (type) {
      case MAJOR:
        return new MajorChord(root);
      case MINOR:
        return new MinorChord(root);
      case DOMINANT7:
        return new Dominant7Chord(root);
      case MINOR7:
        return new Minor7Chord(root);
      case MAJOR7:
        return new Major7Chord(root);
      case DIMINISHED:
        return new DiminishedChord(root);
      default:
        throw new IllegalArgumentException("Unknown chord type: " + type);
    }
  }

  public List<Note> getNotes() {
    return Collections.unmodifiableList(notes);
  }

  public Note getRoot() { return root; }
  public String getSymbol() { return symbol; }

  /**
   * Creates a chord voicing with notes in specific octave range.
   */
  public ChordVoicing voice(int baseOctave, VoicingType type) {
    return new ChordVoicing(this, baseOctave, type);
  }

  @Override
  public String toString() {
    return root.toString() + symbol;
  }
}
