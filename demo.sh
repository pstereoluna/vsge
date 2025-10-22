#!/bin/bash

# VSGE Demo Script - Clean Version
# Virtual Stringless Guitar Engine

echo "=== Virtual Stringless Guitar Engine Demo ==="
echo ""

# Check Java
if ! command -v java &> /dev/null; then
    echo "❌ Java not found. Please install Java 17+"
    exit 1
fi

echo "✅ Java: $(java -version 2>&1 | head -n 1)"
echo ""

# Compile if needed
if [ ! -d "target/classes" ] || [ ! -f "target/classes/com/vsge/Main.class" ]; then
    echo "🔨 Compiling VSGE..."
    mkdir -p target/classes
    javac -cp "src/main/java" -d "target/classes" src/main/java/com/vsge/**/*.java 2>/dev/null
    echo "✅ Compilation complete"
    echo ""
fi

# Show available commands
echo "🎵 VSGE is ready! Available commands:"
echo ""
echo "1. Check MIDI system:"
echo "   java -cp target/classes com.vsge.MidiInit"
echo ""
echo "2. Test guitar sounds:"
echo "   java -cp target/classes com.vsge.EnhancedGuitarTest"
echo ""
echo "3. Run core tests:"
echo "   java -cp target/classes com.vsge.TestRunner"
echo ""
echo "4. Auto demo:"
echo "   java -cp target/classes com.vsge.Demo"
echo ""
echo "5. Interactive mode:"
echo "   java -cp target/classes com.vsge.Main"
echo ""
echo "6. GUI Application:"
echo "   java -cp target/classes com.vsge.ui.VSGEGui"
echo ""
echo "🎸 Features: 4 playing styles, 5 preset songs, 6 chord types"
echo "🖥️  GUI: Interactive chord buttons with visual feedback"
echo "📚 Design patterns: Singleton, Factory, Strategy, Template Method, Builder, Observer"
