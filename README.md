# Virtual Stringless Guitar Engine (VSGE)

A comprehensive Java-based music theory engine that simulates a stringless guitar device, inspired by the Liberlive stringless guitar. This project demonstrates advanced Object-Oriented Design principles and design patterns.

## Features

### Core Music Theory
- **Note System**: Immutable note representation with MIDI conversion
- **Interval System**: Complete musical interval enumeration
- **Chord System**: Abstract chord class with concrete implementations (Major, Minor, Dominant7, etc.)
- **Chord Progressions**: Roman numeral notation with predefined progressions

### Playing Styles (Strategy Pattern)
- **Folk Arpeggio**: Gentle fingerpicking pattern
- **Pop Strum**: Upbeat strumming pattern
- **Jazz Comping**: Sophisticated jazz accompaniment
- **Rock Power**: Heavy, driving rhythm

### Audio System
- **MIDI Integration**: Java Sound API for audio playback
- **Singleton Service**: Centralized audio management
- **Real-time Playback**: Scheduled note events with proper timing

### Interactive Features
- **Console UI**: Menu-driven interface
- **Interactive Performance**: Real-time chord playing
- **Preset Songs**: 5 classic songs with chord progressions
- **Custom Progressions**: User-defined chord sequences

## Design Patterns Implemented

1. **Singleton Pattern**: `MidiService` for audio service management
2. **Factory Pattern**: `ChordFactory` and `StyleFactory` for object creation
3. **Strategy Pattern**: `PlayStyle` interface with multiple implementations
4. **Template Method Pattern**: `Chord` abstract class with `buildChord()` template
5. **Builder Pattern**: `Song.Builder` for complex object construction
6. **Observer Pattern**: Event management for playback events

## Project Structure

```
src/main/java/com/vsge/
├── Main.java                      # Application entry point
├── core/
│   ├── theory/
│   │   ├── Note.java              # Musical note representation
│   │   └── Interval.java          # Musical intervals
│   ├── chord/
│   │   ├── Chord.java             # Abstract chord class
│   │   ├── ChordType.java         # Chord type enumeration
│   │   ├── ChordFactory.java      # Chord creation factory
│   │   ├── MajorChord.java        # Major triad implementation
│   │   ├── MinorChord.java        # Minor triad implementation
│   │   ├── Dominant7Chord.java    # Dominant 7th implementation
│   │   ├── Minor7Chord.java       # Minor 7th implementation
│   │   ├── Major7Chord.java       # Major 7th implementation
│   │   ├── DiminishedChord.java   # Diminished triad implementation
│   │   ├── ChordVoicing.java      # Chord voicing algorithms
│   │   └── VoicingType.java       # Voicing type enumeration
│   └── progression/
│       └── ChordProgression.java  # Chord progression with Roman numerals
├── style/
│   ├── PlayStyle.java             # Strategy pattern interface
│   ├── StyleFactory.java          # Style creation factory
│   └── impl/
│       ├── FolkArpeggioStyle.java # Folk fingerpicking
│       ├── PopStrumStyle.java     # Pop strumming
│       ├── JazzCompingStyle.java  # Jazz accompaniment
│       └── RockPowerStyle.java    # Rock power chords
├── engine/
│   ├── PlaybackEngine.java        # Core playback coordination
│   └── TempoController.java       # BPM and timing management
├── audio/
│   ├── AudioService.java          # Audio service interface
│   └── MidiService.java           # MIDI implementation (Singleton)
├── song/
│   ├── Song.java                  # Song representation with Builder
│   └── SongLibrary.java           # Preset songs collection
└── ui/
    └── ConsoleUI.java             # Console user interface
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher (optional, for building)

### Running the Application

1. **Compile the project**:
   ```bash
   mvn compile
   ```

2. **Run the main application**:
   ```bash
   mvn exec:java -Dexec.mainClass="com.vsge.Main"
   ```

3. **Run the test suite**:
   ```bash
   mvn test
   ```

4. **Run the simple test runner**:
   ```bash
   java -cp target/classes com.vsge.TestRunner
   ```

### Using the Console Interface

The application provides a menu-driven console interface:

```
=== Main Menu ===
1. Quick Play (Preset Songs)
2. Interactive Performance Mode
3. Custom Chord Progression
4. Style Demonstration
5. Single Chord Play
6. Settings
7. Help
8. Exit
```

#### Interactive Performance Mode
- **1-7**: Play chord degrees (I, ii, iii, IV, V, vi, vii°)
- **F/P/J/R**: Change style (Folk/Pop/Jazz/Rock)
- **S**: Stop all notes
- **Q**: Quit to main menu

## Preset Songs

1. **Let It Be** - The Beatles (C G Am F progression)
2. **12-Bar Blues** - Traditional (E major blues progression)
3. **Autumn Leaves** - Joseph Kosma (Gm jazz ii-V-I)
4. **Wonderwall** - Oasis (C D Em progression)
5. **House of the Rising Sun** - Traditional (Am C D F Am E Am)

## Code Examples

### Creating a Chord
```java
Note c4 = new Note(Note.PitchClass.C, 4);
Chord cMajor = ChordFactory.create(c4, ChordType.MAJOR);
```

### Creating a Chord Progression
```java
Note c4 = new Note(Note.PitchClass.C, 4);
ChordProgression pop = new ChordProgression(
    c4, ChordProgression.POP_PROGRESSION, 4
);
```

### Playing with Different Styles
```java
PlayStyle folkStyle = StyleFactory.create("folk");
PlayStyle rockStyle = StyleFactory.create("rock");
```

### Creating a Song
```java
Song song = new Song.Builder()
    .title("My Song")
    .artist("My Band")
    .genre("Rock")
    .progression(progression)
    .style(rockStyle)
    .tempo(120)
    .build();
```

## Technical Details

### Music Theory Implementation
- **Note System**: Immutable value objects with MIDI number conversion
- **Interval System**: Complete chromatic interval enumeration
- **Chord Construction**: Template method pattern for chord building
- **Roman Numerals**: Standard music theory notation (I, ii, iii, IV, V, vi, vii°)

### Audio System
- **MIDI Integration**: Uses Java Sound API (`javax.sound.midi`)
- **Timing**: Precise beat-based timing with tempo control
- **Voicing**: Multiple chord voicing algorithms (root position, inversions, etc.)

### Design Patterns
- **Singleton**: Ensures single audio service instance
- **Factory**: Centralized object creation for chords and styles
- **Strategy**: Interchangeable playing styles
- **Template Method**: Consistent chord construction process
- **Builder**: Complex song object construction
- **Observer**: Event-driven playback system

## Testing

The project includes comprehensive unit tests covering:
- Note creation and transposition
- Chord construction and voicing
- Chord progression generation
- Play style pattern generation
- Song library functionality

Run tests with:
```bash
mvn test
```

## Code Quality

- **Google Java Style Guide** compliance
- **Comprehensive Javadoc** documentation
- **80%+ test coverage** target
- **Proper exception handling**
- **Immutable value objects** where appropriate

## GUI Application

VSGE now includes a **Swing-based graphical user interface** for interactive chord playing!

### Launch the GUI:
```bash
# Run the graphical interface
java -cp target/classes com.vsge.ui.VSGEGui
```

### GUI Features:
- **7 Chord Buttons**: I, ii, iii, IV, V, vi, vii° (Roman numerals)
- **Style Selection**: Folk, Pop, Jazz, Rock
- **Key Selection**: C, D, E, F, G, A, B
- **Strum Speed Slider**: Adjust tempo (50-200 BPM)
- **Stop Button**: Stop all currently playing sounds
- **Visual Feedback**: Buttons change color when playing
- **Real-time Status**: Shows current action and settings

### GUI Layout:
```
+---------------------------------+
| Virtual Stringless Guitar       |
| 🎸 Click a chord to play!        |
|                                 |
| [ I ] [ ii ] [ iii ] [ IV ]     |
| [ V ] [ vi ] [ vii° ]           |
|                                 |
| Style: [Folk▼] Key: [C▼]        |
| Speed: [====●====] [Stop All]   |
|                                 |
| Ready to play!                  |
+---------------------------------+
```

## Future Enhancements

- **MIDI Export**: Save performances to MIDI files
- **Advanced Voicing**: More sophisticated chord voicing algorithms
- **Visual Chord Diagrams**: ASCII art chord representations
- **Custom Instruments**: Multiple MIDI instrument support
- **Recording**: Capture and replay performances

## License

This project is created for educational purposes as part of a CS5004 Object-Oriented Design course.

## Author

VSGE Team - Virtual Stringless Guitar Engine v1.0.0