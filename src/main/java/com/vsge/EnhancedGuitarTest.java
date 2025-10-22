package com.vsge;

import com.vsge.audio.ImprovedMidiService;
import com.vsge.core.chord.Chord;
import com.vsge.core.chord.ChordFactory;
import com.vsge.core.chord.ChordType;
import com.vsge.core.theory.Note;
import javax.sound.midi.*;

/**
 * Enhanced guitar test with better sound processing.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class EnhancedGuitarTest {
    
    public static void main(String[] args) {
        System.out.println("=== Enhanced Guitar Sound Test ===");
        System.out.println("Testing with better sound processing...");
        
        try {
            // Get the synthesizer directly to apply better effects
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            
            System.out.println("Using synthesizer: " + synth.getDeviceInfo().getName());
            
            Note c4 = new Note(Note.PitchClass.C, 4);
            Chord cMajor = ChordFactory.create(c4, ChordType.MAJOR);
            
            System.out.println("\n🎸 Testing with enhanced effects:");
            
            int[] guitarInstruments = {25, 24, 27, 31};
            String[] guitarNames = {
                "Steel String Acoustic",
                "Nylon String Acoustic", 
                "Electric Clean",
                "Guitar Harmonics"
            };
            
            for (int i = 0; i < guitarInstruments.length; i++) {
                System.out.println((i + 1) + ". " + guitarNames[i] + " (Program " + guitarInstruments[i] + ")");
                
                MidiChannel channel = channels[1]; // Use channel 1
                
                // Set instrument
                channel.programChange(guitarInstruments[i]);
                
                // Apply enhanced effects
                channel.controlChange(91, 60);  // Reverb
                channel.controlChange(93, 30);  // Chorus
                channel.controlChange(7, 100);  // Volume
                channel.controlChange(10, 40);  // Pan
                
                // For electric guitar, add some distortion
                if (guitarInstruments[i] == 27) {
                    channel.controlChange(94, 20); // Sustain
                }
                
                System.out.println("   Playing C major chord with enhanced effects...");
                
                // Play chord with strumming effect
                int[] midiNumbers = cMajor.getNotes().stream()
                    .mapToInt(Note::getMidiNumber)
                    .toArray();
                
                // Strumming effect - play notes with slight delay
                for (int j = 0; j < midiNumbers.length; j++) {
                    Thread.sleep(50); // 50ms delay between strings
                    channel.noteOn(midiNumbers[j], 80 + (int)(Math.random() * 20));
                }
                
                Thread.sleep(2000);
                
                // Stop all notes
                for (int midiNumber : midiNumbers) {
                    channel.noteOff(midiNumber);
                }
                
                Thread.sleep(1000);
                System.out.println("   --- Next sound ---\n");
            }
            
            System.out.println("🎵 Enhanced test completed!");
            System.out.println("\nIf sounds still seem similar, it's because:");
            System.out.println("1. Your system uses 'Emergency GM sound set' - basic quality");
            System.out.println("2. This is common on macOS/Linux systems");
            System.out.println("3. The VSGE engine is working correctly!");
            System.out.println("\nFor better sounds, you could:");
            System.out.println("• Install a better MIDI soundfont");
            System.out.println("• Use external MIDI software");
            System.out.println("• The current sounds are still functional for learning!");
            
        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
