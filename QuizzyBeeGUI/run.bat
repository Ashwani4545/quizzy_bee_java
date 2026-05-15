@echo off
echo ============================================
echo    QuizzyBee - Interactive Quiz System
echo ============================================
echo.
echo Compiling...
cd src
javac *.java
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Compilation failed. Make sure Java JDK is installed.
    pause
    exit /b 1
)
echo Compilation successful!
echo.
echo Launching QuizzyBee...
java QuizzyBeeApp
cd ..
pause
