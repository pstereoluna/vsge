package com.vsge;

import com.vsge.audio.ImprovedMidiService;
import com.vsge.music.chord.Chord;
import com.vsge.music.chord.ChordFactory;
import com.vsge.music.chord.ChordType;
import com.vsge.music.theory.Note;
import com.vsge.engine.playback.EnhancedPlaybackEngine;
import com.vsge.engine.humanizer.HumanizationSettings;
import com.vsge.music.rhythm.RhythmPatternFactory;

/**
 * Enhanced demo showcasing the new rhythm patterns and humanization features.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class EnhancedDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Enhanced VSGE Demo ===");
        System.out.println("Demonstrating rhythm patterns and humanization");
        System.out.println();
        
        try {
            // Initialize enhanced audio
            System.out.println("🔧 Initializing enhanced audio system...");
            ImprovedMidiService.getInstance().initialize();
            System.out.println("✅ Enhanced audio system ready");
            System.out.println();
            
            EnhancedPlaybackEngine engine = new EnhancedPlaybackEngine();
            Note c4 = new Note(Note.PitchClass.C, 4);
            Chord cMajor = ChordFactory.create(c4, ChordType.MAJOR);
            
            // Demo 1: Rhythm Patterns
            System.out.println("🎸 Demo 1: Rhythm Patterns");
            System.out.println("Playing C Major chord with different rhythm patterns...");
            System.out.println();
            
            String[] patterns = {"folk", "pop", "jazz", "rock"};
            for (String pattern : patterns) {
                System.out.println("🎵 Playing " + pattern.toUpperCase() + " pattern:");
                System.out.println("  Pattern: " + RhythmPatternFactory.create(pattern).getDescription());
                
                engine.setStyleHumanization(pattern);
                engine.playChord(cMajor, pattern, 120);
                
                Thread.sleep(4000); // Wait for pattern to complete
                System.out.println("  ✅ " + pattern.toUpperCase() + " pattern completed");
                System.out.println();
            }
            
            // Demo 2: Humanization Settings
            System.out.println("⚙️ Demo 2: Humanization Settings");
            System.out.println("Playing same chord with different humanization presets...");
            System.out.println();
            
            HumanizationSettings[] presets = {
                HumanizationSettings.folkPreset(),
                HumanizationSettings.popPreset(),
                HumanizationSettings.jazzPreset(),
                HumanizationSettings.rockPreset()
            };
            
            String[] presetNames = {"Folk", "Pop", "Jazz", "Rock"};
            
            for (int i = 0; i < presets.length; i++) {
                System.out.println("🎵 Playing with " + presetNames[i] + " humanization:");
                System.out.println("  Timing variation: " + presets[i].getTimingOffsetRange() + "s");
                System.out.println("  Velocity variation: " + presets[i].getVelocityVariationRange());
                System.out.println("  Swing: " + (presets[i].isSwingEnabled() ? "ON" : "OFF"));
                
                // Apply preset (simplified - would need to copy settings)
                engine.setStyleHumanization(presetNames[i].toLowerCase());
                engine.playChord(cMajor, "folk", 120); // Use folk pattern for all
                
                Thread.sleep(3000);
                System.out.println("  ✅ " + presetNames[i] + " humanization completed");
                System.out.println();
            }
            
            // Demo 3: Chord Progression
            System.out.println("🎼 Demo 3: Chord Progression with Rhythm");
            System.out.println("Playing C-Am-F-G progression with Pop rhythm...");
            System.out.println();
            
            Chord[] progressionChords = {
                ChordFactory.create(new Note(Note.PitchClass.C, 4), ChordType.MAJOR),
                ChordFactory.create(new Note(Note.PitchClass.A, 4), ChordType.MINOR),
                ChordFactory.create(new Note(Note.PitchClass.F, 4), ChordType.MAJOR),
                ChordFactory.create(new Note(Note.PitchClass.G, 4), ChordType.MAJOR)
            };
            
            System.out.println("Progression: C - Am - F - G");
            engine.setStyleHumanization("pop");
            
            for (Chord chord : progressionChords) {
                System.out.println("🎵 Playing " + chord.getSymbol() + "...");
                engine.playChord(chord, "pop", 120);
                Thread.sleep(2000);
            }
            
            System.out.println("✅ Progression completed");
            System.out.println();
            
            // Demo 4: Available Features
            System.out.println("📚 Demo 4: Available Features");
            System.out.println("✅ Rhythm Patterns: " + String.join(", ", RhythmPatternFactory.getAvailablePatterns()));
            System.out.println("✅ Humanization: Timing, Velocity, Duration, Swing");
            System.out.println("✅ Enhanced Audio: Better guitar sounds with effects");
            System.out.println("✅ Interactive UI: Console and GUI interfaces");
            System.out.println();
            
            System.out.println("🎉 Enhanced VSGE Demo completed!");
            System.out.println("The engine now provides:");
            System.out.println("  • Realistic rhythm patterns for each style");
            System.out.println("  • Humanized timing and dynamics");
            System.out.println("  • Better audio quality");
            System.out.println("  • Focused on interactive chord playing");
            
        } catch (Exception e) {
            System.err.println("❌ Demo failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                ImprovedMidiService.getInstance().close();
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
    }
}
