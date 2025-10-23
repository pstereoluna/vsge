package com.vsge.music.theory;

/**
 * Represents a musical interval between two notes.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public enum Interval {
  UNISON(0, "P1"),
  MINOR_SECOND(1, "m2"),
  MAJOR_SECOND(2, "M2"),
  MINOR_THIRD(3, "m3"),
  MAJOR_THIRD(4, "M3"),
  PERFECT_FOURTH(5, "P4"),
  TRITONE(6, "TT"),
  PERFECT_FIFTH(7, "P5"),
  MINOR_SIXTH(8, "m6"),
  MAJOR_SIXTH(9, "M6"),
  MINOR_SEVENTH(10, "m7"),
  MAJOR_SEVENTH(11, "M7"),
  OCTAVE(12, "P8");

  private final int semitones;
  private final String symbol;

  Interval(int semitones, String symbol) {
    this.semitones = semitones;
    this.symbol = symbol;
  }

  public int getSemitones() { return semitones; }
  public String getSymbol() { return symbol; }

  public static Interval fromSemitones(int semitones) {
    for (Interval interval : values()) {
      if (interval.semitones == semitones % 12) {
        return interval;
      }
    }
    throw new IllegalArgumentException("Invalid semitone count: " + semitones);
  }
}

