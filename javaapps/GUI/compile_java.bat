@echo off
echo Compiling all Java files in GUI folder with Java 8 compatibility for CheerpJ Web JVM...
javac --release 8 *.java
if %errorlevel% neq 0 (
    echo Compilation failed with errors!
    exit /b %errorlevel%
)
echo Compilation successful!
