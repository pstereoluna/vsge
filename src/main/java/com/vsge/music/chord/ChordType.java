// ========== ChordType.java ==========
package com.vsge.music.chord;

/**
 * Enumeration of supported chord types.
 */
public enum ChordType {
  MAJOR("Major"),
  MINOR("Minor"),
  DOMINANT7("Dominant 7th"),
  MINOR7("Minor 7th"),
  MAJOR7("Major 7th"),
  DIMINISHED("Diminished");

  private final String displayName;

  ChordType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
