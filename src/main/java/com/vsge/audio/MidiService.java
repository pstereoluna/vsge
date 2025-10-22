package com.vsge.audio;

import javax.sound.midi.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * MIDI service implementation using Java Sound API.
 * Implements the Singleton design pattern.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class MidiService implements AudioService {
    private static final Logger logger = Logger.getLogger(MidiService.class.getName());
    private static MidiService instance;
    private static final Object lock = new Object();
    
    private Synthesizer synthesizer;
    private MidiChannel[] channels;
    private ScheduledExecutorService scheduler;
    private boolean initialized = false;
    
    // Default instrument settings - Best guitar sounds only
    private static final int DEFAULT_CHANNEL = 0;
    private static final int GUITAR_INSTRUMENT = 25; // Acoustic Guitar (steel) - Best overall
    private static final int GUITAR_NYLON = 24;      // Acoustic Guitar (nylon) - Classical/folk
    private static final int GUITAR_ELECTRIC = 27;   // Electric Guitar (clean) - Best electric
    private static final int GUITAR_HARMONICS = 31;  // Guitar Harmonics - Special effects
    private static final int PIANO_INSTRUMENT = 0;   // Acoustic Grand Piano
    
    private MidiService() {
        // Private constructor for singleton
    }
    
    /**
     * Gets the singleton instance of MidiService.
     * 
     * @return the MidiService instance
     */
    public static MidiService getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new MidiService();
                }
            }
        }
        return instance;
    }
    
    @Override
    public void initialize() throws Exception {
        if (initialized) {
            return;
        }
        
        try {
            // Get the default synthesizer
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            
            // Get the channels
            channels = synthesizer.getChannels();
            
            // Initialize scheduler for timed note events
            scheduler = Executors.newScheduledThreadPool(4);
            
            // Set default instruments
            setInstrument(DEFAULT_CHANNEL, GUITAR_INSTRUMENT);
            
            initialized = true;
            logger.info("MIDI service initialized successfully");
            
        } catch (MidiUnavailableException e) {
            logger.severe("Failed to initialize MIDI service: " + e.getMessage());
            throw new Exception("MIDI service unavailable", e);
        }
    }
    
    @Override
    public void playNote(int midiNumber, int velocity, long durationMs) {
        if (!initialized || channels == null) {
            logger.warning("MIDI service not initialized");
            return;
        }
        
        if (midiNumber < 0 || midiNumber > 127) {
            logger.warning("Invalid MIDI number: " + midiNumber);
            return;
        }
        
        if (velocity < 0 || velocity > 127) {
            velocity = Math.max(0, Math.min(127, velocity));
        }
        
        try {
            MidiChannel channel = channels[DEFAULT_CHANNEL];
            
            // Play the note
            channel.noteOn(midiNumber, velocity);
            
            // Schedule note off
            if (durationMs > 0) {
                scheduler.schedule(() -> {
                    try {
                        channel.noteOff(midiNumber);
                    } catch (Exception e) {
                        logger.warning("Error stopping note: " + e.getMessage());
                    }
                }, durationMs, TimeUnit.MILLISECONDS);
            }
            
        } catch (Exception e) {
            logger.warning("Error playing note: " + e.getMessage());
        }
    }
    
    @Override
    public void playChord(int[] midiNumbers, int velocity, long durationMs) {
        if (!initialized || channels == null) {
            logger.warning("MIDI service not initialized");
            return;
        }
        
        if (midiNumbers == null || midiNumbers.length == 0) {
            logger.warning("No notes provided for chord");
            return;
        }
        
        if (velocity < 0 || velocity > 127) {
            velocity = Math.max(0, Math.min(127, velocity));
        }
        
        try {
            MidiChannel channel = channels[DEFAULT_CHANNEL];
            
            // Play all notes simultaneously
            for (int midiNumber : midiNumbers) {
                if (midiNumber >= 0 && midiNumber <= 127) {
                    channel.noteOn(midiNumber, velocity);
                }
            }
            
            // Schedule note off for all notes
            if (durationMs > 0) {
                scheduler.schedule(() -> {
                    try {
                        for (int midiNumber : midiNumbers) {
                            if (midiNumber >= 0 && midiNumber <= 127) {
                                channel.noteOff(midiNumber);
                            }
                        }
                    } catch (Exception e) {
                        logger.warning("Error stopping chord: " + e.getMessage());
                    }
                }, durationMs, TimeUnit.MILLISECONDS);
            }
            
        } catch (Exception e) {
            logger.warning("Error playing chord: " + e.getMessage());
        }
    }
    
    @Override
    public void stopAll() {
        if (!initialized || channels == null) {
            return;
        }
        
        try {
            MidiChannel channel = channels[DEFAULT_CHANNEL];
            channel.allNotesOff();
            channel.allSoundOff();
        } catch (Exception e) {
            logger.warning("Error stopping all notes: " + e.getMessage());
        }
    }
    
    @Override
    public void setInstrument(int channel, int instrument) {
        if (!initialized || channels == null) {
            logger.warning("MIDI service not initialized");
            return;
        }
        
        if (channel < 0 || channel >= channels.length) {
            logger.warning("Invalid channel: " + channel);
            return;
        }
        
        if (instrument < 0 || instrument > 127) {
            logger.warning("Invalid instrument: " + instrument);
            return;
        }
        
        try {
            channels[channel].programChange(instrument);
            String instrumentName = getInstrumentName(instrument);
            logger.info("Set instrument " + instrument + " (" + instrumentName + ") on channel " + channel);
        } catch (Exception e) {
            logger.warning("Error setting instrument: " + e.getMessage());
        }
    }
    
    @Override
    public void close() {
        if (!initialized) {
            return;
        }
        
        try {
            stopAll();
            
            if (scheduler != null && !scheduler.isShutdown()) {
                scheduler.shutdown();
                try {
                    if (!scheduler.awaitTermination(2, TimeUnit.SECONDS)) {
                        scheduler.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    scheduler.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
            
            if (synthesizer != null && synthesizer.isOpen()) {
                synthesizer.close();
            }
            
            initialized = false;
            logger.info("MIDI service closed");
            
        } catch (Exception e) {
            logger.warning("Error closing MIDI service: " + e.getMessage());
        }
    }
    
    @Override
    public boolean isInitialized() {
        return initialized;
    }
    
    /**
     * Gets the number of available MIDI channels.
     * 
     * @return the number of channels
     */
    public int getChannelCount() {
        return channels != null ? channels.length : 0;
    }
    
    /**
     * Gets the current instrument on the default channel.
     * 
     * @return the instrument number
     */
    public int getCurrentInstrument() {
        if (!initialized || channels == null) {
            return -1;
        }
        return channels[DEFAULT_CHANNEL].getProgram();
    }
    
    /**
     * Gets the name of a MIDI instrument.
     * 
     * @param instrument the instrument number
     * @return the instrument name
     */
    private String getInstrumentName(int instrument) {
        switch (instrument) {
            case 0: return "Acoustic Grand Piano";
            case 24: return "Acoustic Guitar (nylon)";
            case 25: return "Acoustic Guitar (steel)";
            case 26: return "Electric Guitar (jazz)";
            case 27: return "Electric Guitar (clean)";
            case 28: return "Electric Guitar (muted)";
            case 29: return "Overdriven Guitar";
            case 30: return "Distortion Guitar";
            case 31: return "Guitar Harmonics";
            default: return "Instrument " + instrument;
        }
    }
}
