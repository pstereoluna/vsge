package com.vsge.music.theory;

/**
 * Represents a musical note with pitch class and octave.
 * Immutable class following the value object pattern.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class Note implements Comparable<Note> {
  public enum PitchClass {
    C(0), CS(1), D(2), DS(3), E(4), F(5),
    FS(6), G(7), GS(8), A(9), AS(10), B(11);

    private final int value;

    PitchClass(int value) {
      this.value = value;
    }

    public int getValue() { return value; }
  }

  private final PitchClass pitch;
  private final int octave;
  private final int midiNumber;

  public Note(PitchClass pitch, int octave) {
    if (octave < 0 || octave > 10) {
      throw new IllegalArgumentException("Octave must be between 0 and 10");
    }
    this.pitch = pitch;
    this.octave = octave;
    this.midiNumber = calculateMidiNumber();
  }

  private int calculateMidiNumber() {
    return (octave + 1) * 12 + pitch.getValue();
  }

  public Note transpose(int semitones) {
    int newMidi = midiNumber + semitones;
    if (newMidi < 0 || newMidi > 127) {
      throw new IllegalArgumentException("Transposition out of MIDI range");
    }
    return fromMidiNumber(newMidi);
  }

  public static Note fromMidiNumber(int midiNumber) {
    int octave = (midiNumber / 12) - 1;
    int pitchValue = midiNumber % 12;
    PitchClass pitch = PitchClass.values()[pitchValue];
    return new Note(pitch, octave);
  }

  @Override
  public int compareTo(Note other) {
    return Integer.compare(this.midiNumber, other.midiNumber);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Note)) return false;
    Note other = (Note) obj;
    return midiNumber == other.midiNumber;
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(midiNumber);
  }

  @Override
  public String toString() {
    return pitch.name() + octave;
  }

  public PitchClass getPitch() {
    return pitch;
  }

  public int getOctave() {
    return octave;
  }

  public int getMidiNumber() {
    return midiNumber;
  }
}

