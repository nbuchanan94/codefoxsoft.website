@echo off
cd /d "%~dp0"

echo ========================================
echo Git Auto-Sync Script (Robust Mode v2)
echo ========================================

echo 1. Clearing any stuck Git locks/processes...
taskkill /F /IM git.exe 2>nul
taskkill /F /IM "git-remote-https.exe" 2>nul

if exist ".git\index.lock" del ".git\index.lock" /F /Q
if exist ".git\rebase-merge" rd ".git\rebase-merge" /S /Q
if exist ".git\rebase-apply" rd ".git\rebase-apply" /S /Q

echo.
echo 2. Ensuring Repository State...
if not exist .git git init
git remote add origin https://github.com/codefoxsoft/Java 2>nul

echo.
echo 3. Switching to Main Branch...
echo    - Forcing 'main' branch to track current state
git checkout -B main

echo.
echo 4. Syncing...
echo    - Adding files...
git add .
echo    - Committing...
git commit -m "Update sorted Java projects"

echo.
echo 5. Pulling from Remote (Rebase)...
git pull origin main --allow-unrelated-histories --rebase
if %errorlevel% neq 0 (
    echo.
    echo [!] Conflict or Error during pull.
    pause
    exit /b %errorlevel%
)

echo.
echo 6. Pushing to GitHub...
git push -u origin main

echo.
echo ========================================
echo Sync Complete!
echo ========================================
pause
