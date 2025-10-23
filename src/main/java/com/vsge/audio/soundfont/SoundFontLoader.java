package com.vsge.audio.soundfont;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Service for loading and managing SoundFont files.
 * Provides better audio quality than default MIDI synthesizer.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class SoundFontLoader {
    private static final Logger logger = Logger.getLogger(SoundFontLoader.class.getName());
    
    private Synthesizer synthesizer;
    private Soundbank soundbank;
    private boolean soundfontLoaded = false;
    
    /**
     * Loads a SoundFont file.
     * 
     * @param soundfontPath path to the .sf2 file
     * @return true if loaded successfully, false otherwise
     */
    public boolean loadSoundFont(String soundfontPath) {
        try {
            File soundfontFile = new File(soundfontPath);
            if (!soundfontFile.exists()) {
                logger.warning("SoundFont file not found: " + soundfontPath);
                return false;
            }
            
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            
            // Load SoundFont
            soundbank = MidiSystem.getSoundbank(soundfontFile);
            if (soundbank == null) {
                logger.warning("Failed to load SoundFont: " + soundfontPath);
                return false;
            }
            
            // Load all instruments from SoundFont
            synthesizer.loadAllInstruments(soundbank);
            soundfontLoaded = true;
            
            logger.info("SoundFont loaded successfully: " + soundfontFile.getName());
            logger.info("Loaded " + soundbank.getInstruments().length + " instruments");
            
            return true;
            
        } catch (Exception e) {
            logger.severe("Error loading SoundFont: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Gets the synthesizer with SoundFont loaded.
     * 
     * @return synthesizer with SoundFont, or null if not loaded
     */
    public Synthesizer getSynthesizer() {
        return soundfontLoaded ? synthesizer : null;
    }
    
    /**
     * Gets the loaded soundbank.
     * 
     * @return soundbank, or null if not loaded
     */
    public Soundbank getSoundbank() {
        return soundbank;
    }
    
    /**
     * Checks if a SoundFont is loaded.
     * 
     * @return true if SoundFont is loaded, false otherwise
     */
    public boolean isSoundfontLoaded() {
        return soundfontLoaded;
    }
    
    /**
     * Gets available guitar instruments from the loaded SoundFont.
     * 
     * @return array of guitar instrument indices
     */
    public int[] getGuitarInstruments() {
        if (!soundfontLoaded || soundbank == null) {
            return new int[]{24, 25, 26, 27, 28, 29, 30, 31}; // Default GM guitar instruments
        }
        
        Instrument[] instruments = soundbank.getInstruments();
        java.util.List<Integer> guitarInstruments = new java.util.ArrayList<>();
        
        for (Instrument instrument : instruments) {
            String name = instrument.getName().toLowerCase();
            if (name.contains("guitar") || name.contains("acoustic") || name.contains("electric")) {
                guitarInstruments.add(instrument.getPatch().getProgram());
            }
        }
        
        return guitarInstruments.stream().mapToInt(Integer::intValue).toArray();
    }
    
    /**
     * Gets the best guitar instrument from the loaded SoundFont.
     * 
     * @return instrument index, or 25 (Acoustic Guitar) if not found
     */
    public int getBestGuitarInstrument() {
        if (!soundfontLoaded) {
            return 25; // Default Acoustic Guitar
        }
        
        int[] guitarInstruments = getGuitarInstruments();
        if (guitarInstruments.length > 0) {
            // Prefer Acoustic Guitar (25) or Steel String (25)
            for (int instrument : guitarInstruments) {
                if (instrument == 25) {
                    return instrument;
                }
            }
            return guitarInstruments[0]; // Return first available guitar
        }
        
        return 25; // Fallback to default
    }
    
    /**
     * Closes the SoundFont loader and releases resources.
     */
    public void close() {
        if (synthesizer != null && synthesizer.isOpen()) {
            synthesizer.close();
        }
        soundfontLoaded = false;
        logger.info("SoundFont loader closed");
    }
}