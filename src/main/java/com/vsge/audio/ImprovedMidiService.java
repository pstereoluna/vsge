package com.vsge.audio;

import javax.sound.midi.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Improved MIDI service with better guitar sounds and effects.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class ImprovedMidiService implements AudioService {
    private static final Logger logger = Logger.getLogger(ImprovedMidiService.class.getName());
    private static ImprovedMidiService instance;
    private static final Object lock = new Object();
    
    private Synthesizer synthesizer;
    private MidiChannel[] channels;
    private ScheduledExecutorService scheduler;
    private boolean initialized = false;
    
    // Better guitar instrument settings
    private static final int DEFAULT_CHANNEL = 0;
    private static final int GUITAR_CHANNEL = 1;  // Use channel 1 for guitar
    private static final int DRUM_CHANNEL = 9;    // Channel 9 for drums
    
    // Best guitar sounds only (removed overdriven and distortion)
    private static final int[] GUITAR_INSTRUMENTS = {
        25,  // Acoustic Guitar (steel) - Best for most songs
        24,  // Acoustic Guitar (nylon) - Good for classical/folk
        27,  // Electric Guitar (clean) - Best electric sound
        31   // Guitar Harmonics - Unique sound for special effects
    };
    
    private int currentGuitarInstrument = 25; // Start with steel string
    
    private ImprovedMidiService() {
        // Private constructor for singleton
    }
    
    public static ImprovedMidiService getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ImprovedMidiService();
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
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            channels = synthesizer.getChannels();
            
            scheduler = Executors.newScheduledThreadPool(4);
            
            initialized = true;
            logger.info("Improved MIDI service initialized with enhanced guitar sound");
            
            // Set up guitar channel with better sound (after initialization)
            setupGuitarChannel();
            
        } catch (MidiUnavailableException e) {
            logger.severe("Failed to initialize improved MIDI service: " + e.getMessage());
            throw new Exception("MIDI service unavailable", e);
        }
    }
    
    private void setupGuitarChannel() {
        try {
            MidiChannel guitarChannel = channels[GUITAR_CHANNEL];
            
            // Set guitar instrument
            guitarChannel.programChange(currentGuitarInstrument);
            
            // Add some effects to make it sound more like a guitar
            // Reverb (Controller 91)
            guitarChannel.controlChange(91, 40);
            
            // Chorus (Controller 93) 
            guitarChannel.controlChange(93, 20);
            
            // Volume (Controller 7)
            guitarChannel.controlChange(7, 100);
            
            // Pan (Controller 10) - slightly left for guitar
            guitarChannel.controlChange(10, 30);
            
            logger.info("Guitar channel setup complete with effects");
            
        } catch (Exception e) {
            logger.warning("Error setting up guitar channel: " + e.getMessage());
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
            MidiChannel channel = channels[GUITAR_CHANNEL];
            
            // Add slight velocity variation for more natural sound
            int variedVelocity = velocity + (int)(Math.random() * 10) - 5;
            variedVelocity = Math.max(20, Math.min(127, variedVelocity));
            
            // Play the note
            channel.noteOn(midiNumber, variedVelocity);
            
            // Schedule note off with slight timing variation
            if (durationMs > 0) {
                long variedDuration = durationMs + (long)(Math.random() * 100) - 50;
                scheduler.schedule(() -> {
                    try {
                        channel.noteOff(midiNumber);
                    } catch (Exception e) {
                        logger.warning("Error stopping note: " + e.getMessage());
                    }
                }, Math.max(50, variedDuration), TimeUnit.MILLISECONDS);
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
            MidiChannel channel = channels[GUITAR_CHANNEL];
            
            // Play notes with slight timing offset to simulate strumming
            for (int i = 0; i < midiNumbers.length; i++) {
                int midiNumber = midiNumbers[i];
                if (midiNumber >= 0 && midiNumber <= 127) {
                    // Add slight delay for strumming effect
                    long delay = i * 20; // 20ms between each string
                    
                    final int finalVelocity = velocity;
                    scheduler.schedule(() -> {
                        try {
                            int variedVelocity = finalVelocity + (int)(Math.random() * 15) - 7;
                            variedVelocity = Math.max(30, Math.min(120, variedVelocity));
                            channel.noteOn(midiNumber, variedVelocity);
                        } catch (Exception e) {
                            logger.warning("Error playing chord note: " + e.getMessage());
                        }
                    }, delay, TimeUnit.MILLISECONDS);
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
            for (MidiChannel channel : channels) {
                if (channel != null) {
                    channel.allNotesOff();
                    channel.allSoundOff();
                }
            }
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
            if (channel == GUITAR_CHANNEL) {
                currentGuitarInstrument = instrument;
                setupGuitarChannel(); // Reapply effects
            }
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
            logger.info("Improved MIDI service closed");
            
        } catch (Exception e) {
            logger.warning("Error closing improved MIDI service: " + e.getMessage());
        }
    }
    
    @Override
    public boolean isInitialized() {
        return initialized;
    }
    
    /**
     * Cycle through different guitar sounds.
     */
    public void nextGuitarSound() {
        int currentIndex = 0;
        for (int i = 0; i < GUITAR_INSTRUMENTS.length; i++) {
            if (GUITAR_INSTRUMENTS[i] == currentGuitarInstrument) {
                currentIndex = i;
                break;
            }
        }
        
        int nextIndex = (currentIndex + 1) % GUITAR_INSTRUMENTS.length;
        setInstrument(GUITAR_CHANNEL, GUITAR_INSTRUMENTS[nextIndex]);
    }
    
    /**
     * Get current guitar instrument name.
     */
    public String getCurrentGuitarName() {
        return getInstrumentName(currentGuitarInstrument);
    }
    
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
