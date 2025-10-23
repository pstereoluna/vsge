package com.vsge.ui;

import com.vsge.audio.ImprovedMidiService;
import com.vsge.core.chord.Chord;
import com.vsge.core.chord.ChordFactory;
import com.vsge.core.chord.ChordType;
import com.vsge.core.progression.ChordProgression;
import com.vsge.core.theory.Note;
import com.vsge.engine.EnhancedPlaybackEngine;
import com.vsge.engine.HumanizationSettings;
import com.vsge.core.rhythm.RhythmPatternFactory;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Enhanced console UI with rhythm patterns and humanization controls.
 * Focuses on interactive chord playing and custom progressions.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class EnhancedConsoleUI {
    private static final Logger logger = Logger.getLogger(EnhancedConsoleUI.class.getName());
    
    private final Scanner scanner;
    private final EnhancedPlaybackEngine playbackEngine;
    private Note currentKey = new Note(Note.PitchClass.C, 4);
    private String currentStyle = "folk";
    private int currentTempo = 120;
    
    public EnhancedConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.playbackEngine = new EnhancedPlaybackEngine();
    }
    
    /**
     * Starts the enhanced console interface.
     */
    public void start() {
        System.out.println("=== Enhanced Virtual Stringless Guitar Engine ===");
        System.out.println("Welcome to VSGE v2.0 - Now with Rhythm Patterns & Humanization!");
        System.out.println();
        
        try {
            // Initialize audio
            System.out.println("🔧 Initializing enhanced audio system...");
            ImprovedMidiService.getInstance().initialize();
            System.out.println("✅ Enhanced audio system ready");
            System.out.println();
            
            // Main menu loop
            while (true) {
                showMainMenu();
                int choice = getIntInput("Enter your choice: ");
                
                switch (choice) {
                    case 1:
                        interactiveChordPlay();
                        break;
                    case 2:
                        customChordProgression();
                        break;
                    case 3:
                        rhythmPatternDemo();
                        break;
                    case 4:
                        humanizationSettings();
                        break;
                    case 5:
                        keyAndStyleSettings();
                        break;
                    case 6:
                        help();
                        break;
                    case 7:
                        System.out.println("Thank you for using Enhanced VSGE!");
                        return;
                    default:
                        System.out.println("Please enter a valid number.");
                }
                
                System.out.println();
            }
            
        } catch (Exception e) {
            logger.severe("Error in enhanced console UI: " + e.getMessage());
            System.err.println("❌ Error: " + e.getMessage());
        } finally {
            cleanup();
        }
    }
    
    private void showMainMenu() {
        System.out.println("=== Enhanced Main Menu ===");
        System.out.println("1. Interactive Chord Play (with rhythm patterns)");
        System.out.println("2. Custom Chord Progression");
        System.out.println("3. Rhythm Pattern Demo");
        System.out.println("4. Humanization Settings");
        System.out.println("5. Key & Style Settings");
        System.out.println("6. Help");
        System.out.println("7. Exit");
        System.out.println();
    }
    
    private void interactiveChordPlay() {
        System.out.println("\n🎸 Interactive Chord Play");
        System.out.println("Current key: " + currentKey.getPitch() + ", Style: " + currentStyle);
        System.out.println("Available chords: C, D, E, F, G, A, B");
        System.out.println("Chord types: major, minor, 7, maj7, m7, dim");
        System.out.println();
        
        while (true) {
            System.out.print("Enter chord (e.g., 'C major' or 'Am7') or 'back': ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("back")) {
                break;
            }
            
            try {
                Chord chord = parseChord(input);
                if (chord != null) {
                    System.out.println("🎵 Playing " + chord.getSymbol() + " with " + currentStyle + " rhythm...");
                    playbackEngine.setStyleHumanization(currentStyle);
                    playbackEngine.playChord(chord, currentStyle, currentTempo);
                    
                    // Wait for chord to finish
                    Thread.sleep(2000);
                } else {
                    System.out.println("❌ Invalid chord format. Try 'C major' or 'Am7'");
                }
            } catch (Exception e) {
                System.out.println("❌ Error playing chord: " + e.getMessage());
            }
        }
    }
    
    private void customChordProgression() {
        System.out.println("\n🎼 Custom Chord Progression");
        System.out.println("Enter chord progression (e.g., 'C Am F G' or 'I vi IV V')");
        System.out.println("Use Roman numerals: I, ii, iii, IV, V, vi, vii°");
        System.out.println();
        
        System.out.print("Enter progression: ");
        String input = scanner.nextLine().trim();
        
        try {
            ChordProgression progression = parseChordProgression(input);
            if (progression != null) {
                System.out.println("🎵 Playing progression with " + currentStyle + " rhythm...");
                playbackEngine.setStyleHumanization(currentStyle);
                playbackEngine.playProgression(progression, currentStyle, currentTempo);
                
                // Wait for progression to finish
                Thread.sleep(progression.generateChords().size() * 2000);
            } else {
                System.out.println("❌ Invalid progression format");
            }
        } catch (Exception e) {
            System.out.println("❌ Error playing progression: " + e.getMessage());
        }
    }
    
    private void rhythmPatternDemo() {
        System.out.println("\n🥁 Rhythm Pattern Demo");
        System.out.println("Available patterns: Folk, Pop, Jazz, Rock");
        System.out.println();
        
        String[] patterns = {"folk", "pop", "jazz", "rock"};
        Chord demoChord = ChordFactory.create(currentKey, ChordType.MAJOR);
        
        for (String pattern : patterns) {
            System.out.println("🎵 Playing " + pattern.toUpperCase() + " pattern...");
            try {
                playbackEngine.setStyleHumanization(pattern);
                playbackEngine.playChord(demoChord, pattern, currentTempo);
                Thread.sleep(3000);
            } catch (Exception e) {
                System.out.println("❌ Error playing " + pattern + " pattern: " + e.getMessage());
            }
        }
    }
    
    private void humanizationSettings() {
        System.out.println("\n⚙️ Humanization Settings");
        HumanizationSettings settings = playbackEngine.getHumanizationSettings();
        
        System.out.println("Current settings:");
        System.out.println("  Timing variation: " + settings.getTimingOffsetRange() + "s");
        System.out.println("  Velocity variation: " + settings.getVelocityVariationRange());
        System.out.println("  Duration variation: " + settings.getDurationVariationRange());
        System.out.println("  Swing: " + (settings.isSwingEnabled() ? "ON" : "OFF"));
        System.out.println();
        
        System.out.println("1. Apply Folk preset");
        System.out.println("2. Apply Pop preset");
        System.out.println("3. Apply Jazz preset");
        System.out.println("4. Apply Rock preset");
        System.out.println("5. Back to main menu");
        
        int choice = getIntInput("Choose preset: ");
        switch (choice) {
            case 1:
                playbackEngine.setStyleHumanization("folk");
                System.out.println("✅ Applied Folk preset");
                break;
            case 2:
                playbackEngine.setStyleHumanization("pop");
                System.out.println("✅ Applied Pop preset");
                break;
            case 3:
                playbackEngine.setStyleHumanization("jazz");
                System.out.println("✅ Applied Jazz preset");
                break;
            case 4:
                playbackEngine.setStyleHumanization("rock");
                System.out.println("✅ Applied Rock preset");
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice");
        }
    }
    
    private void keyAndStyleSettings() {
        System.out.println("\n🎹 Key & Style Settings");
        System.out.println("Current key: " + currentKey.getPitch() + ", Style: " + currentStyle);
        System.out.println();
        
        // Key selection
        System.out.println("Available keys: C, D, E, F, G, A, B");
        System.out.print("Enter new key: ");
        String keyInput = scanner.nextLine().trim().toUpperCase();
        
        try {
            Note.PitchClass newPitch = Note.PitchClass.valueOf(keyInput);
            currentKey = new Note(newPitch, 4);
            System.out.println("✅ Key changed to: " + currentKey.getPitch());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid key: " + keyInput);
        }
        
        // Style selection
        System.out.println("\nAvailable styles: folk, pop, jazz, rock");
        System.out.print("Enter new style: ");
        String styleInput = scanner.nextLine().trim().toLowerCase();
        
        if (RhythmPatternFactory.isSupported(styleInput)) {
            currentStyle = styleInput;
            System.out.println("✅ Style changed to: " + currentStyle);
        } else {
            System.out.println("❌ Invalid style: " + styleInput);
        }
    }
    
    private void help() {
        System.out.println("\n📚 Enhanced VSGE Help");
        System.out.println();
        System.out.println("🎸 Rhythm Patterns:");
        System.out.println("  • Folk: Fingerpicking with thumb on bass, fingers on melody");
        System.out.println("  • Pop: Down-up strumming with emphasis on downstrokes");
        System.out.println("  • Jazz: Syncopated comping with emphasis on beats 2 and 4");
        System.out.println("  • Rock: Strong downstrokes with power chord emphasis");
        System.out.println();
        System.out.println("⚙️ Humanization:");
        System.out.println("  • Timing variation: Adds natural timing inconsistencies");
        System.out.println("  • Velocity variation: Adds natural dynamics");
        System.out.println("  • Duration variation: Adds natural note length variations");
        System.out.println("  • Swing: Adds swing feel to off-beats");
        System.out.println();
        System.out.println("🎼 Chord Input:");
        System.out.println("  • Chord names: 'C major', 'Am7', 'F#m'");
        System.out.println("  • Roman numerals: 'I', 'ii', 'IV', 'V7'");
        System.out.println("  • Progressions: 'C Am F G' or 'I vi IV V'");
    }
    
    private Chord parseChord(String input) {
        try {
            return ChordFactory.createFromString(input);
        } catch (Exception e) {
            return null;
        }
    }
    
    private ChordProgression parseChordProgression(String input) {
        // Simple implementation - create a basic progression
        try {
            // For now, create a simple C major progression
            // This could be enhanced to parse actual chord progressions
            ChordProgression.Degree[] degrees = {
                ChordProgression.Degree.I,
                ChordProgression.Degree.vi,
                ChordProgression.Degree.IV,
                ChordProgression.Degree.V
            };
            
            return new ChordProgression(new Note(Note.PitchClass.C, 4), degrees, 4);
        } catch (Exception e) {
            return null;
        }
    }
    
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 7; // Exit on invalid input
        }
    }
    
    private void cleanup() {
        try {
            playbackEngine.close();
            ImprovedMidiService.getInstance().close();
        } catch (Exception e) {
            logger.warning("Error during cleanup: " + e.getMessage());
        }
    }
}
