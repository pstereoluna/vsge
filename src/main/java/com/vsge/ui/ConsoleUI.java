package com.vsge.ui;

import com.vsge.audio.MidiService;
import com.vsge.core.chord.Chord;
import com.vsge.core.chord.ChordFactory;
import com.vsge.core.chord.ChordType;
import com.vsge.core.progression.ChordProgression;
import com.vsge.core.theory.Note;
import com.vsge.engine.PlaybackEngine;
import com.vsge.style.PlayStyle;
import com.vsge.style.StyleFactory;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Console-based user interface for the VSGE application.
 * Provides menu-driven interaction with the guitar engine.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class ConsoleUI {
    private static final Logger logger = Logger.getLogger(ConsoleUI.class.getName());
    
    private final Scanner scanner;
    private final PlaybackEngine playbackEngine;
    private boolean running = true;
    
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.playbackEngine = new PlaybackEngine();
    }
    
    /**
     * Starts the console UI main loop.
     */
    public void start() {
        System.out.println("=== Virtual Stringless Guitar Engine ===");
        System.out.println("Welcome to VSGE v1.0.0");
        System.out.println();
        
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            handleMainMenuChoice(choice);
        }
        
        cleanup();
    }
    
    private void displayMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Quick Play (Preset Songs)");
        System.out.println("2. Interactive Performance Mode");
        System.out.println("3. Custom Chord Progression");
        System.out.println("4. Style Demonstration");
        System.out.println("5. Single Chord Play");
        System.out.println("6. Settings");
        System.out.println("7. Help");
        System.out.println("8. Exit");
        System.out.println();
    }
    
    private void handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                quickPlayMenu();
                break;
            case 2:
                interactivePerformanceMode();
                break;
            case 3:
                customChordProgression();
                break;
            case 4:
                styleDemonstration();
                break;
            case 5:
                singleChordPlay();
                break;
            case 6:
                settingsMenu();
                break;
            case 7:
                showHelp();
                break;
            case 8:
                running = false;
                System.out.println("Thank you for using VSGE!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void quickPlayMenu() {
        System.out.println("\n=== Quick Play - Preset Songs ===");
        System.out.println("1. Let It Be (C G Am F)");
        System.out.println("2. 12-Bar Blues in E");
        System.out.println("3. Autumn Leaves (Gm)");
        System.out.println("4. Wonderwall (C D Em)");
        System.out.println("5. Back to Main Menu");
        
        int choice = getIntInput("Select a song: ");
        
        switch (choice) {
            case 1:
                playLetItBe();
                break;
            case 2:
                playBlues12Bar();
                break;
            case 3:
                playAutumnLeaves();
                break;
            case 4:
                playWonderwall();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void playLetItBe() {
        System.out.println("Playing 'Let It Be' progression...");
        try {
            Note c = new Note(Note.PitchClass.C, 4);
            ChordProgression progression = new ChordProgression(
                c, ChordProgression.POP_PROGRESSION, 4
            );
            PlayStyle style = StyleFactory.create("folk");
            playbackEngine.playProgression(progression, style, 120);
            waitForUser("Press Enter to continue...");
        } catch (Exception e) {
            System.out.println("Error playing song: " + e.getMessage());
        }
    }
    
    private void playBlues12Bar() {
        System.out.println("Playing 12-Bar Blues in E...");
        try {
            Note e = new Note(Note.PitchClass.E, 4);
            ChordProgression progression = new ChordProgression(
                e, ChordProgression.BLUES_12_BAR, 4
            );
            PlayStyle style = StyleFactory.create("rock");
            playbackEngine.playProgression(progression, style, 100);
            waitForUser("Press Enter to continue...");
        } catch (Exception e) {
            System.out.println("Error playing song: " + e.getMessage());
        }
    }
    
    private void playAutumnLeaves() {
        System.out.println("Playing 'Autumn Leaves' (Jazz ii-V-I)...");
        try {
            Note gm = new Note(Note.PitchClass.G, 4);
            ChordProgression progression = new ChordProgression(
                gm, ChordProgression.JAZZ_II_V_I, 4
            );
            PlayStyle style = StyleFactory.create("jazz");
            playbackEngine.playProgression(progression, style, 140);
            waitForUser("Press Enter to continue...");
        } catch (Exception e) {
            System.out.println("Error playing song: " + e.getMessage());
        }
    }
    
    private void playWonderwall() {
        System.out.println("Playing 'Wonderwall' progression...");
        try {
            Note c = new Note(Note.PitchClass.C, 4);
            ChordProgression.Degree[] wonderwall = {
                ChordProgression.Degree.I,
                ChordProgression.Degree.IV,
                ChordProgression.Degree.vi,
                ChordProgression.Degree.iii
            };
            ChordProgression progression = new ChordProgression(c, wonderwall, 4);
            PlayStyle style = StyleFactory.create("pop");
            playbackEngine.playProgression(progression, style, 110);
            waitForUser("Press Enter to continue...");
        } catch (Exception e) {
            System.out.println("Error playing song: " + e.getMessage());
        }
    }
    
    private void interactivePerformanceMode() {
        System.out.println("\n=== Interactive Performance Mode ===");
        System.out.println("This mode allows real-time chord playing.");
        System.out.println("Commands:");
        System.out.println("1-7: Play chord degrees (I, ii, iii, IV, V, vi, vii°)");
        System.out.println("F/P/J/R: Change style (Folk/Pop/Jazz/Rock)");
        System.out.println("S: Stop all");
        System.out.println("Q: Quit to main menu");
        System.out.println();
        
        PlayStyle currentStyle = StyleFactory.getDefault();
        Note currentKey = new Note(Note.PitchClass.C, 4);
        
        System.out.println("Current key: " + currentKey.toString());
        System.out.println("Current style: " + currentStyle.getName());
        System.out.println();
        
        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine().trim().toUpperCase();
            
            if (input.equals("Q")) {
                break;
            } else if (input.equals("S")) {
                playbackEngine.stop();
                System.out.println("Stopped all notes.");
            } else if (input.equals("F")) {
                currentStyle = StyleFactory.create("folk");
                System.out.println("Style changed to: " + currentStyle.getName());
            } else if (input.equals("P")) {
                currentStyle = StyleFactory.create("pop");
                System.out.println("Style changed to: " + currentStyle.getName());
            } else if (input.equals("J")) {
                currentStyle = StyleFactory.create("jazz");
                System.out.println("Style changed to: " + currentStyle.getName());
            } else if (input.equals("R")) {
                currentStyle = StyleFactory.create("rock");
                System.out.println("Style changed to: " + currentStyle.getName());
            } else if (input.matches("[1-7]")) {
                int degreeIndex = Integer.parseInt(input) - 1;
                ChordProgression.Degree[] degrees = ChordProgression.Degree.values();
                if (degreeIndex < degrees.length) {
                    ChordProgression.Degree degree = degrees[degreeIndex];
                    Note chordRoot = currentKey.transpose(degree.getSemitones());
                    Chord chord = ChordFactory.create(chordRoot, degree.getDefaultType());
                    playbackEngine.playChord(chord, currentStyle, 120);
                    System.out.println("Playing: " + chord.toString() + " (" + degree.getSymbol() + ")");
                }
            } else {
                System.out.println("Invalid command. Try again.");
            }
        }
    }
    
    private void customChordProgression() {
        System.out.println("\n=== Custom Chord Progression ===");
        System.out.println("Enter chord progression using Roman numerals (e.g., I-V-vi-IV):");
        System.out.println("Available degrees: I, ii, iii, IV, V, vi, vii°");
        
        String input = scanner.nextLine().trim();
        // Implementation would parse the input and create progression
        System.out.println("Custom progression feature coming soon!");
    }
    
    private void styleDemonstration() {
        System.out.println("\n=== Style Demonstration ===");
        System.out.println("Playing C major chord in all styles...");
        
        try {
            Note c = new Note(Note.PitchClass.C, 4);
            Chord chord = ChordFactory.create(c, ChordType.MAJOR);
            
            String[] styles = StyleFactory.getAvailableStyles();
            for (String styleName : styles) {
                System.out.println("Playing: " + styleName);
                PlayStyle style = StyleFactory.create(styleName);
                playbackEngine.playChord(chord, style, 120);
                Thread.sleep(3000); // Wait 3 seconds between styles
            }
        } catch (Exception e) {
            System.out.println("Error in style demonstration: " + e.getMessage());
        }
    }
    
    private void singleChordPlay() {
        System.out.println("\n=== Single Chord Play ===");
        System.out.println("Enter chord (e.g., C, Am, G7): ");
        String chordInput = scanner.nextLine().trim();
        
        try {
            Chord chord = ChordFactory.createFromString(chordInput);
            System.out.println("Chord: " + chord.toString());
            
            System.out.println("Available styles:");
            String[] styles = StyleFactory.getAvailableStyles();
            for (int i = 0; i < styles.length; i++) {
                System.out.println((i + 1) + ". " + styles[i]);
            }
            
            int styleChoice = getIntInput("Select style: ");
            if (styleChoice > 0 && styleChoice <= styles.length) {
                PlayStyle style = StyleFactory.create(styles[styleChoice - 1]);
                playbackEngine.playChord(chord, style, 120);
                waitForUser("Press Enter to continue...");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void settingsMenu() {
        System.out.println("\n=== Settings ===");
        System.out.println("1. Change Tempo");
        System.out.println("2. Change Instrument");
        System.out.println("3. Back to Main Menu");
        
        int choice = getIntInput("Select option: ");
        
        switch (choice) {
            case 1:
                int tempo = getIntInput("Enter tempo (60-200 BPM): ");
                if (tempo >= 60 && tempo <= 200) {
                    playbackEngine.setTempo(tempo);
                    System.out.println("Tempo set to " + tempo + " BPM");
                } else {
                    System.out.println("Invalid tempo range.");
                }
                break;
            case 2:
                System.out.println("Instrument change feature coming soon!");
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void showHelp() {
        System.out.println("\n=== Help ===");
        System.out.println("VSGE - Virtual Stringless Guitar Engine");
        System.out.println("Version 1.0.0");
        System.out.println();
        System.out.println("Features:");
        System.out.println("- Play chords and progressions");
        System.out.println("- Multiple playing styles (Folk, Pop, Jazz, Rock)");
        System.out.println("- Interactive performance mode");
        System.out.println("- Preset songs and custom progressions");
        System.out.println();
        System.out.println("Chord Notation:");
        System.out.println("- Major: C, D, E, F, G, A, B");
        System.out.println("- Minor: Cm, Dm, Em, Fm, Gm, Am, Bm");
        System.out.println("- 7th: C7, D7, E7, F7, G7, A7, B7");
        System.out.println();
    }
    
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next();
            }
            return scanner.nextInt();
        } catch (Exception e) {
            System.out.println("\nInput error: " + e.getMessage());
            return 8; // Default to exit
        }
    }
    
    private void waitForUser(String message) {
        System.out.println(message);
        scanner.nextLine(); // Clear the buffer
        scanner.nextLine(); // Wait for user input
    }
    
    private void cleanup() {
        playbackEngine.close();
        scanner.close();
        MidiService.getInstance().close();
    }
}
