package com.vsge.music.rhythm;

import com.vsge.music.rhythm.impl.*;

/**
 * Factory for creating rhythm patterns.
 * Implements the Factory design pattern.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class RhythmPatternFactory {
    
    /**
     * Creates a rhythm pattern based on the style name.
     * 
     * @param styleName the name of the style
     * @return the corresponding rhythm pattern
     * @throws IllegalArgumentException if style name is not recognized
     */
    public static RhythmPattern create(String styleName) {
        if (styleName == null) {
            throw new IllegalArgumentException("Style name cannot be null");
        }
        
        switch (styleName.toLowerCase()) {
            case "folk":
                return new FolkFingerpickingPattern();
            case "pop":
                return new PopStrummingPattern();
            case "jazz":
                return new JazzCompingPattern();
            case "rock":
                return new RockPowerPattern();
            default:
                throw new IllegalArgumentException("Unknown style: " + styleName);
        }
    }
    
    /**
     * Gets all available rhythm pattern names.
     * 
     * @return array of available pattern names
     */
    public static String[] getAvailablePatterns() {
        return new String[]{"folk", "pop", "jazz", "rock"};
    }
    
    /**
     * Checks if a style name is supported.
     * 
     * @param styleName the style name to check
     * @return true if supported, false otherwise
     */
    public static boolean isSupported(String styleName) {
        if (styleName == null) {
            return false;
        }
        
        String[] available = getAvailablePatterns();
        for (String pattern : available) {
            if (pattern.equalsIgnoreCase(styleName)) {
                return true;
            }
        }
        return false;
    }
}
