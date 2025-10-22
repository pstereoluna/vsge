#!/bin/bash

# VSGE GUI Launcher Script
# Launches the Virtual Stringless Guitar Engine GUI

echo "🎸 Starting Virtual Stringless Guitar Engine GUI..."
echo ""

# Check Java
if ! command -v java &> /dev/null; then
    echo "❌ Java not found. Please install Java 17+"
    exit 1
fi

# Compile if needed
if [ ! -d "target/classes" ] || [ ! -f "target/classes/com/vsge/ui/VSGEGui.class" ]; then
    echo "🔨 Compiling VSGE GUI..."
    mkdir -p target/classes
    javac -cp "src/main/java" -d "target/classes" src/main/java/com/vsge/**/*.java 2>/dev/null
    echo "✅ Compilation complete"
    echo ""
fi

# Launch GUI
echo "🚀 Launching VSGE GUI..."
java -cp target/classes com.vsge.ui.VSGEGui

echo ""
echo "👋 VSGE GUI closed. Thanks for playing!"
