package com.vsge.core.progression;

import com.vsge.core.chord.Chord;
import com.vsge.core.chord.ChordType;
import com.vsge.core.theory.Note;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a chord progression in a specific key.
 * Supports Roman numeral notation and common progressions.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class ChordProgression {
    
    /**
     * Roman numeral chord degrees in a key.
     */
    public enum Degree {
        I(0, "I", ChordType.MAJOR),      // Tonic
        ii(2, "ii", ChordType.MINOR),     // Supertonic
        iii(4, "iii", ChordType.MINOR),   // Mediant
        IV(5, "IV", ChordType.MAJOR),     // Subdominant
        V(7, "V", ChordType.MAJOR),       // Dominant
        vi(9, "vi", ChordType.MINOR),     // Submediant
        vii(11, "vii°", ChordType.DIMINISHED); // Leading tone

        private final int semitones;
        private final String symbol;
        private final ChordType defaultType;

        Degree(int semitones, String symbol, ChordType defaultType) {
            this.semitones = semitones;
            this.symbol = symbol;
            this.defaultType = defaultType;
        }

        public int getSemitones() {
            return semitones;
        }

        public String getSymbol() {
            return symbol;
        }

        public ChordType getDefaultType() {
            return defaultType;
        }
    }

    private final Note key;
    private final List<Degree> progression;
    private final int beatsPerChord;

    // Common progression patterns
    public static final Degree[] POP_PROGRESSION = {Degree.I, Degree.V, Degree.vi, Degree.IV};
    public static final Degree[] BLUES_12_BAR = {
        Degree.I, Degree.I, Degree.I, Degree.I,
        Degree.IV, Degree.IV, Degree.I, Degree.I,
        Degree.V, Degree.IV, Degree.I, Degree.V
    };
    public static final Degree[] JAZZ_II_V_I = {Degree.ii, Degree.V, Degree.I};
    public static final Degree[] CIRCLE_OF_FIFTHS = {
        Degree.I, Degree.IV, Degree.vii, Degree.iii, Degree.vi, Degree.ii, Degree.V, Degree.I
    };

    /**
     * Creates a new chord progression.
     * 
     * @param key the key of the progression
     * @param degrees the chord degrees in Roman numeral notation
     * @param beatsPerChord number of beats per chord
     */
    public ChordProgression(Note key, Degree[] degrees, int beatsPerChord) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (degrees == null || degrees.length == 0) {
            throw new IllegalArgumentException("Progression cannot be null or empty");
        }
        if (beatsPerChord <= 0) {
            throw new IllegalArgumentException("Beats per chord must be positive");
        }

        this.key = key;
        this.progression = Arrays.asList(degrees);
        this.beatsPerChord = beatsPerChord;
    }

    /**
     * Creates a chord progression from a predefined pattern.
     * 
     * @param key the key of the progression
     * @param pattern the predefined progression pattern
     * @param beatsPerChord number of beats per chord
     * @return a new chord progression
     */
    public static ChordProgression fromPattern(Note key, Degree[] pattern, int beatsPerChord) {
        return new ChordProgression(key, pattern, beatsPerChord);
    }

    /**
     * Generates the actual chord objects for this progression.
     * 
     * @return list of chord objects
     */
    public List<Chord> generateChords() {
        List<Chord> chords = new ArrayList<>();
        for (Degree degree : progression) {
            Note chordRoot = key.transpose(degree.getSemitones());
            chords.add(Chord.create(chordRoot, degree.getDefaultType()));
        }
        return chords;
    }

    /**
     * Gets the total duration of the progression in beats.
     * 
     * @return total beats
     */
    public int getTotalBeats() {
        return progression.size() * beatsPerChord;
    }

    /**
     * Gets the chord at a specific position in the progression.
     * 
     * @param index the position (0-based)
     * @return the chord at that position
     */
    public Chord getChordAt(int index) {
        if (index < 0 || index >= progression.size()) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        
        Degree degree = progression.get(index);
        Note chordRoot = key.transpose(degree.getSemitones());
        return Chord.create(chordRoot, degree.getDefaultType());
    }

    public Note getKey() {
        return key;
    }

    public List<Degree> getProgression() {
        return new ArrayList<>(progression);
    }

    public int getBeatsPerChord() {
        return beatsPerChord;
    }

    public int getLength() {
        return progression.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Key: ").append(key.toString()).append(" | ");
        sb.append("Progression: ");
        for (int i = 0; i < progression.size(); i++) {
            if (i > 0) sb.append(" - ");
            sb.append(progression.get(i).getSymbol());
        }
        sb.append(" | ").append(beatsPerChord).append(" beats per chord");
        return sb.toString();
    }
}
