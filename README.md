# Virtual Stringless Guitar Engine (VSGE) 🎸

A Java-based music theory engine that simulates a stringless guitar, inspired by the Liberlive device.

## 📋 Project Overview

VSGE is an object-oriented design project that demonstrates advanced OOP principles through the implementation of a music theory and playback system. The engine can generate and play chord progressions in various musical styles.

### Features
- 🎵 Complete music theory modeling (notes, chords, scales, progressions)
- 🎸 Multiple playing styles (Folk, Pop, Jazz, Rock)
- 🎹 Real-time MIDI playback
- 📝 Preset songs and custom progressions
- 🎨 Extensible architecture using design patterns

## 🏗️ Architecture

The project demonstrates the following design patterns:
- **Singleton Pattern**: MidiService
- **Factory Pattern**: ChordFactory, StyleFactory
- **Strategy Pattern**: PlayStyle implementations
- **Template Method**: Chord hierarchy, PlaybackEngine
- **Builder Pattern**: SongBuilder, ProgressionBuilder
- **Observer Pattern**: Event management system

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MIDI-capable system (for audio playback)

### Building the Project
```bash
# Clone the repository
git clone https://github.com/yourusername/vsge.git
cd vsge

# Build with Maven
mvn clean compile

# Run tests
mvn test

# Package as JAR
mvn package
```

### Running the Application
```bash
# Run with Maven
mvn exec:java -Dexec.mainClass="com.vsge.Main"

# Or run the JAR
java -jar target/vsge-1.0.0-jar-with-dependencies.jar
```

## 💻 Usage

### Quick Play Mode

Select "Quick Play" from the main menu
Choose a preset song (e.g., "Let It Be")
Select a playing style (e.g., "Folk Arpeggio")
Press Enter to stop playback


### Custom Progression Mode

Select "Custom Chord Progression"
Enter chord symbols: C G Am F
Choose a playing style
Enjoy your creation!


## 📦 Project Structure
vsge/
├── src/main/java/com/vsge/
│   ├── core/          # Music theory classes
│   ├── style/         # Playing style implementations
│   ├── engine/        # Playback engine
│   ├── audio/         # MIDI services
│   ├── song/          # Song management
│   └── ui/            # User interface

## 🧪 Testing

Run the complete test suite:
```bash
mvn test
```

Generate code coverage report:
```bash
mvn jacoco:report
```

Run style checks:
```bash
mvn checkstyle:check
```

## 📈 Code Quality Metrics
- Test Coverage: >80%
- Checkstyle Compliance: Google Java Style
- Lines of Code: ~2000 (excluding tests)

## 🎓 Educational Value

This project is designed for CS5004 Object-Oriented Design course and demonstrates:
- Clean architecture and separation of concerns
- Proper use of inheritance and polymorphism
- Implementation of multiple design patterns
- Comprehensive unit testing
- Professional documentation standards

## 🤝 Contributing

This is an academic project. Contributions should maintain:
- Google Java Style Guidelines
- Comprehensive Javadoc documentation
- Unit test coverage for new features
- Clear commit messages

## 📝 License

This project is created for educational purposes as part of CS5004 at Northeastern University.

## 🙏 Acknowledgments

- Inspired by the Liberlive stringless guitar device
- CS5004 course instructors and TAs
- Java MIDI API documentation and community

## 📧 Contact

For questions about this project, please contact via course Piazza or GitHub issues.

---
*Built with ❤️ for CS5004 Object-Oriented Design*