#!/bin/bash
echo "============================================"
echo "   QuizzyBee - Interactive Quiz System"
echo "============================================"
echo ""
echo "Compiling..."
cd src
javac *.java
if [ $? -ne 0 ]; then
    echo ""
    echo "ERROR: Compilation failed. Make sure Java JDK is installed."
    exit 1
fi
echo "Compilation successful!"
echo ""
echo "Launching QuizzyBee..."
java QuizzyBeeApp
cd ..
