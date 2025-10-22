package com.vsge.core.progression;

import com.vsge.core.chord.Chord;
import com.vsge.core.chord.ChordType;
import com.vsge.core.theory.Note;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ChordProgression class.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class ChordProgressionTest {
    
    private Note c4;
    private Note g4;
    
    @BeforeEach
    void setUp() {
        c4 = new Note(Note.PitchClass.C, 4);
        g4 = new Note(Note.PitchClass.G, 4);
    }
    
    @Test
    void testChordProgressionCreation() {
        ChordProgression.Degree[] degrees = {
            ChordProgression.Degree.I,
            ChordProgression.Degree.V,
            ChordProgression.Degree.vi,
            ChordProgression.Degree.IV
        };
        
        ChordProgression progression = new ChordProgression(c4, degrees, 4);
        
        assertEquals(c4, progression.getKey());
        assertEquals(4, progression.getBeatsPerChord());
        assertEquals(4, progression.getLength());
        assertEquals(16, progression.getTotalBeats()); // 4 chords * 4 beats
    }
    
    @Test
    void testGenerateChords() {
        ChordProgression.Degree[] degrees = {
            ChordProgression.Degree.I,
            ChordProgression.Degree.V
        };
        
        ChordProgression progression = new ChordProgression(c4, degrees, 4);
        var chords = progression.generateChords();
        
        assertEquals(2, chords.size());
        
        // First chord should be C major (I)
        Chord firstChord = chords.get(0);
        assertEquals(Note.PitchClass.C, firstChord.getRoot().getPitch());
        assertTrue(firstChord instanceof com.vsge.core.chord.MajorChord);
        
        // Second chord should be G major (V)
        Chord secondChord = chords.get(1);
        assertEquals(Note.PitchClass.G, secondChord.getRoot().getPitch());
        assertTrue(secondChord instanceof com.vsge.core.chord.MajorChord);
    }
    
    @Test
    void testGetChordAt() {
        ChordProgression.Degree[] degrees = {
            ChordProgression.Degree.I,
            ChordProgression.Degree.ii,
            ChordProgression.Degree.iii
        };
        
        ChordProgression progression = new ChordProgression(c4, degrees, 4);
        
        // Test first chord (I - C major)
        Chord first = progression.getChordAt(0);
        assertEquals(Note.PitchClass.C, first.getRoot().getPitch());
        assertTrue(first instanceof com.vsge.core.chord.MajorChord);
        
        // Test second chord (ii - D minor)
        Chord second = progression.getChordAt(1);
        assertEquals(Note.PitchClass.D, second.getRoot().getPitch());
        assertTrue(second instanceof com.vsge.core.chord.MinorChord);
        
        // Test third chord (iii - E minor)
        Chord third = progression.getChordAt(2);
        assertEquals(Note.PitchClass.E, third.getRoot().getPitch());
        assertTrue(third instanceof com.vsge.core.chord.MinorChord);
    }
    
    @Test
    void testGetChordAtInvalidIndex() {
        ChordProgression.Degree[] degrees = {ChordProgression.Degree.I};
        ChordProgression progression = new ChordProgression(c4, degrees, 4);
        
        assertThrows(IllegalArgumentException.class, () -> {
            progression.getChordAt(-1);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            progression.getChordAt(1);
        });
    }
    
    @Test
    void testPredefinedProgressions() {
        // Test pop progression
        ChordProgression pop = new ChordProgression(c4, ChordProgression.POP_PROGRESSION, 4);
        assertEquals(4, pop.getLength());
        
        var popChords = pop.generateChords();
        assertEquals(4, popChords.size());
        
        // I-V-vi-IV should be C-G-Am-F
        assertEquals(Note.PitchClass.C, popChords.get(0).getRoot().getPitch());
        assertEquals(Note.PitchClass.G, popChords.get(1).getRoot().getPitch());
        assertEquals(Note.PitchClass.A, popChords.get(2).getRoot().getPitch());
        assertEquals(Note.PitchClass.F, popChords.get(3).getRoot().getPitch());
    }
    
    @Test
    void testBluesProgression() {
        ChordProgression blues = new ChordProgression(g4, ChordProgression.BLUES_12_BAR, 4);
        assertEquals(12, blues.getLength());
        assertEquals(48, blues.getTotalBeats()); // 12 chords * 4 beats
    }
    
    @Test
    void testInvalidProgressionCreation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ChordProgression(null, ChordProgression.POP_PROGRESSION, 4);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ChordProgression(c4, null, 4);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ChordProgression(c4, new ChordProgression.Degree[0], 4);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ChordProgression(c4, ChordProgression.POP_PROGRESSION, 0);
        });
    }
    
    @Test
    void testDegreeProperties() {
        ChordProgression.Degree i = ChordProgression.Degree.I;
        assertEquals(0, i.getSemitones());
        assertEquals("I", i.getSymbol());
        assertEquals(ChordType.MAJOR, i.getDefaultType());
        
        ChordProgression.Degree ii = ChordProgression.Degree.ii;
        assertEquals(2, ii.getSemitones());
        assertEquals("ii", ii.getSymbol());
        assertEquals(ChordType.MINOR, ii.getDefaultType());
    }
    
    @Test
    void testToString() {
        ChordProgression progression = new ChordProgression(c4, ChordProgression.POP_PROGRESSION, 4);
        String str = progression.toString();
        
        assertTrue(str.contains("Key: C4"));
        assertTrue(str.contains("Progression: I - V - vi - IV"));
        assertTrue(str.contains("4 beats per chord"));
    }
}
