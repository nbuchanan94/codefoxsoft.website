@echo off
title Simple Git Upload
color 0A

:: Make sure we're inside a git repo
if not exist ".git" (
    echo This folder is not a Git repository.
    echo Please run this inside your cloned repo folder.
    pause
    exit /b
)

:: Ask for commit message
set /p msg=Enter commit message: 

echo.
echo --- Uploading changes to GitHub ---
git add -A
git commit -m "%msg%"
git push origin main

echo.
echo Upload complete.
pause
