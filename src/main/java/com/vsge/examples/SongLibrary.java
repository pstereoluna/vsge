package com.vsge.examples;

import com.vsge.music.chord.ChordFactory;
import com.vsge.music.progression.ChordProgression;
import com.vsge.music.theory.Note;
import com.vsge.style.PlayStyle;
import com.vsge.style.StyleFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Library of preset songs for the VSGE system.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class SongLibrary {
    
    private static final List<Song> PRESET_SONGS = new ArrayList<>();
    
    static {
        initializePresetSongs();
    }
    
    private static void initializePresetSongs() {
        // Let It Be
        PRESET_SONGS.add(new Song.Builder()
            .title("Let It Be")
            .artist("The Beatles")
            .genre("Rock")
            .progression(new ChordProgression(
                new Note(Note.PitchClass.C, 4),
                ChordProgression.POP_PROGRESSION,
                4
            ))
            .style(StyleFactory.create("folk"))
            .tempo(120)
            .addLyric("When I find myself in times of trouble")
            .addLyric("Mother Mary comes to me")
            .addLyric("Speaking words of wisdom, let it be")
            .build());
        
        // 12-Bar Blues in E
        PRESET_SONGS.add(new Song.Builder()
            .title("12-Bar Blues")
            .artist("Traditional")
            .genre("Blues")
            .progression(new ChordProgression(
                new Note(Note.PitchClass.E, 4),
                ChordProgression.BLUES_12_BAR,
                4
            ))
            .style(StyleFactory.create("rock"))
            .tempo(100)
            .addLyric("I woke up this morning, feeling blue")
            .addLyric("I woke up this morning, feeling blue")
            .addLyric("My baby left me, what am I gonna do")
            .build());
        
        // Autumn Leaves
        PRESET_SONGS.add(new Song.Builder()
            .title("Autumn Leaves")
            .artist("Joseph Kosma")
            .genre("Jazz")
            .progression(new ChordProgression(
                new Note(Note.PitchClass.G, 4),
                ChordProgression.JAZZ_II_V_I,
                4
            ))
            .style(StyleFactory.create("jazz"))
            .tempo(140)
            .addLyric("The falling leaves drift by the window")
            .addLyric("The autumn leaves of red and gold")
            .build());
        
        // Wonderwall
        PRESET_SONGS.add(new Song.Builder()
            .title("Wonderwall")
            .artist("Oasis")
            .genre("Rock")
            .progression(new ChordProgression(
                new Note(Note.PitchClass.C, 4),
                new ChordProgression.Degree[]{
                    ChordProgression.Degree.I,
                    ChordProgression.Degree.IV,
                    ChordProgression.Degree.vi,
                    ChordProgression.Degree.iii
                },
                4
            ))
            .style(StyleFactory.create("pop"))
            .tempo(110)
            .addLyric("Today is gonna be the day")
            .addLyric("That they're gonna throw it back to you")
            .build());
        
        // House of the Rising Sun
        PRESET_SONGS.add(new Song.Builder()
            .title("House of the Rising Sun")
            .artist("Traditional")
            .genre("Folk")
            .progression(new ChordProgression(
                new Note(Note.PitchClass.A, 4),
                new ChordProgression.Degree[]{
                    ChordProgression.Degree.vi,
                    ChordProgression.Degree.I,
                    ChordProgression.Degree.IV,
                    ChordProgression.Degree.I,
                    ChordProgression.Degree.vi,
                    ChordProgression.Degree.V,
                    ChordProgression.Degree.vi
                },
                4
            ))
            .style(StyleFactory.create("folk"))
            .tempo(90)
            .addLyric("There is a house in New Orleans")
            .addLyric("They call the Rising Sun")
            .addLyric("And it's been the ruin of many a poor boy")
            .build());
    }
    
    /**
     * Gets all available preset songs.
     * 
     * @return list of preset songs
     */
    public static List<Song> getPresetSongs() {
        return new ArrayList<>(PRESET_SONGS);
    }
    
    /**
     * Gets a song by title.
     * 
     * @param title the song title
     * @return the song, or null if not found
     */
    public static Song getSongByTitle(String title) {
        if (title == null) {
            return null;
        }
        
        return PRESET_SONGS.stream()
            .filter(song -> song.getTitle().equalsIgnoreCase(title))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Gets songs by genre.
     * 
     * @param genre the genre to filter by
     * @return list of songs in the genre
     */
    public static List<Song> getSongsByGenre(String genre) {
        if (genre == null) {
            return new ArrayList<>();
        }
        
        return PRESET_SONGS.stream()
            .filter(song -> song.getGenre().equalsIgnoreCase(genre))
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Gets the number of preset songs.
     * 
     * @return number of songs
     */
    public static int getSongCount() {
        return PRESET_SONGS.size();
    }
}
