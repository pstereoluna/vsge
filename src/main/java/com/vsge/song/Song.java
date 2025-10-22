package com.vsge.song;

import com.vsge.core.progression.ChordProgression;
import com.vsge.style.PlayStyle;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a complete song with metadata and progression.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class Song {
    private final String title;
    private final String artist;
    private final String genre;
    private final ChordProgression progression;
    private final PlayStyle style;
    private final int tempo;
    private final List<String> lyrics;
    
    private Song(Builder builder) {
        this.title = builder.title;
        this.artist = builder.artist;
        this.genre = builder.genre;
        this.progression = builder.progression;
        this.style = builder.style;
        this.tempo = builder.tempo;
        this.lyrics = new ArrayList<>(builder.lyrics);
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getArtist() {
        return artist;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public ChordProgression getProgression() {
        return progression;
    }
    
    public PlayStyle getStyle() {
        return style;
    }
    
    public int getTempo() {
        return tempo;
    }
    
    public List<String> getLyrics() {
        return new ArrayList<>(lyrics);
    }
    
    @Override
    public String toString() {
        return String.format("%s by %s (%s) - %d BPM", title, artist, genre, tempo);
    }
    
    /**
     * Builder class for creating Song instances.
     * Implements the Builder design pattern.
     */
    public static class Builder {
        private String title;
        private String artist;
        private String genre;
        private ChordProgression progression;
        private PlayStyle style;
        private int tempo = 120;
        private List<String> lyrics = new ArrayList<>();
        
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        
        public Builder artist(String artist) {
            this.artist = artist;
            return this;
        }
        
        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }
        
        public Builder progression(ChordProgression progression) {
            this.progression = progression;
            return this;
        }
        
        public Builder style(PlayStyle style) {
            this.style = style;
            return this;
        }
        
        public Builder tempo(int tempo) {
            this.tempo = tempo;
            return this;
        }
        
        public Builder addLyric(String lyric) {
            this.lyrics.add(lyric);
            return this;
        }
        
        public Builder lyrics(List<String> lyrics) {
            this.lyrics = new ArrayList<>(lyrics);
            return this;
        }
        
        public Song build() {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalStateException("Title is required");
            }
            if (progression == null) {
                throw new IllegalStateException("Progression is required");
            }
            if (style == null) {
                throw new IllegalStateException("Style is required");
            }
            
            return new Song(this);
        }
    }
}
