package com.vsge.audio;

/**
 * Interface for audio services in the VSGE system.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public interface AudioService {
    
    /**
     * Initializes the audio service.
     * 
     * @throws Exception if initialization fails
     */
    void initialize() throws Exception;
    
    /**
     * Plays a single note.
     * 
     * @param midiNumber the MIDI note number (0-127)
     * @param velocity the note velocity (0-127)
     * @param durationMs the duration in milliseconds
     */
    void playNote(int midiNumber, int velocity, long durationMs);
    
    /**
     * Plays multiple notes simultaneously (chord).
     * 
     * @param midiNumbers array of MIDI note numbers
     * @param velocity the note velocity (0-127)
     * @param durationMs the duration in milliseconds
     */
    void playChord(int[] midiNumbers, int velocity, long durationMs);
    
    /**
     * Stops all currently playing notes.
     */
    void stopAll();
    
    /**
     * Sets the instrument for the specified channel.
     * 
     * @param channel the MIDI channel (0-15)
     * @param instrument the instrument number (0-127)
     */
    void setInstrument(int channel, int instrument);
    
    /**
     * Closes the audio service and releases resources.
     */
    void close();
    
    /**
     * Checks if the audio service is initialized.
     * 
     * @return true if initialized, false otherwise
     */
    boolean isInitialized();
}
