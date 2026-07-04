@echo off
echo Compiling all Java files in GUI folder...
javac *.java
if %errorlevel% neq 0 (
    echo Compilation failed with errors!
    exit /b %errorlevel%
)
echo Compilation successful!
