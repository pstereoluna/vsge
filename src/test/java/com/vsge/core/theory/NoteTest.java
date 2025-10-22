package com.vsge.core.theory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Note class.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class NoteTest {
    
    private Note c4;
    private Note d4;
    private Note c5;
    
    @BeforeEach
    void setUp() {
        c4 = new Note(Note.PitchClass.C, 4);
        d4 = new Note(Note.PitchClass.D, 4);
        c5 = new Note(Note.PitchClass.C, 5);
    }
    
    @Test
    void testNoteCreation() {
        assertEquals(Note.PitchClass.C, c4.getPitch());
        assertEquals(4, c4.getOctave());
        assertEquals(60, c4.getMidiNumber()); // C4 = MIDI 60
    }
    
    @Test
    void testMidiNumberCalculation() {
        // C4 should be MIDI 60
        assertEquals(60, c4.getMidiNumber());
        
        // C5 should be MIDI 72 (12 semitones higher)
        assertEquals(72, c5.getMidiNumber());
        
        // A4 should be MIDI 69
        Note a4 = new Note(Note.PitchClass.A, 4);
        assertEquals(69, a4.getMidiNumber());
    }
    
    @Test
    void testTransposition() {
        // Transpose C4 up by 2 semitones to get D4
        Note transposed = c4.transpose(2);
        assertEquals(Note.PitchClass.D, transposed.getPitch());
        assertEquals(4, transposed.getOctave());
        
        // Transpose C4 up by 12 semitones to get C5
        Note octaveUp = c4.transpose(12);
        assertEquals(Note.PitchClass.C, octaveUp.getPitch());
        assertEquals(5, octaveUp.getOctave());
    }
    
    @Test
    void testFromMidiNumber() {
        // MIDI 60 should be C4
        Note fromMidi = Note.fromMidiNumber(60);
        assertEquals(Note.PitchClass.C, fromMidi.getPitch());
        assertEquals(4, fromMidi.getOctave());
        
        // MIDI 69 should be A4
        Note a4 = Note.fromMidiNumber(69);
        assertEquals(Note.PitchClass.A, a4.getPitch());
        assertEquals(4, a4.getOctave());
    }
    
    @Test
    void testComparison() {
        // C4 should be less than D4
        assertTrue(c4.compareTo(d4) < 0);
        
        // C4 should be less than C5
        assertTrue(c4.compareTo(c5) < 0);
        
        // D4 should be greater than C4
        assertTrue(d4.compareTo(c4) > 0);
        
        // Same notes should be equal
        assertEquals(0, c4.compareTo(c4));
    }
    
    @Test
    void testEquality() {
        Note c4Copy = new Note(Note.PitchClass.C, 4);
        
        // Same pitch and octave should be equal
        assertEquals(c4, c4Copy);
        assertEquals(c4.hashCode(), c4Copy.hashCode());
        
        // Different notes should not be equal
        assertNotEquals(c4, d4);
        assertNotEquals(c4, c5);
    }
    
    @Test
    void testInvalidOctave() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Note(Note.PitchClass.C, -1);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Note(Note.PitchClass.C, 11);
        });
    }
    
    @Test
    void testInvalidTransposition() {
        assertThrows(IllegalArgumentException.class, () -> {
            c4.transpose(-61); // Would go below MIDI 0
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            c4.transpose(68); // Would go above MIDI 127
        });
    }
    
    @Test
    void testToString() {
        assertEquals("C4", c4.toString());
        assertEquals("D4", d4.toString());
        assertEquals("C5", c5.toString());
    }
}
