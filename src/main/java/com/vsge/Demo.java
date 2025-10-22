package com.vsge;

import com.vsge.audio.ImprovedMidiService;
import com.vsge.core.chord.Chord;
import com.vsge.core.chord.ChordFactory;
import com.vsge.core.chord.ChordType;
import com.vsge.core.progression.ChordProgression;
import com.vsge.core.theory.Note;
import com.vsge.engine.PlaybackEngine;
import com.vsge.style.PlayStyle;
import com.vsge.style.StyleFactory;
import com.vsge.song.Song;
import com.vsge.song.SongLibrary;

/**
 * Demo class that showcases VSGE functionality without requiring user input.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class Demo {
    
    public static void main(String[] args) {
        System.out.println("=== Virtual Stringless Guitar Engine Demo ===");
        System.out.println("Welcome to VSGE v1.0.0 - Automated Demo Mode");
        System.out.println();
        
        try {
            // Initialize improved MIDI service
            System.out.println("🔧 Initializing improved MIDI service...");
            ImprovedMidiService midi = ImprovedMidiService.getInstance();
            midi.initialize();
            System.out.println("✅ Improved MIDI service initialized with enhanced guitar sound");
            
            // Create playback engine
            PlaybackEngine engine = new PlaybackEngine();
            
            // Demo 1: Basic chord playing
            System.out.println("\n🎵 Demo 1: Playing C Major chord in different styles");
            Note c4 = new Note(Note.PitchClass.C, 4);
            Chord cMajor = ChordFactory.create(c4, ChordType.MAJOR);
            
            String[] styles = StyleFactory.getAvailableStyles();
            for (String styleName : styles) {
                System.out.println("   Playing: " + styleName);
                PlayStyle style = StyleFactory.create(styleName);
                engine.playChord(cMajor, style, 120);
                Thread.sleep(2000); // Wait 2 seconds between styles
            }
            
            // Demo 2: Chord progression
            System.out.println("\n🎼 Demo 2: Playing 'Let It Be' progression");
            ChordProgression letItBe = new ChordProgression(
                c4, ChordProgression.POP_PROGRESSION, 4
            );
            PlayStyle folkStyle = StyleFactory.create("folk");
            engine.playProgression(letItBe, folkStyle, 120);
            Thread.sleep(8000); // Wait for progression to complete
            
            // Demo 3: Different chord types
            System.out.println("\n🎸 Demo 3: Playing different chord types");
            Chord[] chords = {
                ChordFactory.create(c4, ChordType.MAJOR),
                ChordFactory.create(c4, ChordType.MINOR),
                ChordFactory.create(c4, ChordType.DOMINANT7),
                ChordFactory.create(c4, ChordType.MAJOR7)
            };
            
            String[] chordNames = {"C Major", "C Minor", "C7", "Cmaj7"};
            for (int i = 0; i < chords.length; i++) {
                System.out.println("   Playing: " + chordNames[i]);
                engine.playChord(chords[i], folkStyle, 120);
                Thread.sleep(2000);
            }
            
            // Demo 4: Song library
            System.out.println("\n📚 Demo 4: Available preset songs");
            var songs = SongLibrary.getPresetSongs();
            for (Song song : songs) {
                System.out.println("   • " + song.toString());
            }
            
            // Demo 5: Interactive mode simulation
            System.out.println("\n🎹 Demo 5: Simulating interactive chord playing");
            ChordProgression.Degree[] degrees = {
                ChordProgression.Degree.I,
                ChordProgression.Degree.V,
                ChordProgression.Degree.vi,
                ChordProgression.Degree.IV
            };
            
            String[] degreeNames = {"I (C)", "V (G)", "vi (Am)", "IV (F)"};
            for (int i = 0; i < degrees.length; i++) {
                System.out.println("   Playing degree: " + degreeNames[i]);
                Note chordRoot = c4.transpose(degrees[i].getSemitones());
                Chord chord = ChordFactory.create(chordRoot, degrees[i].getDefaultType());
                engine.playChord(chord, folkStyle, 120);
                Thread.sleep(1500);
            }
            
            System.out.println("\n🎉 Demo completed successfully!");
            System.out.println("\nTo run the interactive version:");
            System.out.println("  java -cp target/classes com.vsge.Main");
            System.out.println("\nFeatures demonstrated:");
            System.out.println("  ✅ Note and chord creation");
            System.out.println("  ✅ Multiple playing styles");
            System.out.println("  ✅ Chord progressions");
            System.out.println("  ✅ MIDI audio playback");
            System.out.println("  ✅ Song library");
            System.out.println("  ✅ Roman numeral progressions");
            
        } catch (Exception e) {
            System.err.println("❌ Demo failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cleanup
            try {
                ImprovedMidiService.getInstance().close();
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
    }
}


