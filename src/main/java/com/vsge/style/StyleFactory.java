package com.vsge.style;

import com.vsge.style.impl.*;

/**
 * Factory class for creating play style instances.
 * Implements the Factory design pattern.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class StyleFactory {
    
    private StyleFactory() {
        // Private constructor to prevent instantiation
    }

    /**
     * Creates a play style instance based on the style name.
     * 
     * @param styleName the name of the style to create
     * @return a new play style instance
     * @throws IllegalArgumentException if the style name is not supported
     */
    public static PlayStyle create(String styleName) {
        if (styleName == null || styleName.trim().isEmpty()) {
            throw new IllegalArgumentException("Style name cannot be null or empty");
        }

        String normalized = styleName.trim().toLowerCase();
        
        switch (normalized) {
            case "folk":
            case "folk arpeggio":
            case "arpeggio":
                return new FolkArpeggioStyle();
            case "pop":
            case "pop strum":
            case "strum":
                return new PopStrumStyle();
            case "jazz":
            case "jazz comping":
            case "comping":
                return new JazzCompingStyle();
            case "rock":
            case "rock power":
            case "power":
                return new RockPowerStyle();
            default:
                throw new IllegalArgumentException("Unknown style: " + styleName);
        }
    }

    /**
     * Gets all available style names.
     * 
     * @return array of available style names
     */
    public static String[] getAvailableStyles() {
        return new String[]{
            "Folk Arpeggio",
            "Pop Strum", 
            "Jazz Comping",
            "Rock Power"
        };
    }

    /**
     * Gets the default style.
     * 
     * @return the default play style
     */
    public static PlayStyle getDefault() {
        return new FolkArpeggioStyle();
    }
}
