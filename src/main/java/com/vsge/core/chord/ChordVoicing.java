package com.vsge.core.chord;

import com.vsge.core.theory.Note;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a chord voicing with notes arranged in a specific octave range.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class ChordVoicing {
    private final Chord chord;
    private final int baseOctave;
    private final VoicingType type;
    private final List<Note> voicedNotes;

    public ChordVoicing(Chord chord, int baseOctave, VoicingType type) {
        this.chord = chord;
        this.baseOctave = baseOctave;
        this.type = type;
        this.voicedNotes = createVoicing();
    }

    private List<Note> createVoicing() {
        List<Note> notes = new ArrayList<>();
        List<Note> chordNotes = chord.getNotes();

        switch (type) {
            case ROOT_POSITION:
                for (Note note : chordNotes) {
                    notes.add(new Note(note.getPitch(), baseOctave + 
                        (note.getMidiNumber() - chord.getRoot().getMidiNumber()) / 12));
                }
                break;
            case FIRST_INVERSION:
                if (chordNotes.size() >= 3) {
                    // Move root up an octave
                    notes.add(new Note(chordNotes.get(1).getPitch(), baseOctave));
                    notes.add(new Note(chordNotes.get(2).getPitch(), baseOctave));
                    notes.add(new Note(chordNotes.get(0).getPitch(), baseOctave + 1));
                }
                break;
            case SECOND_INVERSION:
                if (chordNotes.size() >= 3) {
                    // Move root and third up an octave
                    notes.add(new Note(chordNotes.get(2).getPitch(), baseOctave));
                    notes.add(new Note(chordNotes.get(0).getPitch(), baseOctave + 1));
                    notes.add(new Note(chordNotes.get(1).getPitch(), baseOctave + 1));
                }
                break;
            case CLOSE:
                for (int i = 0; i < chordNotes.size(); i++) {
                    notes.add(new Note(chordNotes.get(i).getPitch(), baseOctave + i));
                }
                break;
            case OPEN:
                // Spread notes across wider range
                for (int i = 0; i < chordNotes.size(); i++) {
                    notes.add(new Note(chordNotes.get(i).getPitch(), baseOctave + i * 2));
                }
                break;
            case DROP2:
                if (chordNotes.size() >= 4) {
                    // Drop second highest note an octave
                    notes.add(new Note(chordNotes.get(0).getPitch(), baseOctave));
                    notes.add(new Note(chordNotes.get(2).getPitch(), baseOctave));
                    notes.add(new Note(chordNotes.get(3).getPitch(), baseOctave));
                    notes.add(new Note(chordNotes.get(1).getPitch(), baseOctave - 1));
                }
                break;
            case SPREAD:
                // Distribute notes across even wider range
                for (int i = 0; i < chordNotes.size(); i++) {
                    notes.add(new Note(chordNotes.get(i).getPitch(), baseOctave + i * 3));
                }
                break;
            default:
                notes.addAll(chordNotes);
        }

        return Collections.unmodifiableList(notes);
    }

    public List<Note> getVoicedNotes() {
        return voicedNotes;
    }

    public Chord getChord() {
        return chord;
    }

    public int getBaseOctave() {
        return baseOctave;
    }

    public VoicingType getType() {
        return type;
    }

    @Override
    public String toString() {
        return chord.toString() + " (" + type.getDisplayName() + ")";
    }
}
