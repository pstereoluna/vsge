package com.vsge.core.chord;

import com.vsge.core.theory.Note;
import com.vsge.core.theory.Interval;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Chord class and its implementations.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class ChordTest {
    
    private Note c4;
    private Note d4;
    private Note e4;
    private Note f4;
    private Note g4;
    private Note a4;
    private Note b4;
    
    @BeforeEach
    void setUp() {
        c4 = new Note(Note.PitchClass.C, 4);
        d4 = new Note(Note.PitchClass.D, 4);
        e4 = new Note(Note.PitchClass.E, 4);
        f4 = new Note(Note.PitchClass.F, 4);
        g4 = new Note(Note.PitchClass.G, 4);
        a4 = new Note(Note.PitchClass.A, 4);
        b4 = new Note(Note.PitchClass.B, 4);
    }
    
    @Test
    void testMajorChord() {
        Chord cMajor = new MajorChord(c4);
        
        assertEquals(c4, cMajor.getRoot());
        assertEquals("", cMajor.getSymbol());
        
        var notes = cMajor.getNotes();
        assertEquals(3, notes.size());
        
        // C major should contain C, E, G
        assertEquals(c4, notes.get(0)); // Root
        assertEquals(e4, notes.get(1)); // Major third
        assertEquals(g4, notes.get(2)); // Perfect fifth
    }
    
    @Test
    void testMinorChord() {
        Chord cMinor = new MinorChord(c4);
        
        assertEquals(c4, cMinor.getRoot());
        assertEquals("m", cMinor.getSymbol());
        
        var notes = cMinor.getNotes();
        assertEquals(3, notes.size());
        
        // C minor should contain C, Eb, G
        assertEquals(c4, notes.get(0)); // Root
        Note eb4 = c4.transpose(Interval.MINOR_THIRD.getSemitones());
        assertEquals(eb4, notes.get(1)); // Minor third
        assertEquals(g4, notes.get(2)); // Perfect fifth
    }
    
    @Test
    void testDominant7Chord() {
        Chord c7 = new Dominant7Chord(c4);
        
        assertEquals(c4, c7.getRoot());
        assertEquals("7", c7.getSymbol());
        
        var notes = c7.getNotes();
        assertEquals(4, notes.size());
        
        // C7 should contain C, E, G, Bb
        assertEquals(c4, notes.get(0)); // Root
        assertEquals(e4, notes.get(1)); // Major third
        assertEquals(g4, notes.get(2)); // Perfect fifth
        Note bb4 = c4.transpose(Interval.MINOR_SEVENTH.getSemitones());
        assertEquals(bb4, notes.get(3)); // Minor seventh
    }
    
    @Test
    void testChordFactory() {
        // Test major chord creation
        Chord major = ChordFactory.create(c4, ChordType.MAJOR);
        assertTrue(major instanceof MajorChord);
        assertEquals(c4, major.getRoot());
        
        // Test minor chord creation
        Chord minor = ChordFactory.create(c4, ChordType.MINOR);
        assertTrue(minor instanceof MinorChord);
        assertEquals(c4, minor.getRoot());
        
        // Test dominant 7th chord creation
        Chord dom7 = ChordFactory.create(c4, ChordType.DOMINANT7);
        assertTrue(dom7 instanceof Dominant7Chord);
        assertEquals(c4, dom7.getRoot());
    }
    
    @Test
    void testChordFactoryFromString() {
        // Test major chord
        Chord cMajor = ChordFactory.createFromString("C");
        assertTrue(cMajor instanceof MajorChord);
        assertEquals(Note.PitchClass.C, cMajor.getRoot().getPitch());
        
        // Test minor chord
        Chord cMinor = ChordFactory.createFromString("Cm");
        assertTrue(cMinor instanceof MinorChord);
        assertEquals(Note.PitchClass.C, cMinor.getRoot().getPitch());
        
        // Test dominant 7th chord
        Chord c7 = ChordFactory.createFromString("C7");
        assertTrue(c7 instanceof Dominant7Chord);
        assertEquals(Note.PitchClass.C, c7.getRoot().getPitch());
    }
    
    @Test
    void testChordFactoryInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            ChordFactory.create(null, ChordType.MAJOR);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            ChordFactory.create(c4, null);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            ChordFactory.createFromString("");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            ChordFactory.createFromString("X");
        });
    }
    
    @Test
    void testChordVoicing() {
        Chord cMajor = new MajorChord(c4);
        ChordVoicing voicing = cMajor.voice(4, VoicingType.ROOT_POSITION);
        
        assertNotNull(voicing);
        assertEquals(cMajor, voicing.getChord());
        assertEquals(4, voicing.getBaseOctave());
        assertEquals(VoicingType.ROOT_POSITION, voicing.getType());
        
        var voicedNotes = voicing.getVoicedNotes();
        assertFalse(voicedNotes.isEmpty());
    }
    
    @Test
    void testChordToString() {
        Chord cMajor = new MajorChord(c4);
        assertEquals("C4", cMajor.toString());
        
        Chord cMinor = new MinorChord(c4);
        assertEquals("C4m", cMinor.toString());
    }
}
