package com.vsge.core.chord;

/**
 * Types of chord voicings for different musical contexts.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public enum VoicingType {
    ROOT_POSITION("Root Position"),
    FIRST_INVERSION("First Inversion"),
    SECOND_INVERSION("Second Inversion"),
    CLOSE("Close Voicing"),
    OPEN("Open Voicing"),
    DROP2("Drop 2 Voicing"),
    SPREAD("Spread Voicing");

    private final String displayName;

    VoicingType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
