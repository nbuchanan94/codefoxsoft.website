@echo off
:: ============================================================
::   CODEFOXSOFT - WEBSITE UPLOADER
::   Retro DOS-style commit & push utility
:: ============================================================

:: Set terminal colors (0 = black background, A = bright green text)
color 0A

title CODEFOXSOFT - WEBSITE UPLOADER

echo.
echo  ============================================================
echo    CODEFOXSOFT - WEBSITE UPLOADER
echo    Nerd-Grade Git Automation Terminal
echo  ============================================================
echo.

cd /d C:\Users\psych\Documents\codefoxsoftsite\codefoxsoft.website

:: Ask for commit message
set /p msg=Enter commit message (leave blank for auto-message): 

:: If empty, generate fallback message
if "%msg%"=="" (
    for /f "tokens=1-4 delims=/ " %%a in ("%date%") do (
        for /f "tokens=1-2 delims=: " %%x in ("%time%") do (
            set msg=update_%%a-%%b-%%c_%%x%%y
        )
    )
)

echo.
echo  Adding changes...
git add -A

echo  Committing with message: "%msg%"
git commit -m "%msg%"

echo  Pushing to origin/main...
git push origin main

echo.
echo  ============================================================
echo    Upload Complete — CODEFOXSOFT Has Spoken
echo  ============================================================
echo.
pause