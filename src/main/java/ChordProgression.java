// ========== ChordProgression.java ==========
package com.vsge.progression;

import com.vsge.theory.*;
import java.util.*;

/**
 * Represents a chord progression in a specific key.
 * Supports Roman numeral notation and common progressions.
 */
public class ChordProgression {
  public enum Degree {
    I(0, "I", ChordType.MAJOR),
    ii(2, "ii", ChordType.MINOR),
    iii(4, "iii", ChordType.MINOR),
    IV(5, "IV", ChordType.MAJOR),
    V(7, "V", ChordType.MAJOR),
    vi(9, "vi", ChordType.MINOR),
    vii(11, "vii°", ChordType.DIMINISHED);

    private final int semitones;
    private final String symbol;
    private final ChordType defaultType;

    Degree(int semitones, String symbol, ChordType defaultType) {
      this.semitones = semitones;
      this.symbol = symbol;
      this.defaultType = defaultType;
    }
  }

  private final Note key;
  private final List<Degree> progression;
  private final int beatsPerChord;

  // Common progression patterns
  public static final Degree[] POP_PROGRESSION = {Degree.I, Degree.V, Degree.vi, Degree.IV};
  public static final Degree[] BLUES_PROGRESSION = {Degree.I, Degree.I, Degree.I, Degree.I,
      Degree.IV, Degree.IV, Degree.I, Degree.I,
      Degree.V, Degree.IV, Degree.I, Degree.V};

  public ChordProgression(Note key, Degree[] degrees, int beatsPerChord) {
    this.key = key;
    this.progression = Arrays.asList(degrees);
    this.beatsPerChord = beatsPerChord;
  }

  public List<Chord> generateChords() {
    List<Chord> chords = new ArrayList<>();
    for (Degree degree : progression) {
      Note chordRoot = key.transpose(degree.semitones);
      chords.add(Chord.create(chordRoot, degree.defaultType));
    }
    return chords;
  }

  public Note getKey() { return key; }
  public List<Degree> getProgression() { return new ArrayList<>(progression); }
  public int getBeatsPerChord() { return beatsPerChord; }
}
