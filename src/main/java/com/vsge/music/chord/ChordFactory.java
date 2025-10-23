package com.vsge.music.chord;

import com.vsge.music.theory.Note;

/**
 * Factory class for creating chord instances.
 * Implements the Factory design pattern.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class ChordFactory {
    
    private ChordFactory() {
        // Private constructor to prevent instantiation
    }

    /**
     * Creates a chord instance based on the root note and chord type.
     * 
     * @param root the root note of the chord
     * @param type the type of chord to create
     * @return a new chord instance
     * @throws IllegalArgumentException if the chord type is not supported
     */
    public static Chord create(Note root, ChordType type) {
        if (root == null) {
            throw new IllegalArgumentException("Root note cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Chord type cannot be null");
        }

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

    /**
     * Creates a chord from a string representation.
     * 
     * @param chordString the chord string (e.g., "C", "Am", "G7")
     * @return a new chord instance
     * @throws IllegalArgumentException if the chord string is invalid
     */
    public static Chord createFromString(String chordString) {
        if (chordString == null || chordString.trim().isEmpty()) {
            throw new IllegalArgumentException("Chord string cannot be null or empty");
        }

        String trimmed = chordString.trim().toUpperCase();
        
        // Parse root note
        Note.PitchClass rootPitch;
        int index = 0;
        
        if (trimmed.startsWith("C")) {
            rootPitch = Note.PitchClass.C;
            index = 1;
        } else if (trimmed.startsWith("CS") || trimmed.startsWith("C#")) {
            rootPitch = Note.PitchClass.CS;
            index = trimmed.startsWith("CS") ? 2 : 2;
        } else if (trimmed.startsWith("D")) {
            rootPitch = Note.PitchClass.D;
            index = 1;
        } else if (trimmed.startsWith("DS") || trimmed.startsWith("D#")) {
            rootPitch = Note.PitchClass.DS;
            index = trimmed.startsWith("DS") ? 2 : 2;
        } else if (trimmed.startsWith("E")) {
            rootPitch = Note.PitchClass.E;
            index = 1;
        } else if (trimmed.startsWith("F")) {
            rootPitch = Note.PitchClass.F;
            index = 1;
        } else if (trimmed.startsWith("FS") || trimmed.startsWith("F#")) {
            rootPitch = Note.PitchClass.FS;
            index = trimmed.startsWith("FS") ? 2 : 2;
        } else if (trimmed.startsWith("G")) {
            rootPitch = Note.PitchClass.G;
            index = 1;
        } else if (trimmed.startsWith("GS") || trimmed.startsWith("G#")) {
            rootPitch = Note.PitchClass.GS;
            index = trimmed.startsWith("GS") ? 2 : 2;
        } else if (trimmed.startsWith("A")) {
            rootPitch = Note.PitchClass.A;
            index = 1;
        } else if (trimmed.startsWith("AS") || trimmed.startsWith("A#")) {
            rootPitch = Note.PitchClass.AS;
            index = trimmed.startsWith("AS") ? 2 : 2;
        } else if (trimmed.startsWith("B")) {
            rootPitch = Note.PitchClass.B;
            index = 1;
        } else {
            throw new IllegalArgumentException("Invalid root note in chord: " + chordString);
        }

        // Parse chord type
        String suffix = trimmed.substring(index);
        ChordType type;
        
        if (suffix.isEmpty()) {
            type = ChordType.MAJOR;
        } else if (suffix.equals("M") || suffix.equals("MAJ")) {
            type = ChordType.MAJOR;
        } else if (suffix.equals("M") || suffix.equals("MIN")) {
            type = ChordType.MINOR;
        } else if (suffix.equals("7")) {
            type = ChordType.DOMINANT7;
        } else if (suffix.equals("M7") || suffix.equals("MAJ7")) {
            type = ChordType.MAJOR7;
        } else if (suffix.equals("M7") || suffix.equals("MIN7")) {
            type = ChordType.MINOR7;
        } else if (suffix.equals("°") || suffix.equals("DIM")) {
            type = ChordType.DIMINISHED;
        } else {
            throw new IllegalArgumentException("Invalid chord suffix: " + suffix);
        }

        Note root = new Note(rootPitch, 4); // Default to octave 4
        return create(root, type);
    }
}
