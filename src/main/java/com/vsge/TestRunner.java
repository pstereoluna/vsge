package com.vsge;

import com.vsge.core.chord.Chord;
import com.vsge.core.chord.ChordFactory;
import com.vsge.core.chord.ChordType;
import com.vsge.core.progression.ChordProgression;
import com.vsge.core.theory.Note;
import com.vsge.style.PlayStyle;
import com.vsge.style.StyleFactory;
import com.vsge.song.Song;
import com.vsge.song.SongLibrary;

/**
 * Simple test runner to verify core VSGE functionality.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class TestRunner {
    
    public static void main(String[] args) {
        System.out.println("=== VSGE Core Functionality Test ===");
        
        try {
            testNoteCreation();
            testChordCreation();
            testChordProgression();
            testPlayStyles();
            testSongLibrary();
            
            System.out.println("\n✅ All tests passed! VSGE is working correctly.");
            
        } catch (Exception e) {
            System.err.println("\n❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testNoteCreation() {
        System.out.println("\n1. Testing Note creation...");
        
        Note c4 = new Note(Note.PitchClass.C, 4);
        Note d4 = c4.transpose(2);
        Note c5 = c4.transpose(12);
        
        assertTrue(c4.getMidiNumber() == 60, "C4 should be MIDI 60");
        assertTrue(d4.getPitch() == Note.PitchClass.D, "Transposed note should be D");
        assertTrue(c5.getOctave() == 5, "Octave up should be octave 5");
        
        System.out.println("   ✓ Note creation and transposition working");
    }
    
    private static void testChordCreation() {
        System.out.println("\n2. Testing Chord creation...");
        
        Note c4 = new Note(Note.PitchClass.C, 4);
        Chord cMajor = ChordFactory.create(c4, ChordType.MAJOR);
        Chord cMinor = ChordFactory.create(c4, ChordType.MINOR);
        Chord c7 = ChordFactory.create(c4, ChordType.DOMINANT7);
        
        assertTrue(cMajor.getNotes().size() == 3, "Major chord should have 3 notes");
        assertTrue(cMinor.getNotes().size() == 3, "Minor chord should have 3 notes");
        assertTrue(c7.getNotes().size() == 4, "Dominant 7th should have 4 notes");
        
        // Test chord factory from string
        Chord fromString = ChordFactory.createFromString("Am");
        assertTrue(fromString.getRoot().getPitch() == Note.PitchClass.A, "Should create A minor");
        
        System.out.println("   ✓ Chord creation and factory working");
    }
    
    private static void testChordProgression() {
        System.out.println("\n3. Testing Chord Progression...");
        
        Note c4 = new Note(Note.PitchClass.C, 4);
        ChordProgression pop = new ChordProgression(
            c4, ChordProgression.POP_PROGRESSION, 4
        );
        
        var chords = pop.generateChords();
        assertTrue(chords.size() == 4, "Pop progression should have 4 chords");
        assertTrue(chords.get(0).getRoot().getPitch() == Note.PitchClass.C, "First chord should be C");
        assertTrue(chords.get(1).getRoot().getPitch() == Note.PitchClass.G, "Second chord should be G");
        
        System.out.println("   ✓ Chord progression generation working");
    }
    
    private static void testPlayStyles() {
        System.out.println("\n4. Testing Play Styles...");
        
        Note c4 = new Note(Note.PitchClass.C, 4);
        Chord chord = ChordFactory.create(c4, ChordType.MAJOR);
        
        String[] styleNames = StyleFactory.getAvailableStyles();
        assertTrue(styleNames.length == 4, "Should have 4 play styles");
        
        for (String styleName : styleNames) {
            PlayStyle style = StyleFactory.create(styleName);
            var events = style.generatePattern(chord, 4, 120);
            assertTrue(!events.isEmpty(), "Style should generate note events");
        }
        
        System.out.println("   ✓ All play styles working");
    }
    
    private static void testSongLibrary() {
        System.out.println("\n5. Testing Song Library...");
        
        var songs = SongLibrary.getPresetSongs();
        assertTrue(songs.size() == 5, "Should have 5 preset songs");
        
        Song letItBe = SongLibrary.getSongByTitle("Let It Be");
        assertTrue(letItBe != null, "Should find Let It Be");
        assertTrue(letItBe.getArtist().equals("The Beatles"), "Should have correct artist");
        
        var rockSongs = SongLibrary.getSongsByGenre("Rock");
        assertTrue(rockSongs.size() >= 2, "Should have multiple rock songs");
        
        System.out.println("   ✓ Song library working");
    }
    
    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
